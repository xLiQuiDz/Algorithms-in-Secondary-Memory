import java.io.File

import Streams.InputStream

import scala.util.Random

object RandomReading {

  // High order function that takes readln as function
  def randjump(file: File, j: Int) : Long = {

    var inputStream = new InputStream(file)
    inputStream.open
    var count = 0

    for (i <- 0 until j) {
      var p = Random.between(0, file.length())
      inputStream.seek(p)

      var line = inputStream.readLine()
      count += line.length()
    }
    count
  }

  def main(args: Array[String]): Unit = {
    
    var file = new File("src/main/resources/sampleFiles/sample.csv")
    println(randjump(file, 5))
  }
}
