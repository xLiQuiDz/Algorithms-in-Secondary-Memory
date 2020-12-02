import Streams.InputStream
import scala.util.Random
import java.io.{BufferedReader, File, FileReader}

object RandomReading {
  def randjump(f: File, j: Int) : Unit = {

    var count = 0
    var inputStream = new InputStream(f)
    inputStream.open

    for (i <- 0 until j) {
      var p = Random.between(0, 10)
      inputStream.seek(p)
    }
    
  }

  def main(args: Array[String]): Unit = {

    var file = new File("src/main/resources/sample.txt")
    print(file.length())





  }
}
