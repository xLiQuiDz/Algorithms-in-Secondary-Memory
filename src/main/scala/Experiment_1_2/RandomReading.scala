package Experiment_1_2

import Streams.InputStream

import java.io.File
import scala.util.Random

case class RandomReading(file: File) {

  val inputStream: InputStream = new InputStream(file)

  // Calculate length by randJumpByReadCharacter.
  def randJumpByReadCharacter(j: Int): Long = {
    inputStream.open
    var count = 0
    for (i <- 0 until j) {
      var p = Random.between(0, file.length())
      inputStream.seek(p)
      var line = inputStream.readCharacter
      count += line.length
    }
    inputStream.close
    count
  }

  // Calculate length by randJumpByReadBuffer.
  def randJumpByReadBuffer(j: Int): Long = {
    inputStream.open
    var count = 0
    for (i <- 0 until j) {
      var p = Random.between(0, file.length())
      inputStream.seek(p)
      var line = inputStream.readLine
      count += line.length
    }
    inputStream.close
    count
  }

  // Calculate length by randJumpByReadBufferSize.
  def randJumpByReadBufferSize(j: Int, bufferSize: Int): Long = {
    inputStream.open
    var count = 0
    for (i <- 0 until j) {
      var p = Random.between(0, file.length())
      inputStream.seek(p)
      inputStream.setBufferSize(bufferSize)
      var line = inputStream.readCharacterWithBuffer
      count += line.length
    }
    inputStream.close
    count
  }

  // Calculate length by randJumpByMappedMemory.
  def randJumpByMappedMemory(j: Int, bufferSize: Int): Long = {
    inputStream.open
    var count = 0
    for (i <- 0 until j) {
      var p = Random.between(0, file.length())
      inputStream.seek(p)
      var line = inputStream.readFromMappedMemory(bufferSize)
      count += line.length
    }
    inputStream.close
    count
  }
}
