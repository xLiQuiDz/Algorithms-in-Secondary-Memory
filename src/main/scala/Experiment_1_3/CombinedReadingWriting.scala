package Experiment_1_3

import Streams._

import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

// https://stackoverflow.com/questions/25086479/read-multiple-text-files-simultaneously-in-scala
class CombinedReadingAndWriting(outputFileAddress: String, inputFilesAddressArrays: Array[String]) {

  var stringBuffer: StringBuffer = new StringBuffer()
  val outputStream = new OutputStream(new File(outputFileAddress))
  var inputStreamsList = new Array[(InputStream, Boolean)](inputFilesAddressArrays.length)

  outputStream.create

  // Maps array with file to array with input stream.
  inputFilesAddressArrays.foreach(fileAddress => {
    val inputStream = new InputStream(new File(fileAddress))
    inputStream.open
    inputStreamsList :+ (inputStream, false)
  })

  def rrmergeByReadBufferSize : Unit = {
    inputFilesAddressArrays.zipWithIndex.map {
      case (fileAddress, index) => Future(readCharacterWithBufferWriteCharacter(fileAddress, index))
        .onComplete { case Success(res) => {val allFilesRead = inputStreamsList.forall(tuple => tuple._2 == true)
          if (allFilesRead) outputStream.close
        }
        case Failure(e) => println("failure: " + e.getMessage)
        }
    }
  }








  // Best result from 1.1
  // Reading character with buffer --> writing character.
  def readCharacterWithBufferWriteCharacter(fileAddress: String, index: Int): Unit = {
    val inputStream = new InputStream(new File(fileAddress))
    inputStreamsList(index) = (inputStream, false)
    inputStream.open
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readCharacterWithBuffer
      outputStream.writeCharacter(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }















  // Best result from 1.2
  // Reading from mapped memory
  def readCharacterWithBufferWriteLine(fileAddress: String, index: Int): Unit = {
    val inputStream = new InputStream(new File(fileAddress))
    inputStreamsList(index) = (inputStream, false)
    inputStream.open
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readCharacterWithBuffer
      outputStream.writeCharacter(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }
}

