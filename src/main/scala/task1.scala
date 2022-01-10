/*package com.gkatzioura.scala

import java.lang.Iterable
import java.util.StringTokenizer

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import scala.collection.JavaConverters._

/**
 * Created by gkatzioura on 2/14/17.
 */
package object WordCount {

  class TokenizerMapper extends Mapper[Object, Text, Text, IntWritable] {

    val one = new IntWritable(1)
    val TypeWord = new Text()
    val word2 = new Text()
    val TimeWord = new Text()
    val intervalCheck = new Text()

    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
      val itr = new StringTokenizer(value.toString)
      while (itr.hasMoreTokens()) {
        TimeWord.set(itr.nextToken())
        // If Time message
        if(TimeWord.getLength()==12){
          if(TimeWord.charAt(2)==':' && TimeWord.charAt(5)==':' && TimeWord.charAt(8)=='.'){
            word2.set(itr.nextToken())
            TypeWord.set(itr.nextToken())
            // Concatanate with the Type for grouping by key
            intervalCheck.set(((TimeWord.toString()).substring(0,9)).concat(TypeWord.toString()))
            if(TypeWord.toString=="INFO"||TypeWord.toString=="ERROR"||TypeWord.toString=="DEBUG"||TypeWord.toString=="WARN")
              context.write(intervalCheck, one)
          }
        }
      }

    }
  }

  class IntSumReader extends Reducer[Text,IntWritable,Text,IntWritable] {
    override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      var sum = values.asScala.foldLeft(0)(_ + _.get)
      context.write(key, new IntWritable(sum))
    }
  }


  def main(args: Array[String]): Unit = {
    val configuration = new Configuration
    configuration.set("mapred.textoutputformat.separator", ",")
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

