/*package com.gkatzioura.scala

import HelperUtils.{CreateLogger, ObtainConfigReference}
import java.lang.Iterable
import java.util.StringTokenizer
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}

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
    //Checks for pattern
    val pattern = new Regex(config.getString("randomLogGenerator.Pattern"))
    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
      val itr = new StringTokenizer(value.toString)
      while (itr.hasMoreTokens()) {
        word3.set(itr.nextToken())
        // Checks if message type
        if(word3.toString=="INFO"||word3.toString=="ERROR"||word3.toString=="DEBUG"||word3.toString=="WARN"){
          word2.set(itr.nextToken())
          word2.set(itr.nextToken())
          word.set(itr.nextToken())
          // Finds the first occurence of the pattern within the actual message
          if((pattern findFirstIn word.toString()) != None){
            one.set(word.getLength)
            // Writes out the type as key so as to group them
            context.write(word3, one)
          }
        }
      }

    }
  }


  class IntSumReader extends Reducer[Text,IntWritable,Text,IntWritable] {
    override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      var sum = values.asScala.foldLeft(0)(_ max _.get)
      context.write(key, new IntWritable(sum))
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
    System.exit(if(job.waitForCompletion(true))  0 else 1)
  }

}
*/