package Experiment_1_3

import Experiment_1_3.CombinedReadingWritingBenchmarks.RoundRobinMergeByReadBufferSizeBM
import Experiment_1_3.CombinedReadingWritingBenchmarks.RoundRobinMergeByMappedMemoryBM
import java.io.File

object Benchmarks extends App {

  val root = "src/main/resources/"
  var inputFile1 = new File(root + "aka_name.csv")
  var inputFile2 = new File(root + "keyword.csv")
  var inputFile3 = new File(root + "person_info.csv")


  var inputFiles = Array(inputFile1, inputFile2, inputFile3)
  var outputFile = new File(root + "test.csv")

  println("----------------------Benchmarking RoundRobinMergeByReadBufferSizeBM---------------------------")
  var bm1 = RoundRobinMergeByReadBufferSizeBM(inputFiles, outputFile)
  bm1.benchmark

    println("----------------------Benchmarking RoundRobinMergeByMappedMemoryBM---------------------------")
    var bm2 = RoundRobinMergeByMappedMemoryBM(inputFiles, outputFile)
    bm2.benchmark

}