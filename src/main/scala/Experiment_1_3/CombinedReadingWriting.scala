package Experiment_1_3

import java.io.File
import Streams.{InputStream, OutputStream}

class CombinedReadingWriting(folderAddress : String) {

  val folder = new File(folderAddress)
  var stringBuffer: StringBuffer = null

  rrMerge(2)

  def rrMerge(f: Int): Unit = {
    if(folder.exists() && folder.isDirectory){
      val fileArray: Array[String] = folder.listFiles(_.isFile).
        filter(_.getName.endsWith(".csv")).
        map(i => folderAddress + i.getName)

      val inputArray: Array[InputStream] = new Array[InputStream](f)

      val outputStream = new OutputStream(new File("src/main/resources/readLineWriteLineFile.csv"))

      outputStream.create

      for(i <- 0 to f-1) {
        val fName = new File(fileArray(i)) // Get File.
        inputArray(i) = new InputStream(fName)
        inputArray(i).open
      }

      // use futures
      var while_var = true
      while (while_var) {
        for (i <- 0 to f - 1) {
          stringBuffer = inputArray(i).readLine
          if(!inputArray(i).endOfStream) {
            outputStream.writeLine(stringBuffer.toString) //works
          }
          while_var = while_var & inputArray(i).endOfStream
        }
        while_var = !while_var
      }

      // Close Inputfiles.
      for(i<- 0 to f-1) {
        inputArray(i).close
      }

      outputStream.close
    }
  }
}
