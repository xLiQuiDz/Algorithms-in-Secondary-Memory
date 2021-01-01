package Experiment_1_1.SequentialReadingBenchmarks

import Benchmark.Benchmark
import Experiment_1_1.SequentialReading
import java.io.File

case class LengthByMappedMemoryBM(file: File, bufferSize: Int) extends Benchmark{

  override var repeatIndex: Int = 0

  // Run benchmark.
  def benchmark: Unit = {
    while (repeatIndex < REPEATS) {
      startTime = System.nanoTime // Start time.
      length = SequentialReading(file).LengthByMappedMemory(bufferSize)
      endTime = System.nanoTime // Stop time.

      val duration = (endTime - startTime) / 100000

      printIntermediateResult("Calculating length of File by \"LengthByReadBufferSize\" Function takes: " + duration + "ms")

      if (repeatIndex >= WARMUP) durations.add(duration)
      repeatIndex += 1
    }

    // Report Result.
    averageTime = calculateAvgDuration(durations)
    printResults(WARMUP, REPEATS, averageTime, length, "file")
  }
}
