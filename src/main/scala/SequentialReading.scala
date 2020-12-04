import java.io.{BufferedReader, File, FileReader, RandomAccessFile}

import Streams.InputStream

object SequentialReading {

  // High order function that takes readln as function.
  def Length(file : File) : Long = {

    var inputStream = new InputStream(file)
    inputStream.open()
    var count = 0

    while (!inputStream.endOfStream) {
      var line = inputStream.readLine()
      // println(counter + ": " + line + " length: " + line.length())
      count += line.length()
    }
    inputStream.close
    count
  }

  def main(args: Array[String]): Unit = {

    var file = new File("src/main/resources/sample.txt")
    println(Length(file))

  }
}

