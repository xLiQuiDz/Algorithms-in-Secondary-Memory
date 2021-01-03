package Experiment_1_3

import Streams._
import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

case class CombinedReadingAndWriting(inputFiles: Array[File], outputFile: File, bufferSize: Int = 128*32) {

  var stringBuffer: StringBuffer = new StringBuffer()
  val outputStream = new OutputStream(outputFile)
  var inputStreamsList = new Array[(InputStream, Boolean)](inputFiles.length)

  outputStream.create

  // Maps array with file to array with input stream.
  inputFiles.foreach(file => {
    val inputStream = new InputStream(file)
    inputStream.open
    inputStreamsList :+ (inputStream, false)
    inputStream.setBufferSize(bufferSize)
  })

  // ************************************************************
  // Read character with buffer and write a character.
  // ************************************************************

  def rrmergeReadCharacterWithBufferWriteCharacter: Unit = {
    inputFiles.zipWithIndex.foreach {
      case (file, index) => Future(readCharacterWithBufferWriteCharacter(file, index))
        .onComplete { case Success(res) => {
          val allFilesRead = inputStreamsList.forall(tuple => tuple._2)
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
    inputStream.setBufferSize(bufferSize)
    outputStream.setBufferSize(bufferSize)
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readCharacterWithBuffer
      outputStream.writeCharacterIntoBuffer(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }

  // ************************************************************
  // Read character with buffer and write line.
  // ************************************************************

  def rrmergeReadCharacterWithBufferWriteLine: Unit = {
    inputFiles.zipWithIndex.foreach {
      case (file, index) => Future(readCharacterWithBufferWriteLine(file, index))
        .onComplete { case Success(_) => {
          val allFilesRead = inputStreamsList.forall(tuple => tuple._2)
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
    inputStream.setBufferSize(bufferSize)
    outputStream.setBufferSize(bufferSize)
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readCharacterWithBuffer
      outputStream.writeLine(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }

  // ************************************************************
  // Read character with buffer and write line with buffer.
  // ************************************************************

  def rrmergeReadCharacterWithBufferWriteLineWithBuffer: Unit = {
    inputFiles.zipWithIndex.foreach {
      case (file, index) => Future(readCharacterWithBufferWriteLineWithBuffer(file, index))
        .onComplete { case Success(res) => {
          var allFilesRead = inputStreamsList.forall(tuple => tuple._2)
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
    inputStream.setBufferSize(bufferSize)
    outputStream.setBufferSize(bufferSize)
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readCharacterWithBuffer
      outputStream.writeCharacterIntoBuffer(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }

  // ************************************************************
  // Read character with buffer and write with mapped memory.
  // ************************************************************

  def rrmergeReadCharacterWithBufferWriteWithMappedMemory: Unit = {
    inputFiles.zipWithIndex.foreach {
      case (file, index) => Future(ReadCharacterWithBufferWriteWithMappedMemory(file, index))
        .onComplete { case Success(_) => {
          val allFilesRead = inputStreamsList.forall(tuple => tuple._2 )
          if (allFilesRead) outputStream.close
        }
        case Failure(e) => println("failure: " + e.getMessage)
        }
    }
  }

  def ReadCharacterWithBufferWriteWithMappedMemory(file: File, index: Int): Unit = {
    val inputStream = new InputStream(file)
    inputStreamsList(index) = (inputStream, false)
    inputStream.open
    inputStream.setBufferSize(bufferSize)
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readCharacterWithBuffer
      outputStream.writeInMappedMemory(bufferSize, stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }


  // ************************************************************
  // Read character with mapped memory and write a character.
  // ************************************************************

  def rrmergeReadCharacterWithMappedMemoryWriteCharacter: Unit = {
    inputFiles.zipWithIndex.foreach {
      case (file, index) => Future(ReadCharacterWithMappedMemoryWriteCharacter(file, index))
        .onComplete { case Success(_) => {
          var allFilesRead = inputStreamsList.forall(tuple => tuple._2)
          if (allFilesRead) outputStream.close
        }
        case Failure(e) => println("failure: " + e.getMessage)
        }
    }
  }

  def ReadCharacterWithMappedMemoryWriteCharacter(file: File, index: Int): Unit = {
    val inputStream = new InputStream(file)
    inputStreamsList(index) = (inputStream, false)
    inputStream.open
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readFromMappedMemory(bufferSize)
      outputStream.writeCharacter(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }

  // ************************************************************
  // Read character with mapped memory and write line.
  // ************************************************************

  def rrmergeReadCharacterWithMappedMemoryWriteLine: Unit = {
    inputFiles.zipWithIndex.foreach {
      case (file, index) => Future(ReadCharacterWithMappedMemoryWriteLine(file, index))
        .onComplete { case Success(_) => {
          val allFilesRead = inputStreamsList.forall(tuple => tuple._2)
          if (allFilesRead) outputStream.close
        }
        case Failure(e) => println("failure: " + e.getMessage)
        }
    }
  }

  def ReadCharacterWithMappedMemoryWriteLine(file: File, index: Int): Unit = {
    val inputStream = new InputStream(file)
    inputStreamsList(index) = (inputStream, false)
    inputStream.open
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readFromMappedMemory(bufferSize)
      outputStream.writeLine(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }

  // ************************************************************
  // Read character with mapped memory and write line with buffer.
  // ************************************************************

  def rrmergeReadCharacterWithMappedMemoryWriteLineWithBuffer: Unit = {
    inputFiles.zipWithIndex.foreach {
      case (file, index) => Future(ReadCharacterWithMappedMemoryWriteLineWithBuffer(file, index))
        .onComplete { case Success(_) => {
          val allFilesRead = inputStreamsList.forall(tuple => tuple._2)
          if (allFilesRead) outputStream.close
        }
        case Failure(e) => println("failure: " + e.getMessage)
        }
    }
  }

  def ReadCharacterWithMappedMemoryWriteLineWithBuffer(file: File, index: Int): Unit = {
    val inputStream = new InputStream(file)
    inputStreamsList(index) = (inputStream, false)
    inputStream.open
    outputStream.setBufferSize(bufferSize)
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readFromMappedMemory(bufferSize)
      outputStream.writeCharacterIntoBuffer(stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }

  // ************************************************************
  // Read character with mapped memory and write with mapped memory.
  // ************************************************************

  def rrmergeReadCharacterWithMappedMemoryWriteWithMappedMemory: Unit = {
    inputFiles.zipWithIndex.foreach {
      case (file, index) => Future(ReadCharacterWithMappedMemoryWriteWithMappedMemory(file, index))
        .onComplete { case Success(_) => {
          val allFilesRead = inputStreamsList.forall(tuple => tuple._2)
          if (allFilesRead) outputStream.close
        }
        case Failure(e) => println("failure: " + e.getMessage)
        }
    }
  }

  def ReadCharacterWithMappedMemoryWriteWithMappedMemory(file: File, index: Int): Unit = {
    val inputStream = new InputStream(file)
    inputStreamsList(index) = (inputStream, false)
    inputStream.open
    while (!inputStream.endOfStream) {
      stringBuffer = inputStream.readFromMappedMemory(bufferSize)
      outputStream.writeInMappedMemory(bufferSize, stringBuffer.toString)
    }
    inputStream.close
    inputStreamsList(index) = (inputStream, true)
  }
}


// ************************************************************
// References
// ************************************************************
// https://stackoverflow.com/questions/25086479/read-multiple-text-files-simultaneously-in-scala



