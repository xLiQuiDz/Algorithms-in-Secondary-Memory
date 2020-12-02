import java.io.{BufferedReader, File, FileReader, RandomAccessFile}

import Streams.InputStream

object SequentialReading {

  def Length(file : File) : Unit = {

    var counter = 0
    var inputStream = new InputStream(file)
    inputStream.open

    while (true) {
      var line = inputStream.readLine
      counter += line.length()
    }

    inputStream.close
  }

  def main(args: Array[String]): Unit = {

    var file = new File("src/main/resources/sample.txt")
    Length(file)
  }
}

