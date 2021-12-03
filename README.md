# Advanced Software Paradigms - Assignment 3 (Concurrency)

## Task

Your application will take three arguments from the command line: **filename**, **square size** and **the processing mode**  
Example: `program somefile.jpg 5 S`

- file name: the name of the graphic file of jpg format (no size constraints)
- square size: the side of the square for the averaging
- processing mode: 'S' - single threaded and 'M' - multi threaded

Task is to show the image, and start performing the following procedure:
- from left to right, top to bottom find the average color for the **(square size) x (square size)** boxes and set the color of the whole square to this average color. You need to show the result by progress, not at once.
- In the multi-processing mode, you need to perform the same procedure in parallel threads. The number of threads shall be selected according to the computer's CPU cores.
There result shall be saved in a `result.jpg` file. The result of the processing shall look like the attached example.

## How to run program

To compile program, run:
```
javac Main.java
```

To execute program all parameters should be passed as command line arguments in the order given in the task description, separated by space. Some example of command line argument values are given below.

- For single threaded mode, run:
```
java Main image.jpg 17 S
```

- For multi threaded mode, run:
```
java Main image.jpg 13 M
```

## Description of program

Rather than some comments made within program files - classes, here general design approach is discussed. Whole program contains 3 files: `Main.java`, `Sequential.java`, `Parallel.java`.

As it can be understood from their names, each class is considered to
do its own job. `Main` class is only responsible for getting command
line arguments, drawing image frame and setting processing mode
according to command. It has `drawImage()`, `singleThreadedProcess()`,
`multiThreadedProcess()` methods, where `drawImage()` is creating 
image frame and redirects program to do `singleThreadedProcess()` 
or `multiThreadedProcess()` depending on processing mode option. 

`singleThreadedProcess()` method creates an instance of `Sequential.class`
with some parameters (check code for them) and runs `process()`
method of it which does pixelation as it is required in task.

`multiThreadedProcess()` method divides whole task (processing whole image)
between threads depending on the number of cores and _start_ each thread. 
Then _join_ them to get the whole processed image as resulting file. 
During creation of threads, instances of `Parallel.class` are instantiated 
with some required parameters and class variables (again check code for them).
In its `run()` method single _Sequential_ process is performed for already 
assigned task for current thread.