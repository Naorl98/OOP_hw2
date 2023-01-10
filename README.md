# hw2 - 318664190_209133826

the arthurs:
-Naor Ladani - 318664190
-Itamar Cohen - 209133826

#Divied to two parts - Part1 first and Part2 after.

## Part1 - Count file's lines Description


### Classes:
#### myCallable
implements "Callable<Integer>" interface,and save String object.
The class get name of a file.
The class is specific callable that count the lines of fileand return the count.
```
$ String name - Save the file's name.
```

##### Methods:
```
$ myCallable - Constructor method that get name of a file and save in the variable name.
$ call - Count the lines in the file by using variable name and return the count.
```
#### myThread
extend "Thread" ,and save String object.
The class get name of a file.
The class is specific callable that count the lines of file.
```
$ int linesCount - Save the count lines of the file.
```

##### Methods:
```
$ myThread - Constructor method that get name of a file and use super with the name.
$ getLines - return the variable linesCount. 
run - Count the lines in the file by get the name from this and put the count in linesCount.
```
#### Ex2_1

##### Methods:

createTectFiles:
```
The method create n text files, each file with another random number of lines. 
variables:
```
$ int n - the number of files
$ int seed - the minimun number of random
$ int bound - the maximon number of random
```
Return - Array of the files's names
```

getNumOfLines:
```
The method Count the lines of all the files with the normal way.
variables:
```
$ String[] fileNames - Array of the files's names
```
Return - The count of lines
```

getNumOfLinesThreads:
```
The method Count the lines of all the files with thread.
variables:
```
$ String[] fileNames - Array of the files's names
```
Return - The count of lines
```

getNumOfLinesThreadPool:
```
The method Count the lines of all the files with threadPool and use callable.

variables:
```
$ String[] fileNames - Array of the files's names
```
Return - The count of lines
```

main:
```
The method use the Ex2_1 class.

Creat files with the function createTextFiles and save the string array.

Use the methods with the same array and counting time of each method:

```
$ getNumOfLines
$ getNumOfLinesThreads
$ getNumOfLinesThreadPool
```
Print:
for each file print the his name and number of lines.

for each method print the time that take to the method to calulted the count
and print the count.
```

### Diagram:

![Part1Diagram](https://user-images.githubusercontent.com/118805710/211622143-5cbbc9b1-93ec-4688-84c8-6f4403551ab7.png)


in Part to we have 2 classes - Task, CustmoExecutor, TaskFuture( in CustomExecutor ).

### Classes:

#### Task
implements "Comparable" interface.
 the class is callable class with priority option

```
$ myFunc - private Callable variable that save the Callable object that we got in the constructor.
$ priority - private int that save the priority value.
$ tType - private TaskType that save the type of the Task.

```
##### Methods:
```
$ Task - Constructor method that get Callable, TaskType, int Priority variables.
$ getPriority - Return the variable priority.
createTask - Factory method that get Callable and TaskType variables and crate Task object 
with the variables that he got.
return the Task object.
The class have one more createTask that get only Callable object and set TaskType to "OTHER".
$ compareTo - Override method that cast the Task class to be a comparable class,
the method get Task object and compare the object with her self of the priority variable.
$ call - call to myFunc's call method and return the return value.
```

#### CustomExecutor
extend "ThreadPoolExecutor".
 the class is ThreadPool class with priority option.
 
 #### TaskFuture - Adapter  - class in CustomExcutor
 extend FutureTask, implement Comparable.
 it's a FutureTask class that comparable and have a priority option.
 
 ```
$ priority - private int that save the priority value.

##### Methods:
```
$ TaskFuture - Constructor method that get Callable ans Int variables.
Use Super with the Callable variable
the set the int variable in this.priority 
$ getPriority - Return the variable priority.
$ toString - Return The FutureTask properties plus priority value.
$ compareTo - Override method that cast the Task class to be a comparable class,
the method get TaskFuture object and compare the object with her self of the priority variable.
$ printPriority - return string value of the priority.
```
```

#### return to CustomExecutor:

```
$ int[] priorties - private array that save all the priorities of the task that in the queue
$ priority - private int that save the priority value.
$ tType - private TaskType that save the type of the Task.

```
###### Methods:
```
$ CustomExecutor - Constructor method that use super:
     *corePoolSize = half of the available processors
     * MaximumPoolSize = the available processors -1
     * KeepAliveTime = 300 Millisecond
     * TimeUnit = Millisecond
     * Queue = new PriorityBlockingQueue
     
$ newTask -Cast Task object to TaskFuture object.
$ getCurrentMax   
     * Search of the biggest priority:
     * start from 0-9, if the value in cell in bigger than zero,
     * so it's mean that we have task with this value of priority in the queue.
     * @return The index of the first cell that his value is bigger than zero plus one = (index        +1), if the array is empty return zero.
     
$ submitTask -
      * Enter the priority of the task to the suitable cell
     * Cast task to a TaskFuture with newTask method
     * Ececute the new TaskFuture
     * @param task - Task object
     * @return The new TaskFuture object

$ submit - The class have three type of submit:

  $ First - Get Task object and return the return value that get from call the method 
  sumbitTask with the Task object.
  $ Second - Get Callable object and TastType, create Task object and return the return value     that get from call the method sumbitTask with the Task object.
  $ Third - Get Callable object, create Task object and return the return value that get from
  call the method sumbitTask with the Task object.

$ gracefullyTerminate - Do super.shutdown and super.awaitTremination because we need to wait to all the task to finish.

$ beforeExecute -  
     * Calculated the max priority.
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     * execution completed normally
$ getQueueP - return super.getQueue

###Diagram:

![Part2Diagram](https://user-images.githubusercontent.com/118805710/211622293-0e1e729f-d537-4c91-9087-6673458f7b51.png)
