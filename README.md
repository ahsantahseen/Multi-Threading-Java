## Category of Approach and Parallelism Model

According to the Flynn’s Taxonomy, the category of my approach falls into SIMD’s sub category SIMT or
also known as Array Processing, basically what I am doing is that I am splitting my data across 4 Threads,
each thread will be perform the given trigonometric equation with different data values and summing
up the results with the main sum variable, so the parallelism model will be Data Parallelism.

<img src="https://i.ibb.co/hFhTp9P/C1.png" alt="C1" border="0">

## Overall Speedup Through Amdahl’s Law

```
Considering time unit as milliseconds

Speedup = Sequential Instructions Execution Time/Parallel Instructions Execution Time
Speedup = 4/3 = 1.3

Max Speedup = 1/(1-0.5)+(0.5/1.3)
Max Speedup = 8.6 
```

The overall speed up achieved from Parallelizing the program will be max around 86 %, the limitations
that I faced was the size of the data, I think the parallel solution will be much more effective when there
will be a large amount of data to process where the different threads can work on different parts of the
large amount of that data thus giving us lesser execution time, Apart from this the manual testing I did
also showed me quite similar results with respect to execution time, as my experience is quite less in
this particular type of work, I had a slight thought that It could be the system that is overpowered for
this type of problem, but to me I noticed slight differences so I would say that my solution did worked as
parallel as I intended it would. There will be some overheads of course because in my solution I am
waiting for threads to die so that I can have control of the global variable and read its value. Therefore, I
conclude my results by saying that I noticed a slight 1 or 2 milliseconds of different between each of the
solutions I made.


## Pseudo Code

**1. Main Class**

```
Initialize sum = 0.
Initialize threadsList = []
Initialize numberOfThreads=
Initialize increment = 180 / numberOfThreads
Initialize start = 0.00, end = start
Clock The Start Time
Loop From 1 till numberOfThreads
do
add the value of end with increment
create Solver Thread class with start,end and threadNumber
add the value of start with increment
start the thread
push the thread reference into the threadsList
end
Loop From 0 till threadsList’s length
do
get thread from threadsList
wait for each thread to close
end
Print sum
Clock the End Time
Initialize executionTime = Start Time – End Time
Print execution time
```
**2. Thread Class**

```
Defining StartFrom,endTo,ThreadNumber
Initializing LocalSum = 0
Creating Lock Object
Overriding Default Constructor as to take StartFrom,endTo and ThreadNumber as Parameters
AND assigning their values to the defined variables
Overriding Default run method as
Loop from StartFrom to endTo
do
if i is not 0.0 Then LocalSum adds the value of cosec(i)+sec(i)
end
Synchronize sum with lock object
Adding LocalSum to sum (defined in main class)
```

## Flowchart


<a href="https://ibb.co/SBqL64J"><img src="https://i.ibb.co/89FhmLX/C2.png" alt="C2" border="0"></a>


## References

_Flynn's Taxonomy_. (2022, Octobor 9th). Retrieved from Wikipedia:
https://en.wikipedia.org/wiki/Flynn%27s_taxonomy

John. (2021, June 28). _Coding with John_. Retrieved from Youtube:
https://www.youtube.com/watch?v=r_MbozD32eo

_Volatile Keyword in Java_. Retrieved from Javatpoint: https://www.javatpoint.com/volatile-keyword-in-
java


