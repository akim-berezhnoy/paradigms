initCache(_, []).
initCache(Iterator, [(NodeKey, NodeValue) | T]) :- assert(get(Iterator, (NodeKey, NodeValue))), NextIterator is Iterator + 1, initCache(NextIterator, T).

map_build(List, Tree) :- length(List, Len), initCache(0, List), map_build(0, Len, List, Tree), retractall(get(_, _)).

map_build(L, R, List, Tree) :- (L is R, Tree = nil, !);
   Middle is (L + R)//2,
   get(Middle, (NodeKey, NodeValue)),
   MiddleNext is Middle + 1,
   map_build(L, Middle, List, LeftChild),
   map_build(MiddleNext, R, List, RightChild),
   Tree = [NodeKey, NodeValue, LeftChild, RightChild].

map_values(nil, []).
map_values([_, Value, LeftChild, RightChild], Values) :-
    map_values(LeftChild, MinValues),
    map_values(RightChild, MaxValues),
    append(MinValues, [Value], Minims),
    append(Minims, MaxValues, Values).

map_get(Tree, Key, Value) :- Tree = [NodeKey, NodeValue, LeftChild, RightChild],
       (Key < NodeKey, map_get(LeftChild, Key, Value); Key > NodeKey, map_get(RightChild, Key, Value); Key = NodeKey, Value = NodeValue).
