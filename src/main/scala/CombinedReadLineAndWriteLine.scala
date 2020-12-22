import java.io.File

import Streams.{InputStream, OutputStream}


object CombinedReadLineAndWriteLine extends App {
  val folderPath: String="src/main/resources/sampleFiles/"
  val file1 = new File(folderPath)
  var strBuff:StringBuffer =null
  rrMerge(2)

  def rrMerge(f:Int):Unit={

    if(file1.exists() && file1.isDirectory){
      val fileArray: Array[String] = file1.listFiles(_.isFile).
        filter(_.getName.endsWith(".csv")).
        map(i=>folderPath + i.getName)

      var inputArray: Array[InputStream] = new Array[InputStream](f)

      val outputStream = new OutputStream("src/main/resources/readLineWriteLineFile.csv")
      outputStream.create()

      for(i<- 0 to f-1) {
        val fName = new File(fileArray(i))
        inputArray(i) = new InputStream(fName)
        inputArray(i).open()
      }

      var while_var = true
      while (while_var) {
        for (i <- 0 to f - 1) {
          strBuff = inputArray(i).readLine()
          if(!inputArray(i).endOfStream) {
            outputStream.writeLine(strBuff.toString) //works
          }
          while_var = while_var & inputArray(i).endOfStream
        }
        while_var = !while_var
      }
      for(i<- 0 to f-1) {
        inputArray(i).close()
      }
      outputStream.close
    }
  }

}
