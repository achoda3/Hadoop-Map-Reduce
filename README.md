Project By: Aryann Chodankar <br />
To execute these, make sure to have hadoop configured
You want to first use scala to build
Then add a plugins.sbt under the project directory
and add the line
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.15.0")
Then you can just clean compile and even assembly
For different tasks, just uncomment the one you want and comment the rest
after assembly, a JAR file is produced that takes in two arguments, an input file and an output directory
Use hadoop jar GeneratedFarJar.jar <input.txt> <output/dir>
And it should work as intended :D
https://www.youtube.com/watch?v=tlzeRTLLpuk&ab_channel=BiggusChungus
[youtube link]
"# Hadoop-Map-Reduce" 
