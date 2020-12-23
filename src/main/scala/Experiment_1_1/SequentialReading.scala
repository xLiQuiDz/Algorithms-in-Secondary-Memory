package Experiment_1_1

import Streams.InputStream
import java.io.File

case class SequentialReading(file: File){

  val inputStream: InputStream = new InputStream(file)

  // Calculate length by readCharacter.
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

  // Calculate length by readLine.
  def LengthByReadBuffer(): Long = {
    inputStream.open
    var count = 0
    while (!inputStream.endOfStream) {
      var line = inputStream.readLine
      count += line.length
    }
    inputStream.close
    count
  }

  // Calculate length by readCharacterWithBuffer.
  def LengthByReadBufferSize(bufferSize: Int): Long = {
    inputStream.open
    var count = 0
    while (!inputStream.endOfStream) {
      inputStream.setBufferSize(bufferSize) // Specify buffer size.
      var line = inputStream.readCharacterWithBuffer
      count += line.length
    }
    inputStream.close
    count
  }

  // Calculate length by readFromMappedMemory.
  def LengthByMappedMemory(bufferSize: Int): Long = {
    inputStream.open
    var count = 0
    while (!inputStream.endOfStream) {
      var line = inputStream.readFromMappedMemory(bufferSize)
      count += line.length
    }
    inputStream.close
    count
  }
}