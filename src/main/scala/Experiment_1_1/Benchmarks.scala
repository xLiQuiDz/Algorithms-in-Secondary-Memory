package Experiment_1_1

import Experiment_1_1.SequentialReadingBenchmarks._
import java.io.File

object Benchmarks extends App {
  val file = new File("src/main/resources/sample1.csv")

  println("----------------------Benchmarking LengthByReadCharacterBM---------------------------")
  val bm1 = LengthByReadCharacterBM(file)
  bm1.benchmark

  println("----------------------Benchmarking LengthByReadBufferBM---------------------------")
  val bm2 = LengthByReadBufferBM(file)
  bm2.benchmark

  println("----------------------Benchmarking LengthByReadBufferSizeBM---------------------------")
  val bm3 = LengthByReadBufferSizeBM(file, 1024)
  bm3.benchmark

  println("----------------------Benchmarking LengthByMappedMemoryBM---------------------------")
  val bm4 = LengthByMappedMemoryBM(file, 1024)
  bm4.benchmark
}
