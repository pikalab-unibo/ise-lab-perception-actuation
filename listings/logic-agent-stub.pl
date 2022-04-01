start :- 
    repeat,
    sleep(100 /* ms */ ), % just to slow down the execution
    act(Step),
    fail.

act(Step) :- write('Hello world '), write(Step), nl.