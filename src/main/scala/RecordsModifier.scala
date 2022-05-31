import org.apache.spark.sql.SparkSession

object RecordsModifier extends App {

  val spark = SparkSession
    .builder
    .appName("KafkaToSpark")
    .master("local[*]")
    .getOrCreate

import spark.implicits._

val df = spark
  .readStream
  .format("kafka")
  .option("subscribe", "topic")
  .option("kafka.bootstrap.servers", ":9092")
  .load()
  .withColumn("value", ($"value"))
  .writeStream
  .format("console")
  .start()
  .awaitTermination()
}
