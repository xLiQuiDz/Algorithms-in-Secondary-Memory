package Experiment_1_3

import Streams._
import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

case class CombinedReadingAndWriting(inputFiles: Array[File], outputFile: File) {

  var stringBuffer: StringBuffer = new StringBuffer()
  val outputStream = new OutputStream(outputFile)
  var inputStreamsList = new Array[(InputStream, Boolean)](inputFiles.length)

  outputStream.create

  // Maps array with file to array with input stream.
  inputFiles.foreach(file => {
    val inputStream = new InputStream(file)
    inputStream.open
    inputStreamsList :+ (inputStream, false)
    inputStream.setBufferSize(1024)
  })

  // ************************************************************
  // Read character with buffer and write a character.
  // ************************************************************

  def rrmergeReadCharacterWithBufferWriteCharacter : Unit = {
    inputFiles.zipWithIndex.map {
      case (file, index) => Future(readCharacterWithBufferWriteCharacter(file, index))
        .onComplete { case Success(res) => {var allFilesRead = inputStreamsList.forall(tuple => tuple._2 == true)
          if (allFilesRead) outputStream.close
        }
        case Failure(e) => println("failure: " + e.getMessage)
        }
    }
  }

  def readCharacterWithBufferWriteCharacter(file: File, index: Int): Unit = {
    val inputStream = new InputStream(file)
    inputStreamsList(index) = (inputStream, false)
    inputStream.open
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readLine
      outputStream.writeLine(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }

  // ************************************************************
  // Read character with buffer and write line.
  // ************************************************************

  def rrmergeReadCharacterWithBufferWriteLine : Unit = {
    inputFiles.zipWithIndex.map {
      case (file, index) => Future(readCharacterWithBufferWriteLine(file, index))
        .onComplete { case Success(res) => {var allFilesRead = inputStreamsList.forall(tuple => tuple._2 == true)
          if (allFilesRead) outputStream.close
        }
        case Failure(e) => println("failure: " + e.getMessage)
        }
    }
  }

  def readCharacterWithBufferWriteLine(file: File, index: Int): Unit = {
    val inputStream = new InputStream(file)
    inputStreamsList(index) = (inputStream, false)
    inputStream.open
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readLine
      outputStream.writeLine(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }

  // ************************************************************
  // Read character with buffer and write line with buffer.
  // ************************************************************

  def rrmergeReadCharacterWithBufferWriteLineWithBuffer : Unit = {
    inputFiles.zipWithIndex.map {
      case (file, index) => Future(readCharacterWithBufferWriteLineWithBuffer(file, index))
        .onComplete { case Success(res) => {var allFilesRead = inputStreamsList.forall(tuple => tuple._2 == true)
          if (allFilesRead) outputStream.close
        }
        case Failure(e) => println("failure: " + e.getMessage)
        }
    }
  }

  def readCharacterWithBufferWriteLineWithBuffer(file: File, index: Int): Unit = {
    val inputStream = new InputStream(file)
    inputStreamsList(index) = (inputStream, false)
    inputStream.open
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readLine
      outputStream.writeLine(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }
















}

// ************************************************************
// References
// ************************************************************

// https://stackoverflow.com/questions/25086479/read-multiple-text-files-simultaneously-in-scala



