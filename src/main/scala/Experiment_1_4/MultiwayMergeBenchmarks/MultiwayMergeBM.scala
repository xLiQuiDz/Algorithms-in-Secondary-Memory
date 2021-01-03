package Experiment_1_4.MultiwayMergeBenchmarks

import java.io.File

import Benchmark.Benchmark
import Experiment_1_4.MultiwayMerge

case class MultiwayMergeBM(f: File, k: Int, M: Int, d: Int) extends Benchmark{

  override var repeatIndex: Int = 0

  // Run benchmark.
  def benchmark: Unit = {
    while (repeatIndex < REPEATS) {
      startTime = System.nanoTime // Start time.
      //MultiwayMerge(f,k,M,d)
      //f -> the file to be merged
      //k -> column number which is to be sorted
      //M -> number of lines to be divided and sorted from total number of data N
      //d -> number of streams to be merged from the queue
      MultiwayMerge(f, k, M, d).extsort()
      endTime = System.nanoTime // Stop time.

      val duration = (endTime - startTime) / 1000000

      printIntermediateResult(s"Merge sort of ${f.getAbsolutePath} with k:${k}, M:${M}, d:${d} takes: " + duration + "ms")

      if (repeatIndex >= WARMUP) durations.add(duration)
      repeatIndex += 1
    }

    // Report Result.
    averageTime = calculateAvgDuration(durations)
    printResults(WARMUP, REPEATS, averageTime, length, "file")
  }
}