package Experiment_1_2

import Streams.InputStream

import java.io.File
import scala.util.Random

case class RandomReading(fileAddress: String) {
  val file: File = new File(fileAddress)
  val inputStream: InputStream = new InputStream(file)

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
