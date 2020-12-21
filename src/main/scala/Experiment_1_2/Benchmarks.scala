package Experiment_1_2

import Experiment_1_2.RandomReadingBenchmarks.{RandJumpByMappedMemoryBM, RandJumpByReadBufferBM, RandJumpByReadBufferSizeBM, RandJumpByReadCharacterBM}

object Benchmarks extends App{
  val fileAddress = "src/main/resources/sample.txt"

  System.out.println("----------------------Benchmarking RandJumpByReadCharacterBM---------------------------")
  val bm1 = RandJumpByReadCharacterBM(fileAddress, j = 10)
  bm1.benchmark

  //  System.out.println("----------------------Benchmarking RandJumpByReadBufferBM---------------------------")
  //  val bm2 = RandJumpByReadBufferBM(fileAddress, j = 10)
  //  bm2.benchmark
  //
  //  System.out.println("----------------------Benchmarking RandJumpByReadBufferSizeBM---------------------------")
  //  val bm3 = RandJumpByReadBufferSizeBM(fileAddress, j = 10, bufferSize =  1024)
  //  bm3.benchmark
  //
  //  System.out.println("----------------------Benchmarking RandJumpByMappedMemoryBM---------------------------")
  //  val bm4 = RandJumpByMappedMemoryBM(fileAddress, j = 10, bufferSize =  1024)
  //  bm4.benchmark
}
