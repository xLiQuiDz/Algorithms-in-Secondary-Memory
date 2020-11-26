import Streams.InputStream
import scala.util.Random
import java.io.{BufferedReader, File, FileReader}

object RandomReading {
  def randjump(f: String, j: Int) : Int = {

    var inputStream = new InputStream(f)
    inputStream.open
    var count = 0
    var p = Random.between(0, 30)
    inputStream.seek(p)


   5
  }

  def main(args: Array[String]): Unit = {


  }
}
