package Streams

import java.io.{BufferedWriter, File, FileOutputStream, FileWriter, RandomAccessFile}
import java.nio.{CharBuffer}
import java.nio.channels.FileChannel
import java.nio.charset.Charset

// 1.2 Writing.
class OutputStream(file: File) {

  private var fileWriter: FileWriter = null // Main writer stream.
  private var bufferedWriter: BufferedWriter = null // Used for writing line.
  private var randomAccessFile: RandomAccessFile = null // Used for seeking a position in file.
  private var fileOutputStream: FileOutputStream = null
  private var fileChannel: FileChannel = null
  private var currentPosition: Int = 0
  private var channelSize: Long = 0
  var allFileWritten: Boolean = false

  // Open file for writing.
  def create: Unit = {
    try {
      fileWriter = new FileWriter(file) // Used in 1.1.1
      bufferedWriter = new BufferedWriter(fileWriter) // Used in 1.1.2 and 1.1.3
      randomAccessFile = new RandomAccessFile(file, "rw") // Used in seek and 1.1.4
      fileOutputStream = new FileOutputStream(file)  // Used in 1.1.4
      fileChannel = randomAccessFile.getChannel
      channelSize = fileChannel.size
      currentPosition = 0
      allFileWritten = false
    } catch {
      case _ => throw new Exception("Exception in creating file ...")
    }
  }

  // Implementation 1.1.1
  // Write one character to file.
  def writeCharacter(line: String): Unit = {
    try {
      var i = 0
      while (i < line.length()) {
        fileWriter.write(line.charAt(i))
        i += 1
      }
      fileWriter.write(System.lineSeparator)
      fileWriter.flush
    } catch {
      case _ => throw new Exception("Stream has not been created ...")
    }
  }

  // Implementation 1.1.2
  // Write one line to file.
  def writeLine(line: String): Unit = {
    try {
      bufferedWriter.write(line)
      bufferedWriter.flush()
      fileWriter.write(System.lineSeparator)
    } catch {
      case _ => throw new Exception("Stream has not been created ...")
    }
  }

  // Implementation 1.1.3
  // Write B character of line into the stream.
  def writeCharacterIntoBuffer(line: String): Unit = {
    try {
      var i = 0
      while (i < line.length()) {
        bufferedWriter.write(line.charAt(i))
        i += 1
      }
      bufferedWriter.flush()
      fileWriter.write(System.lineSeparator)
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Implementation 1.1.4
  def writeInMappedMemory(bufferSize: Int, string: String): Unit = {
    try {
      var newString = string
      var endIndex = 0
      if (string.length > bufferSize) {
        endIndex = bufferSize + currentPosition
        if ((bufferSize + currentPosition) > string.length) endIndex = string.length
        newString = string.substring(currentPosition, endIndex)
        currentPosition = currentPosition + bufferSize
      }

      val mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, currentPosition, bufferSize)
      val charBuffer: CharBuffer = CharBuffer.wrap(newString)
      fileChannel.write(Charset.forName("utf-8").encode(charBuffer))
      mappedByteBuffer.clear

      if (currentPosition >= string.length || bufferSize > string.length)
        allFileWritten = true
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Set buffer size for BufferedReader.
  def setBufferSize(bufferSize: Int): Unit = {
    try {
      bufferedWriter = new BufferedWriter(fileWriter, bufferSize)
    }
    catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  def close: Unit = {
    try {
      fileWriter.close
      bufferedWriter.close
    } catch {
      case _ => throw new Exception("Stream has not been created ...")
    }
  }
}
