package queue;

import java.util.Iterator;

/*
Model: a[1]..a[n]
Invariant: n >= 0 && forall i=1..n: a[i] != null

Let immutable(n): for i=1..n a'[i] = a[i]
Let first(el) = el in a ? (i : a[i] == el && forall j : a[j] == el -> i < j) : -1
Let last(el) = el in a ? (i : a[i] == el && forall j : a[j] == el -> i > j) : -1

Inv: queue.size >= 0 && forall i = 1..n: elements[i] != null
 */
public interface Queue extends Iterable<Object> {

    //  Pred: queue != null && element != null
    //  Post: n' = n + 1 && a[n'] = element && immutable(n)
    //  enqueue(element)
    void enqueue(Object element);

    //  Pred: n >= 1
    //  Post: R = a[1] && n' = n && immutable(n)
    //  element()
    Object element();

    //  Pred: n >= 1
    //  Post: R = a[1] && n' = n - 1 && forall i=1..n-1: a'[i] = a[i+1]
    //  dequeue()
    Object dequeue();

    //  Pred: true
    //  Post: R = n && n' = n && immutable(n)
    //  size()
    int size();

    //  Pred: true
    //  Post: R = (n == 0) && n' = n && immutable(n)
    //  isEmpty()
    boolean isEmpty();

    //  Pred: true
    //  Post: n' = 0
    //  clear()
    void clear();

    //  Pred: true
    //  Post: R = |{i : a[i] == element}|
    //  count(element)
    int count(Object element);

    //  Pred: 1 <= index <= n
    //  Post: R = a[index]
    //  get(index)
    Object get(int index);

    //  Pred: 1 <= index <= n
    //  Post: a[index] = element
    //  set(index, element)
    void set(int index, Object element);

    //  Pred: true
    //  Post: R = [a]
    //  toArray()
    Object[] toArray();

    //  Pred: true
    //  Post: R = (for any i =1..n : a[i] == element) && n' = n && immutable(n)
    //  contains(element)
    boolean contains(Object element);

    //  Pred: true
    //  Post:
    //    R = first(el) < 0 ? false : true &&
    //    immutable(first(el)-1) && forall i=first(el)..n-1 : a'[i] = a[i+1]
    //  removeFirstOccurrence()
    boolean removeFirstOccurrence(Object element);

    //  Pred: true
    //  Post: R = first(el) && immutable(n)
    //  indexOf()
    int indexOf(Object element);

    //  Pred: true
    //  Post: R = last(el) && immutable(n)
    //  lastIndexOf()
    int lastIndexOf(Object element);
}
