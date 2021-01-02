package Experiment_1_4

import java.io.File

object Benchmarks extends App{

  val resourceFolderPath: String="src/main/resources/aka_name.csv"
  val file1 = new File(resourceFolderPath)
  val bm1 = MultiwayMergeBenchmarks.MultiwayMergeBM(file1,1,10000000,20)
  bm1.benchmark

}
