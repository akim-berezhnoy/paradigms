ensure_prime(N, Candidate) :- N < Candidate * Candidate; mod(N, Candidate) =\= 0, ensure_prime(N, Candidate + 1).
prime(N) :- primes(N); ensure_prime(N, 2), assert(primes(N)).
composite(N) :- \+ prime(N).

prime_divisors(1, []).
prime_divisors(N, divisors) :- N > 1, remaining_divisors(N, Divisors, 2), !.
is_prime_divisor(N, Divisor) :- prime(Divisor), 0 is mod(N, Divisor).
remaining_divisors(N, [Divisor], _) :- prime(N), Divisor is N.
remaining_divisors(N, [Divisor | Tail], Candidate) :-
    (N >= Candidate * Candidate,
     is_prime_divisor(N, Candidate),
     Divisor is Candidate,
     remaining_divisors(div(N, Divisor), Tail, Divisor));
    remaining_divisors(N, [Divisor | Tail], Candidate + 1).

square_divisors(1, []).
square_divisors(N, DoubledPrimes) :- N > 1, prime_divisors(N, Primes), double(Primes, DoubledPrimes).

double([Head], [Head, Head]).
double([Head | Tail], [Head, Head | DoubledTail]) :- double(Tail, DoubledTail).
