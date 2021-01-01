package Streams

import java.io.{BufferedWriter, File, FileOutputStream, FileWriter, RandomAccessFile}
import java.nio.{CharBuffer, MappedByteBuffer}
import java.nio.channels.FileChannel
import java.nio.charset.Charset

class OutputStream(file: File) {

  private var fileWriter: FileWriter = null // Main writer stream.
  private var bufferedWriter: BufferedWriter = null // Used for writing line.
  private var randomAccessFile: RandomAccessFile = null // Used for seeking a position in file.
  private var fileOutputStream: FileOutputStream = null
  private var mappedByteBuffer: MappedByteBuffer = null
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
      fileChannel = randomAccessFile.getChannel // Get file channel in read-write mode.
      channelSize = fileChannel.size
      currentPosition = 0
      allFileWritten = false
    } catch {
      case _ => throw new Exception("Exception in creating file ...")
    }
  }

  // CLose file for writing.
  def close: Unit = {
    try {
      fileWriter.close
      bufferedWriter.close
      randomAccessFile.close
      fileOutputStream.close
      fileChannel.close
      channelSize = 0
      currentPosition = 0
      if(mappedByteBuffer != null) mappedByteBuffer.force
      fileChannel.truncate(file.length())
    } catch {
      case _ => throw new Exception("Stream has not been created ...")
    }
  }

  // ***********************************************************************************
  // Writeln Procedures
  // ***********************************************************************************

  // Implementation 1.1.1
  // Write one character to file.
  def writeCharacter(line: String): Unit = {
    try {
      for (i <- 0 until line.length) {
        fileWriter.write(line.charAt(i))
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
      for (i <- 0 until line.length()) {
        bufferedWriter.write(line.charAt(i))
      }
      bufferedWriter.flush()
      fileWriter.write(System.lineSeparator)
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Implementation 1.1.4
  // Read the buffer to output file.
  def writeInMappedMemory(bufferSize: Int, string: String): Unit = {
    try {
      var size = 0
      var newString = string
      var endIndex = 0
      if (string.length > bufferSize) {
        size = bufferSize
        endIndex = bufferSize + currentPosition
        if ((bufferSize + currentPosition) > string.length) endIndex = string.length
        newString = string.substring(currentPosition, endIndex)
        currentPosition = currentPosition + bufferSize
      }
      else if(string.length <= bufferSize){
        size = string.length
      }

      mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, currentPosition, size)

      val charBuffer: CharBuffer = CharBuffer.wrap(newString)
      while(charBuffer.hasRemaining){
        fileChannel.write(Charset.forName("utf-8").encode(charBuffer))
      }
      mappedByteBuffer.clear


      if (currentPosition >= string.length || bufferSize > string.length) {
        allFileWritten = true
      }
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

  // ************************************************************
  // References
  // ************************************************************

  // https://www.mathworks.com/help/matlab/import_export/overview-of-memory-mapping.html#:~:text=Memory%2Dmapping%20is%20a%20mechanism,within%20an%20application's%20address%20space.&text=This%20makes%20file%20reads%20and,such%20as%20fread%20and%20fwrite%20.
  // https://www.ibm.com/support/knowledgecenter/ssw_aix_72/generalprogramming/understanding_mem_mapping.html
  // https://howtodoinjava.com/java/nio/memory-mapped-files-mappedbytebuffer/
  // https://www.javacodegeeks.com/2013/05/power-of-java-memorymapped-file.html
}
