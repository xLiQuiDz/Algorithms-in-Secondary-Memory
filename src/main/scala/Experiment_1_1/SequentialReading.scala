package Experiment_1_1

import Streams.InputStream

import java.io.File

case class SequentialReading(fileAddress: String){

  val inputStream: InputStream = new InputStream(new File(fileAddress))

  def LengthByReadCharacter(): Long = {
    inputStream.open
    var count = 0
    while (!inputStream.endOfStream) {
      var line = inputStream.readCharacter
      count += line.length
    }
    inputStream.close
    count
  }

  def LengthByReadBuffer(): Long = {
    inputStream.open
    var count = 0
    while (!inputStream.endOfStream) {
      var line = inputStream.readLine //72031550
      count += line.length
    }
    inputStream.close
    count
  }

  def LengthByReadBufferSize(bufferSize: Int): Long = {
    inputStream.open
    var count = 0
    while (!inputStream.endOfStream) {
      inputStream.setBufferSize(bufferSize)
      var line = inputStream.readCharacterWithBuffer //72031550
      count += line.length
    }
    inputStream.close
    count
  }

  def LengthByMappedMemory(bufferSize: Int): Long = {
    inputStream.open
    var count = 0
    while (!inputStream.endOfStream) {
      var line = inputStream.readFromMappedMemory(bufferSize) // problem
      count += line.length
    }
    inputStream.close
    count
  }
}
