package Experiment_1_3

import Experiment_1_1.Benchmarks.file
import Experiment_1_1.SequentialReadingBenchmarks.LengthByMappedMemoryBM
import Experiment_1_2.Benchmarks.bm2
import Experiment_1_3.CombinedReadingWritingBenchmarks.RoundRobinMergeByReadBufferSizeBM

import java.io.File

object Benchmarks extends App {

  var inputFile1 = new File("src/main/resources/sample1.csv")
  var inputFile2 = new File("src/main/resources/sample2.csv")
  var inputFile3 = new File("src/main/resources/sample3.csv")

  var inputFiles = Array(inputFile1, inputFile2, inputFile3)
  var outputFile = new File("src/main/resources/test.csv")

  println("----------------------Benchmarking RoundRobinMergeByReadBufferSizeBM---------------------------")
  var bm1 = RoundRobinMergeByReadBufferSizeBM(inputFiles, outputFile)
  bm1.benchmark









}