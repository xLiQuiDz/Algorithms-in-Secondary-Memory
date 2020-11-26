package Sorting

object MergeSort {
  def merge_Sort(numArray: Array[Int]) {
    //return if array is empty
    if(numArray == null) {
      return;
    }

    if(numArray.length > 1) {
      var mid: Int = numArray.length / 2 //find mid of the array

      // left half of the array
      var left: Array[Int] = new Array[Int](mid)
      Array.copy(numArray, 0, left, 0, mid)

      var rest = numArray.length - mid
      var right: Array[Int] = new Array[Int](rest)
      Array.copy(numArray, mid, right, 0 , rest)

      merge_Sort(left);   //call merge_Sort routine for left half of the array
      merge_Sort(right);   // call merge_Sort routine for right half of the array

      var i = 0;
      var j = 0;
      var k = 0;
      // now merge two arrays
      while(i < left.length && j < right.length) {
        if(left(i) < right(j)) {
          numArray(k) = left(i);
          i += 1;
        }
        else {
          numArray(k) = right(j);
          j += 1;
        }
        k += 1;
      }

      // remaining elements
      while(i < left.length) {
        numArray(k) = left(i);
        i+= 1;
        k+= 1;
      }
      while(j < right.length) {
        numArray(k) = right(j);
        j += 1;
        k += 1;
      }
    }
  }
}