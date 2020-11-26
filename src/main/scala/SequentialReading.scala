import java.io.{BufferedReader, File, FileReader}

import Streams.InputStream

object SequentialReading {

  def Length(f : String) : Unit = {
    var count = 0

    var inputStream = new InputStream(f)
    inputStream.open

    while(!inputStream.endOfStream(inputStream.bufferedReader)) {
      count += 1
    }
    println(count)
  }


  def main(args: Array[String]): Unit = {

    Length("src/main/resources/sample.txt")

    var inputStream = new InputStream("src/main/resources/sample.txt")
    inputStream.open

    // Go forward pointer for 40 position

    println(inputStream.seek(2))
    inputStream.readLineByBuffer
    println(inputStream.seek(2))

    inputStream.close

  }


}
