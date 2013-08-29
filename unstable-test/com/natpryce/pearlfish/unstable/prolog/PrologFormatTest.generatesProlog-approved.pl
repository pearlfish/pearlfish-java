level(level_0).
level(level_4).
level(level_1).

person('Alice').
person('Eve').
person('Dave').
person('Bbob').
person('Carol').

room(livingRoom).
room(kitchen).


:- modeh(1, female(+person))?

female('Alice').
:- female('Bbob').
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
