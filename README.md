# Taskmanager exercise description

With Task Manager we refer to a software component that is designed for handling multiple processes inside an operating system. 

Each process is identified by 2 fields, a unique unmodifiable identifier (PID), and a priority (low, medium, high).
The process is immutable, it is generated with a priority and will die with this priority.
Each process has a kill() method that will destroy it.

We want the Task Manager to expose the following functionalities: 

- Add a process
- List running processes
- Kill/KillGroup/KillAll

The task manager should have a prefixed maximum capacity, so it can not have more than a certain number of running processes within itself. 
This value is defined at build time. The add(process) method in TM is used for it. 
The default behaviour is that we can accept new processes till when there is capacity inside the Task Manager, otherwise we won’t accept
any new process.


Add a process – FIFO approach 

A different customer wants a different behaviour:
he’s asking to accept all new processes through the add() method, killing and removing from the TM list the oldest one (First-In, First-Out) when the max size is reached.


Add a process – Priority based 

A new customer is asking something different again, every call to the add() method, when the max size is reached, should result into an evaluation: if the new process passed in the add() call has a higher priority compared to any of the existing one, we remove the lowest priority that is the oldest, otherwise we skip it


List running processes

The task manager offers the possibility to list() all the running processes, sorting them by time of creation (implicitly we can consider it the time in which has been added to the TM), priority or id.


Kill, KillGroup and KillAll

Model one or more methods capable of

1. killing a specific process
2. killing all processes with a specific priority
3. killing all running processes


# Test 

Since no framework could be used, we couldn't leverage on the dependency injection advantages (given by Spring/Google guice etc..) 
For this particular exercise, we could have even saved some code, testing the common methods in the abstract class, without doing the same for each specific concrete class (we can't instantiate the abstract class)

# Implementation details

I would have used lombok for the domain classes or added a property file where specify e.g. capacity and other props, but I think was not requested in this particular exercise.

For the Circular FIFO queue I found an implementation using the java.util.Queue, so I used it to limitate the code to write, not sure if it is ok or it was preferred implementing the behaviour from skratch. 

The code can be build using maven and a version of java >= 1.8 , since there are some pieces in which streams were used.  

