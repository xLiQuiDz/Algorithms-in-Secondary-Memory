package Experiment_1_3.CombinedReadingWritingBenchmarks

import Benchmark.Benchmark
import Experiment_1_3.CombinedReadingAndWriting

import java.io.File

case class RoundRobinMergeByReadBufferSizeBM(inputFiles: Array[File], outputFile: File) extends Benchmark {

  override var repeatIndex: Int = 0

  // Run benchmark.
  def benchmark: Unit = {
    while (repeatIndex < REPEATS) {
      startTime = System.nanoTime // Start time.
      CombinedReadingAndWriting(inputFiles, outputFile).rrmergereadCharacterWithBufferWriteCharacter
      endTime = System.nanoTime // Stop time.

      val duration = (endTime - startTime) / 100000

      printIntermediateResult("Calculating length of File by \"RoundRobinMergeByReadBufferSizeBM\" Function takes: " + duration + "ms")

      if (repeatIndex >= WARMUP) durations.add(duration)
      repeatIndex += 1
    }

    // Report Result.
    averageTime = calculateAvgDuration(durations)
    printResults(WARMUP, REPEATS, averageTime, length, "file")
  }

}
