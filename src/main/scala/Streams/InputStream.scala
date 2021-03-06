package Streams

import java.io.{BufferedReader, ByteArrayOutputStream, File, FileInputStream, FileReader, RandomAccessFile}
import java.nio.channels.{FileChannel}
import java.nio.charset.StandardCharsets
import java.nio.ByteBuffer


class InputStream(file: File) {

  private var fileReader: FileReader = null // Main reader stream.
  private var bufferedReader: BufferedReader = null // Used for reading line.
  private var randomAccessFile: RandomAccessFile = null // Used for seeking a position in file.
  private var fileInputStream: FileInputStream = null
  private var fileChannel: FileChannel = null
  private var stringBuffer: StringBuffer = null // Shows string output.
  private var currentPosition: Int = 0
  private var channelSize: Long = 0
  var endOfStream = false

  // Open file for reading.
  def open: Unit = {
    try {
      fileReader = new FileReader(file) // Used in 1.1.1
      bufferedReader = new BufferedReader(fileReader) // Used in 1.1.2 and 1.1.3
      randomAccessFile = new RandomAccessFile(file, "r") // Used in seek and 1.1.4
      fileInputStream = new FileInputStream(file) // Used in 1.1.4
      stringBuffer = new StringBuffer // Used for output the read line.
      fileChannel = randomAccessFile.getChannel // //Get file channel in read-only mode.
      channelSize = fileChannel.size
      currentPosition = 0
      endOfStream = false
    } catch {
      case _ => throw new Exception("Exception ...")
    }
  }

  // Close file for reading.
  def close: Unit = {
    try {
      resetStringBuffer
      fileReader.close
      bufferedReader.close
      randomAccessFile.close
      fileInputStream.close
      fileChannel.close
      endOfStream = false
      channelSize = 0
      currentPosition = 0
    } catch {
      case _ => throw new Exception("Exception ...")
    }
  }

  // ***********************************************************************************
  // Readln Procedures
  // ***********************************************************************************

  // Implementation 1.1.1
  // Read one character and add to StringBuffer using FileReader.
  def readCharacter: StringBuffer = {
    resetStringBuffer
    try {
      var data = fileReader.read() // Reads the ASCI of the next character.
      while (data != 10 && data != -1) { // End of line or end of file.
        stringBuffer.append(data.asInstanceOf[Char]) // Convert ASCI to Char.
        data = fileReader.read()
      }
      if (data == -1 || data == -10) {
        endOfStream = true
      }
      stringBuffer
    } catch {
      case _ => throw new Exception("Exception ...")
    }
  }

  // Implementation 1.1.2
  // Read one line and add to StringBuffer using BufferedReader with default buffer size.
  def readLine: StringBuffer = {
    resetStringBuffer
    try {
      val data = bufferedReader.readLine() // Reads one line.
      if (data != 10 && data != null) { // End of line or end of file.
        stringBuffer.append(data)
      } else {
        endOfStream = true
      }
      stringBuffer
    } catch {
      case _ => throw new Exception("Exception ...")
    }
  }

  // Implementation 1.1.3
  // Read one line and add to StringBuffer using BufferedReader, the BufferedReader is limited by setBufferSize.
  def readCharacterWithBuffer: StringBuffer = {
    resetStringBuffer
    try {
      var data = bufferedReader.read() // Reads the ASCI of the next character.
      while (data != 10 && data != -1) { // 10 for detecting End of line or -1 for detecting end of file.
        stringBuffer.append(data.asInstanceOf[Char])
        data = bufferedReader.read()
      }
      if (data == -1) {
        endOfStream = true
      }
      stringBuffer
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Implementation 1.1.4
  // Read one character and add to buffer.
  def readFromMappedMemory(bufferSize: Int): StringBuffer = {
    resetStringBuffer

    var size = bufferSize
    if(channelSize - currentPosition == 0)  {
      endOfStream = true
      return new StringBuffer()
    }

    if (bufferSize > (channelSize - currentPosition)) {
      size = (channelSize - currentPosition).asInstanceOf[Int]
    }

    val mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, currentPosition, size)

    var data = mappedByteBuffer.get()

    while (data != -1 && data != 10 && mappedByteBuffer.hasRemaining) {
      stringBuffer.append(data.asInstanceOf[Char])
      data = mappedByteBuffer.get
      currentPosition += 1
    }
    mappedByteBuffer.clear()
    currentPosition += 1
    stringBuffer
  }

  // ***********************************************************************************
  // Auxiliary Procedures
  // ***********************************************************************************

  // Seek position in line.
  def seek(pos: Long): Unit = {
    try {
      randomAccessFile.seek(pos)
      fileReader = new FileReader(randomAccessFile.getFD)
      bufferedReader = new BufferedReader(fileReader)
    } catch {
      case _ => throw new Exception("File does not exist ...")
    }
  }

  // Set buffer size for BufferedReader.
  def setBufferSize(bufferSize: Int): Unit = {
    try {
      bufferedReader = new BufferedReader(fileReader, bufferSize)
    }
    catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Reset the stringBuffer.
  def resetStringBuffer(): Unit = {
    stringBuffer = new StringBuffer()
  }

  // ************************************************************
  // References
  // ************************************************************

  // https://www.mathworks.com/help/matlab/import_export/overview-of-memory-mapping.html#:~:text=Memory%2Dmapping%20is%20a%20mechanism,within%20an%20application's%20address%20space.&text=This%20makes%20file%20reads%20and,such%20as%20fread%20and%20fwrite%20
  // https://www.ibm.com/support/knowledgecenter/ssw_aix_72/generalprogramming/understanding_mem_mapping.html
  // https://howtodoinjava.com/java/nio/memory-mapped-files-mappedbytebuffer/
  // https://www.javacodegeeks.com/2013/05/power-of-java-memorymapped-file.html
}

