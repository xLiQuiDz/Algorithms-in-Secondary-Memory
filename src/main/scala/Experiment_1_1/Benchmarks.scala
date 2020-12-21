package Experiment_1_1

import Experiment_1_1.SequentialReadingBenchmarks.{LengthByMappedMemoryBM, LengthByReadBufferBM, LengthByReadBufferSizeBM, LengthByReadCharacterBM}

object Benchmarks extends App {
  val fileAddress = "src/main/resources/sample.txt"

  System.out.println("----------------------Benchmarking LengthByReadCharacterBM---------------------------")
  val bm1 = LengthByReadCharacterBM(fileAddress)
  bm1.benchmark

  System.out.println("----------------------Benchmarking LengthByReadBufferBM---------------------------")
  val bm2 = LengthByReadBufferBM(fileAddress)
  bm2.benchmark

  System.out.println("----------------------Benchmarking LengthByReadBufferSizeBM---------------------------")
  val bm3 = LengthByReadBufferSizeBM(fileAddress, 1024)
  bm3.benchmark

  System.out.println("----------------------Benchmarking LengthByMappedMemoryBM---------------------------")
  val bm4 = LengthByMappedMemoryBM(fileAddress, 1024)
  bm4.benchmark

}
