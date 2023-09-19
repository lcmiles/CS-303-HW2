import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class HW2 {

    private static String s = "";
    private static int[] originalArray;
    private static int[] array;
    private static int insertionSortThreshold = 7; //sets the constant insertion sorting threshold for the modified merge sort algorithm

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
        //     if (i == 0) {
        //         s = s + array[i]; 
        //     }
        //     else {
        //         s = s + "," + array[i];
        //     }
        // }
        // System.out.println(s);
        System.out.println("Insertion Sort Time:" + time + " nanoseconds, " + (float)time/1000000 + " milliseconds, or " + (float)time/1000000000 + " seconds");
        array = originalArray; //resets the array

        timeInit = System.nanoTime(); //records initial system time in nanoseconds
        int[] tempArray = new int[array.length];
        mergeSort(array, tempArray, 0, array.length - 1); 
        timeFinal = System.nanoTime(); // records final system time in nanoseconds
        time = timeFinal - timeInit; //calculates time taken for merge sort algorithm
        // s = "";
        // for (int i = 0; i < array.length; i++) { //prints each value of the array after sorting to verify results (testing only)
        //     if (i == 0) {
        //         s = s + array[i]; 
        //     }
        //     else {
        //         s = s + "," + array[i];
        //     }
        // }
        // System.out.println(s);
        System.out.println("Merge Sort Time:" + time + " nanoseconds, " + (float)time/1000000 + " milliseconds, or " + (float)time/1000000000 + " seconds");
        array = originalArray; //resets the array

        timeInit = System.nanoTime(); //records initial system time in nanoseconds
        modifiedMergeSort(array, tempArray, 0, array.length - 1); 
        timeFinal = System.nanoTime(); // records final system time in nanoseconds
        time = timeFinal - timeInit; //calculates time taken for modified merge sort algorithm
        // s = "";
        // for (int i = 0; i < array.length; i++) { //prints each value of the array after sorting to verify results (testing only)
        //     if (i == 0) {
        //         s = s + array[i]; 
        //     }
        //     else {
        //         s = s + "," + array[i];
        //     }
        // }
        // System.out.println(s);
        System.out.println("Modified Merge Sort Time:" + time + " nanoseconds, " + (float)time/1000000 + " milliseconds, or " + (float)time/1000000000 + " seconds");
        array = originalArray; //resets the array

        timeInit = System.nanoTime(); //records initial system time in nanoseconds
        heapSort(array);
        timeFinal = System.nanoTime(); // records final system time in nanoseconds
        time = timeFinal - timeInit; //calculates time taken for modified heap sort algorithm
        // s = "";
        // for (int i = 0; i < array.length; i++) { //prints each value of the array after sorting to verify results (testing only)
        //     if (i == 0) {
        //         s = s + array[i]; 
        //     }
        //     else {
        //         s = s + "," + array[i];
        //     }
        // }
        // System.out.println(s);
        System.out.println("Heap Sort Time:" + time + " nanoseconds, " + (float)time/1000000 + " milliseconds, or " + (float)time/1000000000 + " seconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Description: insertionSort() iterates through each element in the array and compares the value of the current index to 
    that of the previous index moving the value back until the value of the previous index is less than the current
    Parameters: int[] array is an array of integers read from the text file in main()
    Returns: nothing
    Citations: 
    https://chat.openai.com/share/e0124443-6628-4959-9653-b91b956b9b12
    https://www.geeksforgeeks.org/insertion-sort/
    */
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

    /*
    Description: mergeSort() calculates the middle index, if the recursively calls mergeSort() on the left and right subarrays in order to sort them, 
    then calls merge() to merge the newly sorted subarrays
    Parameters: 
    int[] array is an array of integers read from the text file in main()
    int[] temparray is a temporary copy of array for iteration purposes
    int p is a pointer for starting index of left subarray
    int r is a pointer for ending index of right subarray
    Returns: nothing
    Citations: 
    https://chat.openai.com/share/c8f53e73-fb52-483b-846c-c756faa3bc6e
    https://www.geeksforgeeks.org/merge-sort/
    */
    public static void mergeSort(int[] array, int[] tempArray, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2; //calculate middle index
            mergeSort(array, tempArray, p, q); //recursively sort left subarray
            mergeSort(array, tempArray, q + 1, r); //recursively sort right subarray
            merge(array, tempArray, p, q, r); //merge the two sorted subarrays
        }
    }

    /*
    Description: modifiedMergeSort() works similarly to mergeSort(), only the algorithm implements insertionSort() when the array size has been reduced to less
    than or equal to insertionSortThreshold
    Parameters:
    int[] array is an array of integers read from the text file in main()
    int[] temparray is a temporary copy of array for iteration purposes
    int p is a pointer for starting index of left subarray
    int r is a pointer for ending index of right subarray
    Returns: nothing
    Citations: https://chat.openai.com/share/c8f53e73-fb52-483b-846c-c756faa3bc6e
    */
    public static void modifiedMergeSort(int[] array, int[] tempArray, int p, int r) {
        if (p < r) {
            if (r - p <= insertionSortThreshold) { //runs insertion sort algorithm if array size is less than or equal to the threshold
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

    /*
    Description: merge() is called in mergeSort(), and it is responsible for adding the values in the correct order from the subarrays back into the array they
    were divided from until the whole array is sorted
    Parameters:
    int[] array is an array of integers read from the text file in main()
    int[] temparray is a temporary copy of array for iteration purposes
    int p is a pointer for starting index of left subarray
    int r is a pointer for ending index of right subarray
    int q is a pointer for middle index of the original array
    Returns: nothing
    Citations: https://chat.openai.com/share/c8f53e73-fb52-483b-846c-c756faa3bc6e
    */
    public static void merge(int[] array, int[] tempArray, int p, int q, int r) {
        int i = p; //initializing pointer to starting index of left subarray
        int j = q + 1; //initializing pointer to starting index of right subarray
        for (int k = p; k <= r; k++) { //copy all values in array to tempArray
            tempArray[k] = array[k];
        }
        for (int k = p; k <= r; k++) {
            if (i > q) { //if left subarray is empty, copy the element from the right subarray to the main array
                array[k] = tempArray[j];
                j++;
            }
            else if (j > r) { //if right subarray is empty, copy the element the left subarray to the main array
                array[k] = tempArray[i];
                i++;
            }
            else if (tempArray[j] < tempArray[i]) { //if the right element is smaller than the left element, copy the element from the right subarray to the main array
                array[k] = tempArray[j];
                j++;
            }
            else { //else copy the element from the left subarray to the main array
                array[k] = tempArray[i];
                i++;
            }
        }
    }

    /*
    Description: buildMaxHeap() builds the max heap by iterating down the array and calling maxHeapify() on non-leaf node to ensure that the subtree rooted at i is a valid max heap
    Parameters: int[] array is an array of integers read from the text file in main()
    Returns: nothing
    Citations: https://chat.openai.com/share/1fb9df06-3160-486e-90f1-8587877af3e9
    */
    public static void buildMaxHeap(int[] array) { 
        int heapSize = array.length;
        for (int i = heapSize / 2 - 1; i >= 0; i--) { //initializes i to the last non-leaf node 
            maxHeapify(array, heapSize, i);
        }
    }
    
    /*
    Description: maxHeapify() maintains the max heap properties during each recursion of heapSort() 
    Parameters: 
    int[] array is an array of integers read from the text file in main()
    int i is the parent iterated over in buildMaxHeap()
    int heapSize is the number of elements in the heap
    Returns: nothing
    Citations: https://chat.openai.com/share/1fb9df06-3160-486e-90f1-8587877af3e9
    */
    public static void maxHeapify(int[] array, int i, int heapSize) { 
        int largest = i; //index of the parent
        int left = 2 * i + 1; //index of the left leaf
        int right = 2 * i + 2; //index of the right leaf
        if (left < heapSize && array[left] > array[largest]) { //if index of the left leaf is less than the heap size and the value of the left leaf is greater than the parent value
            largest = left; //set left leaf to parent
        }
        if (right < heapSize && array[right] > array[largest]) { //if index of the right leaf is less than the heap size and the value of the right leaf is greater than the parent value
            largest = right; //set right leaf to parent
        }
        if (largest != i) { //if the largest element is not the root of the maxheap, swap them and recursively heapify the affected sub-tree
            int temp = array[i]; 
            array[i] = array[largest];
            array[largest] = temp;
            maxHeapify(array,heapSize,largest);
        }
    }
    
    /*
    Description: calls buildMaxHeap() to build the max heap then iterates down the heap calling maxHeapify() to sort the heap
    Parameters: int[] array is an array of integers read from the text file in main()
    Returns: nothing
    Citations: https://chat.openai.com/share/1fb9df06-3160-486e-90f1-8587877af3e9
    */
    public static void heapSort(int[] array) { 
        buildMaxHeap(array);
        for (int i = array.length - 1; i > 0; i--) {
            maxHeapify(array,i,0);
        }
    }
}