package Experiment_1_4

import java.io.File

object Benchmarks extends App{

  val resourceFolderPath: String="src/main/resources/imdb/company_name.csv"
  val file1 = new File(resourceFolderPath)
  val bm1 = MultiwayMergeBenchmarks.MultiwayMergeBM(file1,1,200000,10)
  bm1.benchmark

}
