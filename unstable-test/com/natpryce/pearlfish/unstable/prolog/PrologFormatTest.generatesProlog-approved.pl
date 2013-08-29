level(level_0).
level(level_1).
level(level_4).

person('Alice').
person('Bob').
person('Carol').
person('Dave').
person('Eve').

room(kitchen).
room(livingRoom).


:- modeh(1, female(+person))?

female('Alice').
:- female('Bob').
female('Carol').
:- female('Dave').
female('Eve').

:- modeh(1, ordered(+number, +number))?

ordered(10, 20).
ordered(1, 2).
ordered(-5, -3).
ordered(-5, 4).
:- ordered(2, 1).
:- ordered(0, 0).
:- ordered(1, 1).

:- modeh(1, light_level(+room, +level))?

light_level(kitchen, level_1).
light_level(livingRoom, level_4).
light_level(_, level_0).
