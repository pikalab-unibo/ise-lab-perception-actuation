# Perception & Actuation from the Agent Perspective

## Lecture Goals

- Understand how to enable the perception and actuation from the agent perspective

- Understand the notion of layered software system

- Understand the key role of Application Programming Interfaces

## (Software) Environments

- Environment = anything laying outside the agents
    * Can be perceived via sensors (input)
    * Can be affected via actuators (output)

- Software agents can be abstractly modelled as composed by layers
    1. Some control software (e.g. Java program / logic program / AgentSpeak program)
    2. Some interpreter for that software (e.g. JVM, Prolog interpreter, Jason)
    3. Some OS mediating the usage of HW resources for the interpreter
    4. Hardware resources 
        - memory, storage, computational power, I/O (there including networking)

- From the sw agent's perspective, the environment is shaped by what perceptual / actuatory actions in can perform 

- The agent may also be endowed with __epistemic__ capabilities 
    - eg memorise new knowledge
    - eg forget memorised knowledge
    - eg update memorised knowledge

- Of course, endowing a sw agent with some perceptual / actuatory action requires
    - some enabling HW facility to be present
    - some OS functionality enabling the usage of that HW facility
    - the interpreter to know how to call that OS functionality
    - the programming language to have some ad-hoc API wrapping the OS functionality

### Running example

The file system as an environment

- reading a file as a string, given its path as the perception action

- writing a file as a string, given its path as the actuation action

- capability to memorize custom information

- capability to discard memorised information

## Prolog Agents

- Prolog is goal-oriented

- Basic structure of a Prolog agent:

    ```prolog
    start(Step) :- 
        natural(Step),
        sleep(100 /* ms */ ), % just to slow down the execution
        act(Step),
        fail.

    act(Step) :- write('Hello world '), write(Step), nl.
    ```

- Hence, it is very easy to see a Prolog solver as an agent capable of reasoning
    * Knowledge base -> memory
    * Resolution -> thinking / reasoning
    * Assertion/retraction -> epistemic actions
    * Write, ??? -> Actuation
    * ??? -> Perception
    * ??? -> Environment

### Example of thermostat agent in Prolog

```prolog

warm_range(20, 30).

start :- 
    repeat,
    sleep(100 /* ms */), % just to slow down the execution
    warm_range(Min, Max),
    keep_temperature(Min, Max),
    fail.

keep_temperature(Min, Max) :- 
    check_temperature(T),
    handle_temperature(T, Min, Max).

check_temperature(T) :-
    read_text("/path/to/environment.txt", T).

handle_temperature(T, Min, _) :- T <= Min, !,
    T1 = T + 1,
    write_text("/path/to/environment.txt", T1).

handle_temperature(T, _, Max) :- T >= Max, !,
    T1 = T - 1,
    write_text("/path/to/environment.txt", T1).

handle_temperature(_, _, _). % otherwise do nothing 
```
#### Things to be noticed

* Knowledge base -> memory

* Resolution -> thinking / reasoning

* Assertion/retraction -> epistemic actions

* File writing -> Actuation

* File reading -> Perception

* File system -> Environment

#### TODO list

- reading a file as a string, given its path as the perception action
    * read_text/2 __(to be implemented)__

- writing a file as a string, given its path as the actuation action
    * write_text/2 __(to be implemented)__

- capability to memorize custom information
    * assert/1, asserta/1, assertz/1 (provided)

- capability to discard memorised information
    * retract/1 (provided)

> How to realise these kind of functionalities?

> How to extend logic solver with further functionalities?

## The notion of Generator

> citation to jelia + figure generator.svg

Logic solver's gateways towards the world

Servers serving a solver's request to perform some functionality (and providing 0, 1, or more responses)

* a particular case of artefact, under a MAS perspective

Definition:

* Signature: name + arity 

* Behaviour: function mapping **requests** to **_a stream of_ responses**
    - Positive responses
    - Negative responses
    - Exceptional responses

* Requests carry information about
    - the execution context (at the time of request issuing)
    - the _actual_ arguments of the request

* Responses carry information about
    - success / failure / error
    - substitution to be applied to variables
    - side-effects to be applied to the execution context


Workflow: 

1. When attempting to solve a goal `G` the solver may try to use a generator

2. Then it creates a request to be sent to the generator

3. The generator is then triggered: it produces a stream of responses 

5. The solver lazily consumes the stream of responses
    - when a response is consumed depends on how the solver performs resolution
    - whenever a response is consumed, any side effect possibly carried by that response is reified

6. Each response constitutes a branch in the proof three, to be explored by the solver

In pratice:
1. 2P-Kt provides the `Primitive` interface, to implemented by generators
    - many sub-types are available in practice to simplify programming generators

2. Upon creation, solver accept `Libraries`, containing zero or more primitives
    - standard built-ins are constructed in this way

3. During resolution, solvers may exploit generators to solve (sub-)goals 


### Examples

1. `natural(?N)` tests or generates natural numbers

2. `update(?Fact)` retract & update a fact in a single computational step

## Exercises

1. `read_text(+Path, -Text)` reads whole file as `Text`, given its `Path`

2. `write_text(+Path, +Text)` write `Text` as a whole file, given its `Path`
    + replaces the file silently if already present

3. support smooth termination for the thermostat agent 

## Discussion about the thermostat agent

1. Pro-active or reactive?

2. Autonomous?
    - w.r.t. motivational autonomy
    - w.r.t. executive autonomy
    - w.r.t. computational autonomy

3. Weak goal or strong goal?
