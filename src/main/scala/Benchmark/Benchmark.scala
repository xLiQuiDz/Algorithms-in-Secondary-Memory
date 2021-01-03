package Benchmark

import java.util

abstract class Benchmark{

  var WARMUP = 2 // Warm-up round.
  var REPEATS = 10 // Total rounds.

  var length: Long = 0
  var repeatIndex: Int
  val durations = new util.ArrayList[Long]

  var startTime: Long = 0
  var endTime: Long = 0
  var averageTime: Long = 0

  def calculateAvgDuration(durations: util.ArrayList[Long]): Long = {
    var temp: Long = 0
    for (i <- 0 until durations.size) {
      temp += durations.get(i) // Get duration at episode i.
    }
    temp / durations.size
  }

  def printResults(warmUp: Int, reps: Int, avg: Long, length: Long, parameterName:String): Unit = {
    System.out.println()
    System.out.println("--------------------------------FINAL RESULTS----------------------------------")
    System.out.println("Length of " + parameterName + ": " + length)
    System.out.println("Total runs: " + reps)
    System.out.println("Warm-up runs: " + warmUp)
    System.out.println("Average \"RUN TIME\" Duration: " + avg + "ms")
    System.out.println("-------------------------------------------------------------------------------")
  }

  def printIntermediateResult(message: String): Unit = {
    if(WARMUP -1 >=  repeatIndex ) println("Round " + (repeatIndex + 1) + ": (WARM UP)")
    else System.out.println("Round " + (repeatIndex + 1) + ":")
    println(message)
    println("-------------------------------------------------------------------------------")
  }
}