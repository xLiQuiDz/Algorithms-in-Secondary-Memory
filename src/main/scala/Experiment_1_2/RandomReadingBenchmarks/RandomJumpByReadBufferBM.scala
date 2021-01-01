package Experiment_1_2.RandomReadingBenchmarks

import Benchmark.Benchmark
import Experiment_1_2.RandomReading
import java.io.File

case class RandJumpByReadBufferBM(file: File, j:Int) extends Benchmark{

  override var repeatIndex: Int = 0

  // Run benchmark
  def benchmark: Unit = {
    while (repeatIndex < REPEATS) {
      startTime = System.nanoTime
      length = RandomReading(file).randJumpByReadBuffer(j)
      endTime = System.nanoTime

      val duration = (endTime - startTime) / 100000

      printIntermediateResult("Calculating length by \"randJumpByReadBufferSize\" Function takes: " + duration + "ms")

      if (repeatIndex >= WARMUP) durations.add(duration)
      repeatIndex += 1
    }

    //Report Result
    averageTime = calculateAvgDuration(durations)
    printResults(WARMUP, REPEATS, averageTime, length, "line")
  }
}

