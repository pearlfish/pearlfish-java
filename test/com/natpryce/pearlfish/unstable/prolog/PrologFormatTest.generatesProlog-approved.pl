number(-3).
number(-5).
number(0).
number(1).
number(10).
number(2).
number(20).
number(4).

person(alice).
person(bob).
person(carol).
person(dave).
person(eve).


female(alice).
:- female(bob).
female(carol).
:- female(dave).
female(eve).

ordered(10, 20).
ordered(1, 2).
ordered(-5, -3).
ordered(-5, 4).
:- ordered(2, 1).
:- ordered(0, 0).
:- ordered(1, 1).
