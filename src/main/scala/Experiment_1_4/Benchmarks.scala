package Experiment_1_4

import java.io.File

object Benchmarks extends App{

  println("----------------------BenchmarkingMultiwayMergeBM---------------------------")
  val file = new File("src/main/resources/aka_name.csv")
  val bm1 = MultiwayMergeBenchmarks.MultiwayMergeBM(file, 1, 10000000, 20)
  bm1.benchmark
}