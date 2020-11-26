package Streams

import java.io.{BufferedWriter, FileWriter}

class OutputStream(fileAddress: String) {

  var fileWriter: FileWriter = null //main writer stream

  var bufferedWriter: BufferedWriter = null //used in second readLine
  var stringBuffer: StringBuffer = null //to show output


  def create = {
    fileWriter = new FileWriter(fileAddress)
  }

  def writeLine(str: String) = {

    if(fileWriter == null) {
      throw new Exception("Stream has not been created ...")
    }

    fileWriter.write(str)
    fileWriter.write(System.lineSeparator)
  }

  def writeLineByBuffer(str: String) = {

    if(fileWriter == null) {
      throw new Exception("Stream has not been created ...")
    }

    bufferedWriter = new BufferedWriter(fileWriter);
    bufferedWriter.write(str)
    bufferedWriter.write(System.lineSeparator)
    bufferedWriter.flush
  }

  def close = {

    if(fileWriter == null) {
      throw new Exception("Stream has not been created ...")
    }
    fileWriter.close()
  }

  def bufferClose = {

    if(bufferedWriter == null) {
      throw new Exception("Buffer stream has not been created ...")
    }
    bufferedWriter.close
  }

  def writeLineByBuffer(str: String, bufferSize: Int) = ???

}
