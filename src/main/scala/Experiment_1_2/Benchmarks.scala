package Experiment_1_2

import Experiment_1_2.RandomReadingBenchmarks.{RandJumpByMappedMemoryBM, RandJumpByReadBufferBM, RandJumpByReadBufferSizeBM, RandJumpByReadCharacterBM}

import java.io.File

object Benchmarks extends App{

  val file = new File("src/main/resources/sample1.csv")

  System.out.println("----------------------Benchmarking RandJumpByReadCharacterBM---------------------------")
  val bm1 = RandJumpByReadCharacterBM(file, j = 10)
  bm1.benchmark

  System.out.println("----------------------Benchmarking RandJumpByReadBufferBM---------------------------")
  val bm2 = RandJumpByReadBufferBM(file, j = 10)
  bm2.benchmark

  System.out.println("----------------------Benchmarking RandJumpByReadBufferSizeBM---------------------------")
  val bm3 = RandJumpByReadBufferSizeBM(file, j = 10, bufferSize =  1024)
  bm3.benchmark

  System.out.println("----------------------Benchmarking RandJumpByMappedMemoryBM---------------------------")
  val bm4 = RandJumpByMappedMemoryBM(file, j = 10, bufferSize =  1024)
  bm4.benchmark
}
