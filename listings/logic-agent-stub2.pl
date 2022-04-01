start :- 
    sleep(100 /* ms */ ), % just to slow down the execution
    act,
    start. % recursion here

act :- write('Hello world'), nl.