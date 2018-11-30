/**
 * Created on 9/20/18
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 0.1
 */
public class MyFirstSort {

  /**
   * Determine if an integer array is sorted or not
   *
   * @param array the array in question
   * @return true if the array is in smallest to largest order, false otherwise
   */
  public static boolean isSorted(int[] array) {
    for (int i = 1; i < array.length; i++) {
      if (array[i - 1] > array[i]) return false;
    }
    return true;
  }

  public static void bubbleSort(int[] array) {

    for (int stoppingIndex = array.length; stoppingIndex > 0; stoppingIndex--) {

      boolean didASwap = false;

      for (int comparisonIndex = 0; comparisonIndex < stoppingIndex - 1; comparisonIndex++) {

        if (array[comparisonIndex] > array[comparisonIndex + 1]) {
          // do the swap
          System.out.println("swapping " + array[comparisonIndex] + " and " + array[comparisonIndex + 1]);
          int temp = array[comparisonIndex];
          array[comparisonIndex] = array[comparisonIndex + 1];
          array[comparisonIndex + 1] = temp;
          didASwap = true;
        } else {
          System.out.println("not swapping " + array[comparisonIndex] + " and " + array[comparisonIndex + 1]);
        }

      }

      if (!didASwap) return;

    }

  }

}
