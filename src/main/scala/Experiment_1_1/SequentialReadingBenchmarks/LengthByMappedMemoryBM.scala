package Experiment_1_1.SequentialReadingBenchmarks

import Benchmark.Benchmark
import Experiment_1_1.SequentialReading

case class LengthByMappedMemoryBM(fileAddress: String, bufferSize: Int) extends Benchmark{

  override var repeatIndex: Int = 0
  def benchmark: Unit = {

    //Run benchmark
    while (repeatIndex < REPEATS) {
      startTime = System.nanoTime
      length = SequentialReading(fileAddress).LengthByMappedMemory(bufferSize)
      endTime = System.nanoTime


      val duration = (endTime - startTime) / 100000
      printIntermediateResult("Calculating length of File by \"LengthByReadBufferSize\" Function takes: " +
        duration + "ms")
      if (repeatIndex >= WARMUP) durations.add(duration)
      repeatIndex += 1
    }
    //Report Result
    averageTime = calculateAvgDuration(durations)
    printResults(WARMUP, REPEATS, averageTime, length, "file")
  }


}
