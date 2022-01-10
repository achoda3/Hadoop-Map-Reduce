/*package com.gkatzioura.scala

import HelperUtils.{CreateLogger, ObtainConfigReference}
import java.lang.Iterable
import java.util.StringTokenizer
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.io.WritableComparable
import org.apache.hadoop.io.WritableComparator
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters.*
import scala.collection.mutable
import scala.util.matching.Regex

/**
 * Created by gkatzioura on 2/14/17.
 */
package object WordCount {

  val config = ObtainConfigReference("randomLogGenerator") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  class TokenizerMapper extends Mapper[Object, Text, Text, IntWritable] {
    val one = new IntWritable(1)
    val word = new Text()
    val word2 = new Text()
    val word3 = new Text()
    val intervalCheck = new Text()
    val pattern = new Regex(config.getString("randomLogGenerator.Pattern"))
    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
      val itr = new StringTokenizer(value.toString)
      while (itr.hasMoreTokens()) {
        word3.set(itr.nextToken())
        if(word3.getLength()==12){
          // Finds the Time String
          if(word3.charAt(2)==':' && word3.charAt(5)==':' && word3.charAt(8)=='.'){
            word2.set(itr.nextToken())
            word.set(itr.nextToken())
            intervalCheck.set((word3.toString()).substring(0,9))
            // If its the error case
            if(word.toString=="ERROR")
              context.write(intervalCheck, one)

          }
        }
      }

    }
  }

  // A map that uses the previous results ouput value as its key, allowing us to order by value rather than key
  class reverseMapper extends Mapper[Object, Text, Text, Text] {
    val logger = LoggerFactory.getLogger(classOf[reverseMapper])
    val one = new IntWritable(1)
    val word2 = new Text()
    val word3 = new Text()
    val intervalCheck = new Text()
    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, Text]#Context): Unit = {
      val itr = new StringTokenizer(value.toString)
      while (itr.hasMoreTokens()) {
        word3.set(itr.nextToken())
        word2.set(itr.nextToken())
        logger.info(word3.toString)
        logger.info("word")
        logger.info(word2.toString)
        intervalCheck.set((word2.toString()).concat(word3.toString()))
        context.write(intervalCheck,word3)
      }
    }
  }

  // Default Sum Reader
  class IntSumReader extends Reducer[Text,IntWritable,Text,IntWritable] {
    override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      var sum = values.asScala.foldLeft(0)(_ + _.get)
      context.write(key, new IntWritable(sum))
    }
  }

  // Reverse Reader
  class outRevReader extends Reducer[Text,Text,Text,Text] {
    override def reduce(key: Text, values: Iterable[Text], context: Reducer[Text, Text, Text, Text]#Context): Unit = {
      context.write(values.asScala.head, key)
    }
  }

  def main(args: Array[String]): Unit = {
    // Job 1
    val configuration = new Configuration
    configuration.set("mapred.textoutputformat.separator", " ")
    val job = Job.getInstance(configuration,"word count")
    job.setJarByClass(this.getClass)
    job.setMapperClass(classOf[TokenizerMapper])
    job.setCombinerClass(classOf[IntSumReader])
    job.setReducerClass(classOf[IntSumReader])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])
    FileInputFormat.addInputPath(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))
    job.waitForCompletion(true)

    //Job 2 - Job created for reading the output of the first job and using it as input
    // This allows us to instead of sort by key (Time Var), to sort by the Value (Count)
    val configuration1 = new Configuration
    configuration1.set("mapred.textoutputformat.separator", ",")
    val job1 = Job.getInstance(configuration1,"Reverse Order")
    job1.setJarByClass(this.getClass)
    job1.setMapperClass(classOf[reverseMapper])
    job1.setCombinerClass(classOf[outRevReader])
    job1.setReducerClass(classOf[outRevReader])
    job1.setOutputKeyClass(classOf[Text])
    job1.setOutputValueClass(classOf[Text])
    FileInputFormat.addInputPath(job1, new Path(args(1)))
    FileOutputFormat.setOutputPath(job1, new Path(args(2)))
    System.exit(if(job1.waitForCompletion(true))  0 else 1)
  }

}
*/
