package Streams

import java.io.{BufferedWriter, FileWriter}

class OutputStream(fileAddress: String) {

  private var fileWriter: FileWriter = null // Main writer stream.
  private var bufferedWriter: BufferedWriter = null // Used in second readLine.

  var stringBuffer: StringBuffer = null // To show output.

  // Open file for writing.
  def create(): Unit = {
    fileWriter = new FileWriter(fileAddress)
    bufferedWriter = new BufferedWriter(fileWriter)
  }

  // Implementation 1.1.1
  // Write one character to file.
  def writeCharacter(line: StringBuffer): Unit = {
    try {
      var i = 0
      while (i < line.length()) {
        fileWriter.write(line.charAt(i))
        i += 1
      }
      fileWriter.write(System.lineSeparator)
      } catch {
      case _ => throw new Exception("Stream has not been created ...")
    }
  }

  // Implementation 1.1.2
  // Write one line to file.
  def writeLine(line: String): Unit = {
    try{
      bufferedWriter = new BufferedWriter(fileWriter);
      bufferedWriter.write(line)
      bufferedWriter.write(System.lineSeparator)
      bufferedWriter.flush()
    } catch {
      case _ => throw new Exception("Stream has not been created ...")
    }
  }

  def writeLineWithBuffer = ???


  def close: Unit = {
    try {
      fileWriter.close()
      bufferedWriter.close
    } catch {
      case _ => throw new Exception("Stream has not been created ...")
    }
  }
}
