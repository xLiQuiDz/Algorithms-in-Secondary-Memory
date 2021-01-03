package Experiment_1_4

import java.io.File

import Streams.{InputStream, OutputStream}

import scala.collection.mutable.{ListBuffer, PriorityQueue, Queue}

case class MultiwayMerge(f: File, k: Int, M: Int, d: Int) {

  var strBuff: StringBuffer = null

  // Checks whether the k-th column is string or Int
  def isAllDigits(x: String) = x forall Character.isDigit

  // Merge sort
  def extsort(): Unit = {

    val inputStream = new InputStream(f)
    inputStream.open

    var outputFile: File = null
    var outputStream: OutputStream = null
    var mergelist = new ListBuffer[Array[String]]
    var templist = new ListBuffer[Array[String]]
    var count: Int = 0
    var file_num = 0
    var file_queue = new Queue[File]()
    var int_flag: Int = 0
    var byte_count: Int = 0

    // ***************************************************************************************
    // Dividing the total number of file data(N) in file f into [N/M] sorted Files.
    // ***************************************************************************************

    // While file not ends
    while (!inputStream.endOfStream) {
      count = count + 1

      // Total number of file data divided into N/M sorted Files
      if (byte_count > M) { // When total number of bytes read is more than M
        // Sorting based on k-th column
        if (isAllDigits(mergelist(0)(k - 1))) {
          templist = mergelist.sortBy(_ (k - 1).toInt) // If k-th column is Integer
          int_flag = 1
        } else {
          templist = mergelist.sortBy(_ (k - 1)) // If k-th column is String
        }

        // Writing M rows into output stream
        for (str_temp <- templist) {
          outputStream.writeLine(str_temp.mkString(","))
        }
        outputStream.close
        mergelist = new ListBuffer[Array[String]] // MergeList cleared up to add next M rows
        count = 1 // Count reset to 1 to write next M rows
        byte_count = 0 // Reset byte count
      }

      if (count == 1) {
        file_num = file_num + 1
        // Create file with M number of lines
        // n_by_m_sortedFiles
        outputFile = new File("src/main/resources/file_" + file_num.toString() + ".txt")
        // Newly created file added to queue
        file_queue.enqueue(outputFile)
        outputStream = new OutputStream(outputFile)
        // New File created
        outputStream.create
      }

      strBuff = inputStream.readLine
      byte_count = byte_count + strBuff.length() // Updating byte count
      // Each row converted into Array[String] and appended to mergeList
      var mergeSortArray: Array[String] = strBuff.toString.split(",")
      if (!inputStream.endOfStream) {
        mergelist.append(mergeSortArray)
      }

    } // While(!inputStream.endOfStream) loop ends here

    //This is for writing the N % M rows to the files
    if (mergelist.length > 0) {
      if (isAllDigits(mergelist(0)(k - 1))) {
        templist = mergelist.sortBy(_ (k - 1).toInt)
      } else {
        templist = mergelist.sortBy(_ (k - 1))
      }
      for (str_temp <- templist) {
        outputStream.writeLine(str_temp.mkString(","))
      }
    }

    outputStream.close //closing the file
    mergelist = new ListBuffer[Array[String]] //clearing the mergeList

    // ***************************************************************************************
    // Do the merging d number of files
    // ***************************************************************************************

    //t:(Int,Array[String]) -> Int is a temporary value which is used while dequeueing
    // to recognize which file does this smallest row come from within the d files being merged
    // -> Array[String] is a row from the file
    def cond_string(t: (Int, Array[String])) = t._2(k - 1) // condition for sorting string
    def cond_int(t: (Int, Array[String])) = t._2(k - 1).toInt //condition for sorting int

    //priority queues for String and Int
    var select_string_queue = new PriorityQueue[(Int, Array[String])]()(Ordering.by(cond_string).reverse)
    var select_int_queue = new PriorityQueue[(Int, Array[String])]()(Ordering.by(cond_int).reverse)

    // main merge loop -> loops until only one file remaining
    while (file_queue.length > 1) {

      //find out how many files to merge in this iteration
      var merge_count: Int = 0
      if (file_queue.length > d) {
        merge_count = d
      }
      else {
        merge_count = file_queue.length
      }

      var temp_output_stream: OutputStream = null
      var temp_input_stream: Array[InputStream] = new Array[InputStream](d)

      //open input streams
      for (f <- 0 to merge_count - 1) {

        temp_input_stream(f) = new InputStream(file_queue.dequeue())
        temp_input_stream(f).open

        // Store first line from each file into strBuff
        strBuff = temp_input_stream(f).readLine

        // Each row converted to Array[String]
        var mergeSortArray: Array[String] = strBuff.toString.split(",")

        // upload the first row from each file to the priority queue
        if (!temp_input_stream(f).endOfStream) {
          if (int_flag == 1) {
            select_int_queue.enqueue((f, mergeSortArray))
          }
          else {
            select_string_queue.enqueue((f, mergeSortArray))
          }
        }
      }

      // Create output file
      file_num = file_num + 1
      // d_mergeFiles
      outputFile = new File("src/main/resources/file_" + file_num.toString() + ".txt")
      file_queue.enqueue(outputFile)
      temp_output_stream = new OutputStream(outputFile)
      temp_output_stream.create

      //loop exits when the files are merged
      var while_var = true
      while (while_var) {

        var temp_out: (Int, Array[String]) = null

        if (int_flag == 1) {
          temp_out = select_int_queue.dequeue()
        }
        else {
          temp_out = select_string_queue.dequeue()
        }

        temp_output_stream.writeLine(temp_out._2.mkString(","))
        // Upload one more to queue
        strBuff = temp_input_stream(temp_out._1).readLine
        var mergeSortArray: Array[String] = strBuff.toString.split(",")
        if (!(temp_input_stream(temp_out._1).endOfStream)) {
          if (int_flag == 1) {
            select_int_queue.enqueue((temp_out._1, mergeSortArray))
          }
          else {
            select_string_queue.enqueue((temp_out._1, mergeSortArray))
          }
        }
        if (int_flag == 1) {
          while_var = !(select_int_queue.length == 0)
        }
        else {
          while_var = !(select_string_queue.length == 0)
        }
      } //while(while_var) ends here

      //close files
      for (f <- 0 to merge_count - 1) {
        temp_input_stream(f).close
      }
      temp_output_stream.close
    } //while(file_queue.length>1) ends here
    println("Sorted Output file is " + file_queue(0).getPath)
  }
}

// ************************************************************
// References
// ************************************************************

//https://stackoverflow.com/questions/9938098/how-to-check-to-see-if-a-string-is-a-decimal-number-in-scala#:~:text=forall%20takes%20a%20function%20(in,the%20collection%2C%20and%20false%20otherwise.
//https://www.geeksforgeeks.org/queue-in-scala/