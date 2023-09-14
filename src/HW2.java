import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class HW2 {

    private static int[] originalArray;
    private static int[] array;
    private static final int insertion_sort_threshold = 7; //sets the constant insertion sorting threshold for the modified merge sort algorithm

    public static void main(String[] arg) {
        try (Scanner reader = new Scanner(Paths.get("1000.txt"))) {
            while (reader.hasNextLine()) {
            String intString = reader.nextLine();
            String[] stringArray = intString.split("\\s*,\\s*"); //splits the string at each " , " and adds each individual string to an array
            originalArray = new int[stringArray.length]; //initializes array to size of the string array read from the file
            array = new int[stringArray.length]; //initializes a copy of the original array
            for(int i = 0; i < stringArray.length; i++) { //converts each string in the array to an int and adds to two new identical arrays
                String string = stringArray[i];
                originalArray[i] = Integer.parseInt(string);
                array[i] = Integer.parseInt(string);
            }
        }
        reader.close();   

        long timeInit = System.nanoTime(); //records initial system time in nanoseconds
        insertationSort(array); 
        long timeFinal = System.nanoTime(); // records final system time in nanoseconds
        long time = timeFinal - timeInit; //calculates time taken for insertion sort algorithm
        // for (int i = 0; i < array.length; i++) { //prints each value of the array after sorting to verify results (testing only)
        //     System.out.println(array[i]);
        // }
        System.out.println("Insertion Sort Time:" + time + " nanoseconds, " + (float)time/1000000 + " milliseconds, or " + (float)time/1000000000 + " seconds");
        array = originalArray; //resets the array

        long timeInit2 = System.nanoTime(); //records initial system time in nanoseconds
        int[] tempArray = new int[array.length];
        mergeSort(array, tempArray, 0, array.length - 1); 
        long timeFinal2 = System.nanoTime(); // records final system time in nanoseconds
        long time2 = timeFinal2 - timeInit2; //calculates time taken for merge sort algorithm
        // for (int i = 0; i < array.length; i++) { //prints each value of the array after sorting to verify results (testing only)
        //     System.out.println(array[i]);
        // }
        System.out.println("Merge Sort Time:" + time2 + " nanoseconds, " + (float)time2/1000000 + " milliseconds, or " + (float)time2/1000000000 + " seconds");
        array = originalArray; //resets the array

        long timeInit3 = System.nanoTime(); //records initial system time in nanoseconds
        modifiedMergeSort(array, tempArray, 0, array.length - 1); 
        long timeFinal3 = System.nanoTime(); // records final system time in nanoseconds
        long time3 = timeFinal3 - timeInit3; //calculates time taken for modified merge sort algorithm
        // for (int i = 0; i < array.length; i++) { //prints each value of the array after sorting to verify results (testing only)
        //     System.out.println(array[i]);
        // }
        System.out.println("Modified Merge Sort Time:" + time3 + " nanoseconds, " + (float)time3/1000000 + " milliseconds, or " + (float)time3/1000000000 + " seconds");
        array = originalArray; //resets the array

        long timeInit4 = System.nanoTime(); //records initial system time in nanoseconds
        heapSort(array);
        long timeFinal4 = System.nanoTime(); // records final system time in nanoseconds
        long time4 = timeFinal4 - timeInit4; //calculates time taken for modified heap sort algorithm
        // for (int i = 0; i < array.length; i++) { //prints each value of the array after sorting to verify results (testing only)
        //     System.out.println(array[i]);
        // }
        System.out.println("Heap Sort Time:" + time4 + " nanoseconds, " + (float)time4/1000000 + " milliseconds, or " + (float)time4/1000000000 + " seconds");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertationSort(int[] array) {
        int i, j, k; //initializes count variables
        for (i = 0; i < array.length; i++) {
            k = array[i]; //set the key to the value of the current index
            j = i - 1; //j is the previous index
            while (j >= 0 && array[j] > k) { //while lowest index is above 0 and the value of the previous index is above the value of the current index
                array[j + 1] = array[j]; //shifts value at index one position to the right, making space for key
                j = j - 1; //the j index is moved one left for further comparisons
            }
            array[j + 1] = k; //places the k value at the correct sorted position
        }
    }

    public static void mergeSort(int[] array, int[] tempArray, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2; //calculate middle index
            mergeSort(array, tempArray, p, q); //recursively sort left subarray
            mergeSort(array, tempArray, q + 1, r); //recursively sort right subarray
            merge(array, tempArray, p, q, r); //merge the two sorted subarrays
        }
    }

    public static void modifiedMergeSort(int[] array, int[] tempArray, int p, int r) {
        if (p < r) {
            if (r - p <= insertion_sort_threshold) { //runs insertion sort algorithm if array size is less than or equal to the threshold
                insertationSort(array);
            }
            else { //else runs mergesort algorithm
            int q = (p + r) / 2; //calculate middle index
            mergeSort(array, tempArray, p, q); //recursively sort left subarray
            mergeSort(array, tempArray, q + 1, r); //recursively sort right subarray
            merge(array, tempArray, p, q, r); //merge the two sorted subarrays
            }
        }
    }

    public static void merge(int[] array, int[] tempArray, int p, int q, int r) {
        int i = p; //initializing pointer to starting index of left subarray
        int j = q + 1; //initializing pointer to starting index of right subarray
        for (int k = p; k <= r; k++) { //copy all values in array to tempArray
            tempArray[k] = array[k];
        }
        for (int k = p; k <= r; k++) {
            if (i > q) { //if left half is empty, copy from the right
                array[k] = tempArray[j];
                j++;
            }
            else if (j > r) { //if right half is empty, copy from the left
                array[k] = tempArray[i];
                i++;
            }
            else if (tempArray[j] < tempArray[i]) { //if neither copy from the right
                array[k] = tempArray[j];
                j++;
            }
            else { //else copy from left
                array[k] = tempArray[i];
                i++;
            }
        }
    }

    public static void buildMaxHeap(int[] array) { //builds the max heap by iterating down the array calls maxHeapify() to ensure that the subtree rooted at i is a valid max heap
        int heapSize = array.length;
        for (int i = heapSize / 2 - 1; i >= 0; i--) { //initializes i to the last non-leaf node 
            maxHeapify(array, i, heapSize);
        }
    }

    public static void maxHeapify(int[] array, int i, int heapSize) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < heapSize && array[left] > array[largest]) {
            largest = left;
        }
        if (right < heapSize && array[right] > array[largest]) {
            largest = right;
        }
        else {
            if (largest != i) {
                int temp = array[i];
                array[i] = array[largest];
                array[largest] = temp;
                maxHeapify(array,i,heapSize);
            }
        }
    }
    
    public static void heapSort(int[] array) {
        buildMaxHeap(array);
        for (int i = array.length - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            maxHeapify(array,i,0);
        }
    }

}
