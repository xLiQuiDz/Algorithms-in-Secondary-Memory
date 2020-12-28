import Streams.{InputStream, OutputStream}

import java.io.{BufferedReader, ByteArrayOutputStream, File, FileInputStream, FileReader, RandomAccessFile}
import java.nio.ByteBuffer

object Test extends App {

  var x = new InputStream(new File("src/main/resources/sample1.csv"))
  var y = x.readFromMappedMemory(1024)

  println(y)



}
