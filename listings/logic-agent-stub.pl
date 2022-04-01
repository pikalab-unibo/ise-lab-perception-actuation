start :- 
    repeat,
    sleep(100 /* ms */ ), % just to slow down the execution
    act,
    fail.

act :- write('Hello world'), nl.