package Experiment_1_1.SequentialReadingBenchmarks

import Experiment_1_1.SequentialReading
import Streams.InputStream

import java.io.File
import java.util
import Benchmark.Benchmark

case class LengthByReadCharacterBM(file: File) extends Benchmark {

  override var repeatIndex: Int = 0

  // Run benchmark.
  def benchmark: Unit = {
    while (repeatIndex < REPEATS) {
      startTime = System.nanoTime // Start time.
      length = SequentialReading(file).LengthByReadCharacter()
      endTime = System.nanoTime // stop time.

      val duration = (endTime - startTime) / 1000000

      printIntermediateResult("Calculating length of File by \"LengthByReadCharacter\" Function takes: " + duration + "ms")

      if (repeatIndex >= WARMUP) durations.add(duration)
      repeatIndex += 1
    }

    // Report Result.
    averageTime = calculateAvgDuration(durations)
    printResults(WARMUP, REPEATS, averageTime, length, "file")
  }
}
