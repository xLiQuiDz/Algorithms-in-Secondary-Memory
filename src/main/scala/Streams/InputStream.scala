package Streams

import java.io.{BufferedReader, File, FileInputStream, FileReader, IOException, RandomAccessFile}

class InputStream(fileAddress: String){

  var fileReader: FileReader = null //main stream
  var bufferedReader: BufferedReader = null //used in second readLine

  private var randomAccessFile: RandomAccessFile = null //used for seek function
  private var stringBuffer: StringBuffer = null //to show output
  private var file: File = null

  private var nextChar : Char = " ".charAt(0)
  private var nextInt = 0
  private var nextLine = ""

  override def toString: String = stringBuffer.toString

  def open: Unit = {
    try{
      file = new File(fileAddress)
      fileReader = new FileReader(file)
      bufferedReader =  new BufferedReader(fileReader)

      nextChar = " ".charAt(0)
      stringBuffer = new StringBuffer
    } catch {
      case _ => throw new Exception("File does not exist ...")
    }
  }

  def close: Unit = {
    try {
      nextChar = " ".charAt(0)
      fileReader.close
      bufferedReader.close
      stringBuffer = new StringBuffer
    } catch {
      case _ => throw new Exception("File does not exist ...")
    }
  }

  // Read one character
  def readLine: StringBuffer = {
    try {
      while(nextInt != 10) {
        nextChar = nextInt.toChar
        stringBuffer.append(nextChar)
        nextInt = fileReader.read()
      }
      stringBuffer
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  def readLineByBuffer: StringBuffer = {
    try {
      resetStringBuffer
      stringBuffer.append(nextLine)
      nextLine = ""
      stringBuffer
    } catch {
      case _ => throw new Exception("Stream has not been opened ...")
    }
  }

  // Seek possition in line
  def seek(pos: Int): StringBuffer = {
    try {
      randomAccessFile = new RandomAccessFile(fileAddress, "r")
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

  // Returns true if, end of file
  def endOfStream(file : Any): Boolean = file match {
    case f: FileReader => { nextInt = fileReader.read
      nextInt == -1 } // end of stream
    case b: BufferedReader => { nextLine = bufferedReader.readLine
      nextLine == null } // end of stream
  }

  def resetStringBuffer = {
    stringBuffer.delete(0, stringBuffer.length)
  }
}

