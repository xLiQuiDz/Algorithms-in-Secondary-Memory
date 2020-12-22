package Streams

import java.io.{BufferedReader, File, FileInputStream, FileReader, RandomAccessFile}
import java.nio.channels.FileChannel

//1.1 Reading
class InputStream(file: File) {

  private var fileReader: FileReader = null // Main reader stream.
  private var bufferedReader: BufferedReader = null // Used for reading line.
  private var randomAccessFile: RandomAccessFile = null // Used for seeking a position in file.
  private var fileInputStream: FileInputStream = null
  private var fileChannel: FileChannel = null

  private var stringBuffer: StringBuffer = null // Shows string output.

  var endOfStream = false // for detecting end of stream.

  private var currentPosition: Int = 0
  private var channelSize: Long = 0

  // Initializing the fields for stream.
  def open: Unit = {
    try {
      // Used in 1.1.1
      fileReader = new FileReader(file)

      // Used in 1.1.2 and 1.1.3
      bufferedReader = new BufferedReader(fileReader)

      // Used in seek and 1.1.4
      randomAccessFile = new RandomAccessFile(file, "r")

      // Used in 1.1.4
      fileInputStream = new FileInputStream(file)
      fileChannel = randomAccessFile.getChannel
      channelSize = fileChannel.size
      currentPosition = 0
      endOfStream = false

      // Used for output the read line
      stringBuffer = new StringBuffer

    } catch {
      case _ => throw new Exception("Exception ...")
    }
  }

  // Close file.
  def close: Unit = {
    try {
      fileReader.close
      bufferedReader.close
      randomAccessFile.close
      fileInputStream.close
      fileChannel.close
      channelSize = 0
      currentPosition = 0

      endOfStream = false

      resetStringBuffer
    } catch {
      case _ => throw new Exception("Exception ...")
    }
  }

  // Implementation 1.1.1
  // Read one character and add to StringBuffer using FileReader.
  def readCharacter: StringBuffer = {
    resetStringBuffer
    try {
      var data = fileReader.read() // Reads the int of next character.
      while (data != 10 && data != -1) { // 10 for detecting End of line or -1 for detecting end of file.
        stringBuffer.append(data.asInstanceOf[Char])
        data = fileReader.read()
      }
      if (data == -1) endOfStream = true
      stringBuffer
    } catch {
      case _ => throw new Exception("Exception ...")
    }
  }

  // Implementation 1.1.2
  // Read one line and add to StringBuffer using BufferedReader.
  def readLine: StringBuffer = {
    resetStringBuffer
    try {
      val data = bufferedReader.readLine()
      if (data != 10 && data != null) { // End of line or end of file.
        stringBuffer.append(data)
        stringBuffer.append(System.lineSeparator())
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
      var data = bufferedReader.read()
      while (data != 10 && data != -1) { // 10 for detecting End of line or -1 for detecting end of file.
        stringBuffer.append(data.asInstanceOf[Char])
        data = bufferedReader.read()
      }
      if (data == -1) endOfStream = true
      stringBuffer

    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Implementation 1.1.4
  // Read one character and add to buffer.
  // Explanation of memorry mapping:
  // https://www.mathworks.com/help/matlab/import_export/overview-of-memory-mapping.html#:~:text=Memory%2Dmapping%20is%20a%20mechanism,within%20an%20application's%20address%20space.&text=This%20makes%20file%20reads%20and,such%20as%20fread%20and%20fwrite%20.
  // https://www.ibm.com/support/knowledgecenter/ssw_aix_72/generalprogramming/understanding_mem_mapping.html
  // https://howtodoinjava.com/java/nio/memory-mapped-files-mappedbytebuffer/
  // https://www.javacodegeeks.com/2013/05/power-of-java-memorymapped-file.html
  // this function does not work correctly!!!!!!
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

    var byteArray: Array[Byte] = new Array[Byte](size)

    var data = mappedByteBuffer.get()

    var i: Int = 0
    while (data != -1 && data != 10 && i < size && mappedByteBuffer.remaining() >= 36) {
      byteArray(i) = data
      data = mappedByteBuffer.get()
      currentPosition += 1
      i += 1
    }
    val text = (byteArray.map(_.toChar)).mkString
    stringBuffer.append(text)
    mappedByteBuffer.clear()
    currentPosition += 1
    stringBuffer
  }

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

  // Set buffer size for BufferedReader
  def setBufferSize(bufferSize: Int): Unit = {
    try {
      bufferedReader = new BufferedReader(fileReader, bufferSize)
    }
    catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  def resetStringBuffer(): Unit = {
    stringBuffer = new StringBuffer()
  }
}

