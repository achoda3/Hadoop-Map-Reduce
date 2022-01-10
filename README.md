# Hadoop-Map-Reduce
Project By: Aryann Chodankar <br />
To execute these, make sure to have hadoop configured
You want to first use scala to build
Then add a plugins.sbt under the project directory
and add the line
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.15.0")
Then you can just clean compile and even assembly like so: <br>
sbt clean compile assembly <br>
For different tasks, just uncomment the one you want and comment the rest
after assembly, a JAR file is produced that takes in two arguments, an input file and an output directory
Use hadoop jar GeneratedFarJar.jar <input.txt> <output/dir>
And it should work as intended :D
https://www.youtube.com/watch?v=tlzeRTLLpuk&ab_channel=BiggusChungus <br>
The tasks I have created basically perform the following batch operations on the log files: <br>
Task 1: Shows the distribution of different types of messages across predefined time intervals and injected string instances of the designated regex pattern for these log message types <br>
Task 2: computes time intervals sorted in the descending order that contained most log messages of the type ERROR with injected regex pattern string instances <br>
Task 3:  for each message type produces the number of the generated log messages <br>
Task 4:  produces the number of characters in each log message for each log message type that contain the highest number of characters in the detected instances of the designated regex pattern

