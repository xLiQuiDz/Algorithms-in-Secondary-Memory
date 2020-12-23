import Streams.{InputStream, OutputStream}

import java.io.{BufferedReader, ByteArrayOutputStream, File, FileInputStream, FileReader, RandomAccessFile}
import java.nio.ByteBuffer

object Test extends App {

  var x = new InputStream(new File("src/main/resources/sample1.csv"))
  x.open


  var y = x.readFromMappedMemory(1024)
  var z = x.readFromMappedMemory(1024)

  println(y)
  println(z)

  x.close
}
