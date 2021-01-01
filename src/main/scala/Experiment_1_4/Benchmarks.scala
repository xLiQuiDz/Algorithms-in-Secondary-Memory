package Experiment_1_4

import java.io.File

object Benchmarks extends App{

  val resourceFolderPath: String="src/main/resources/imdb/aka_title.csv"
  val file1 = new File(resourceFolderPath)
  val bm1 = MultiwayMergeBenchmarks.MultiwayMergeBM(file1,2,10000,10)
  bm1.benchmark

}
