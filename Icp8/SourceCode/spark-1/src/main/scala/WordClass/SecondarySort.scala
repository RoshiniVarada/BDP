
package WordClass

import org.apache.spark._

object SecondarySort {
  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir","D:\\UMKC\\PB\\Project\\spark-2.4.5-bin-hadoop2.7" )
    val conf = new SparkConf().setAppName("Spark - Secondary Sort").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val personRDD = sc.textFile("inputsort")
    val pairsRDD = personRDD.map(_.split(",")).map { k => ((k(0) + "-" + k(1)),k(3)) }

    val numReducers = 2;

    // val listRDD = pairsRDD.groupByKey(1).mapValues(iter => iter.toList.sortBy(r => r))
    val listRDD = pairsRDD.groupByKey(1)
      .mapValues(iter => "[" + iter.toArray.sortBy(r => r).reverse.mkString(",") + "]")
    listRDD.foreach {
      println
    }

    val x = listRDD.partitionBy(new HashPartitioner(2))

    x.saveAsTextFile("outputsort")

  }
}
