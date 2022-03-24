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

- The file system as an environment
    - reading a file as a string, given its path as the perception action
    - writing a file as a string, given its path as the actuation action
    - capability to memorize custom information
    - capability to discard memorised information



