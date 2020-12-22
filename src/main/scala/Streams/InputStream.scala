package Streams

import java.io.{BufferedReader, File, FileReader, RandomAccessFile}

class InputStream(file: File){

  private var fileReader: FileReader = null // Main stream.
  private var bufferedReader: BufferedReader = null // Used for reading line.
  private var randomAccessFile: RandomAccessFile = null // Used for seeking a position in file.

  private var stringBuffer: StringBuffer = null // Shows string output.

  var endOfStream = false

  // Open file for reading.
  def open(): Unit = {
    try{
      fileReader = new FileReader(file)
      bufferedReader =  new BufferedReader(fileReader)
      randomAccessFile = new RandomAccessFile(file, "r")
      stringBuffer = new StringBuffer
    } catch {
      case _ => throw new Exception("File does not exist ...")
    }
  }

  // close file.
  def close(): Unit = {
    try {
      fileReader.close()
      bufferedReader.close()
      randomAccessFile.close()
      stringBuffer = new StringBuffer
    } catch {
      case _ => throw new Exception("File does not exist ...")
    }
  }

  // Implementation 1.1.1
  // Read one character and add to string buffer.
  def readCharacter(): StringBuffer = {
    resetStringBuffer()
    try {
      var data = fileReader.read()
      while(data != 10 && data != -1) { // End of line or end of file.
        stringBuffer.append(data.asInstanceOf[Char])
        data = fileReader.read()
      }
      if (data == -1) endOfStream = true
      stringBuffer
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Implementation 1.1.2
  // Read one line and add to string buffer.
  def readLine(): StringBuffer = {
    resetStringBuffer()
    try {
      var data = bufferedReader.readLine()
      if (data != 10 && data != null) { // End of line or end of file.
        stringBuffer.append(data)
      } else {
        endOfStream = true
      }
      stringBuffer
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Implementation 1.1.3
  // Read one character and add to buffer.
  def readCharacterWithBuffer(bufferSize : Int) : StringBuffer = {
    resetStringBuffer()
    try {
      var i = 0
      var data = fileReader.read()

      while(data != -1 && i < bufferSize ) { // End of line or end of file.
        stringBuffer.append(data.asInstanceOf[Char])
        data = fileReader.read()
        i += 1
      }
      if (data == -1) endOfStream = true
      stringBuffer
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Seek position in line.
  def seek(pos: Long): Unit = {
    try {
      randomAccessFile.seek(pos)
      fileReader = new FileReader(randomAccessFile.getFD)
      bufferedReader =  new BufferedReader(fileReader)
    } catch {
      case _ => throw new Exception("File does not exist ...")
    }
  }

  def resetStringBuffer(): Unit = {
    stringBuffer = new StringBuffer()
  }
}

