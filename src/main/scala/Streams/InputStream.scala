package Streams

import java.io.{BufferedReader, File, FileInputStream, FileReader, IOException, RandomAccessFile}

class InputStream(file: File){

  var fileReader: FileReader = null //main stream
  var bufferedReader: BufferedReader = null //used in second readLine

  private var randomAccessFile: RandomAccessFile = null //used for seek function
  private var stringBuffer: StringBuffer = null //to show output

  private var nextChar : Char = " ".charAt(0)
  private var nextInt = 0

  override def toString: String = stringBuffer.toString()

  // Open file
  def open: Unit = {
    try{
      fileReader = new FileReader(file)
      bufferedReader =  new BufferedReader(fileReader)
      stringBuffer = new StringBuffer
    } catch {
      case _ => throw new Exception("File does not exist ...")
    }
  }

  // close file
  def close: Unit = {
    try {
      fileReader.close
      bufferedReader.close
      stringBuffer = new StringBuffer
    } catch {
      case _ => throw new Exception("File does not exist ...")
    }
  }

  // Implementation 1.1.1
  // Read one character and add to string buffer.
  def readCharacter: StringBuffer = {
    resetStringBuffer()
    try {
      var data = 0
      while(data != -1) {
        data = fileReader.read()
        stringBuffer.append(data.asInstanceOf[Char]) // Convert ASCI to char.
      }
      stringBuffer
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Implementation 1.1.2
  // Read one line and add to string buffer.
  def readLine: StringBuffer = {
    resetStringBuffer()
    try {
      var data = bufferedReader.readLine()
      if (data != null) {
        stringBuffer.append(data)
      } else {

      }
      stringBuffer
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Seek possition in line
  def seek(pos: Int): StringBuffer = {
    try {
      randomAccessFile = new RandomAccessFile(file.getPath(), "r")
      randomAccessFile.seek(pos)
      fileReader = new FileReader(randomAccessFile.getFD)
      bufferedReader =  new BufferedReader(fileReader)

      val line = bufferedReader.readLine
      stringBuffer.append(line)
      randomAccessFile.close()
      stringBuffer
    } catch {
      case _ => throw new Exception("File does not exist ...")
    }
  }



  def test: StringBuffer = {

    if(fileReader == null){
      throw new Exception("Stream has not been opened ...")
    }

    while(nextInt != 10) {
      nextChar = nextInt.toChar
      stringBuffer.append(nextChar.asInstanceOf[Char])
      nextInt = fileReader.read()
    }
    stringBuffer
  }

  // Returns true if, end of file
  def endOfStream(file : Any): Boolean = file match {
    case f: FileReader => {nextInt = fileReader.read
      nextInt == -1 } // end of stream
    case b: BufferedReader => {var nextLine = bufferedReader.readLine
      nextLine == null } // end of stream
  }

  def resetStringBuffer() = {
    stringBuffer = new StringBuffer()
  }
}

