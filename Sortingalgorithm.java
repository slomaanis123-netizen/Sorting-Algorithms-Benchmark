package sortingalgorithm;
import java.util.Arrays;
import java.util.Random;

public class Sortingalgorithm {


    static final int N = 100_000;
 

    static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) min = j;
            }
            int t = a[i]; a[i] = a[min]; a[min] = t;
        }
    }
 
 
    static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }
 
    static void bubbleSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t;
                    swapped = true;
                }
            }
            if (!swapped) break; 
        }
    }
 
    static void mergeSort(int[] a, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(a, left, mid);
        mergeSort(a, mid + 1, right);
        merge(a, left, mid, right);
    }
 
    static void merge(int[] a, int left, int mid, int right) {
        int[] L = Arrays.copyOfRange(a, left, mid + 1);
        int[] R = Arrays.copyOfRange(a, mid + 1, right + 1);
        int i = 0, j = 0, k = left;
        while (i < L.length && j < R.length) {
            if (L[i] <= R[j]) a[k++] = L[i++];
            else a[k++] = R[j++];
        }
        while (i < L.length) a[k++] = L[i++];
        while (j < R.length) a[k++] = R[j++];
    }

    
    static void quickSort(int[] a, int low, int high) {
        if (low >= high) return;
        int p = partition(a, low, high);
        quickSort(a, low, p - 1);
        quickSort(a, p + 1, high);
    }
 
    static int partition(int[] a, int low, int high) {
        int mid = low + (high - low) / 2;
        // نضع المحور في آخر المصفوفة
        int t = a[mid]; a[mid] = a[high]; a[high] = t;
        int pivot = a[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (a[j] < pivot) {
                i++;
                t = a[i]; a[i] = a[j]; a[j] = t;
            }
        }
        t = a[i + 1]; a[i + 1] = a[high]; a[high] = t;
        return i + 1;
    }
 
    static int[] randomArray(Random rnd) {
        int[] a = new int[N];
        for (int i = 0; i < N; i++) a[i] = rnd.nextInt(1_000_000);
        return a;
    }
 
    static int[] sortedArray(Random rnd) {
        int[] a = randomArray(rnd);
        Arrays.sort(a);
        return a;
    }
 
    static int[] reverseArray(Random rnd) {
        int[] a = sortedArray(rnd);
        for (int i = 0; i < N / 2; i++) {
            int t = a[i]; a[i] = a[N - 1 - i]; a[N - 1 - i] = t;
        }
        return a;
    }
 
   
    static int[] partiallySortedArray(Random rnd) {
        int[] a = sortedArray(rnd);
        int swaps = (int) (N * 0.20 / 2);
        for (int k = 0; k < swaps; k++) {
            int i = rnd.nextInt(N);
            int j = rnd.nextInt(N);
            int t = a[i]; a[i] = a[j]; a[j] = t;
        }
        return a;
    }
 
    
    static long measure(int algo, int[] original) {
        int[] testArray = Arrays.copyOf(original, original.length); // نسخة مستقلة
        long start = System.nanoTime();
        switch (algo) {
            case 0: selectionSort(testArray); break;
            case 1: insertionSort(testArray); break;
            case 2: bubbleSort(testArray); break;
            case 3: mergeSort(testArray, 0, testArray.length - 1); break;
            case 4: quickSort(testArray, 0, testArray.length - 1); break;
        }
        long end = System.nanoTime();
        return (end - start) / 1000; 
    }
 
    public static void main(String[] args) {
        Random rnd = new Random(42); 
 
        String[] algoNames = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Merge Sort", "Quick Sort"};
        String[] caseNames = {"Random", "Reversed", "Partially", "Sorted"};
 
        int[][] cases = {
                randomArray(rnd),
                reverseArray(rnd),
                partiallySortedArray(rnd),
                sortedArray(rnd)
        };
 
        System.out.println("Sorting Benchmark (N = " + N + ") - time in microseconds (us)");
        System.out.println("Running... please wait.\n");
 
        long[][] results = new long[5][4];
        for (int a = 0; a < 5; a++) {
            for (int c = 0; c < 4; c++) {
                results[a][c] = measure(a, cases[c]);
            }
        }
 
  
        System.out.printf("%-16s | %-14s | %-14s | %-14s | %-14s%n",
                "Algorithm", caseNames[0], caseNames[1], caseNames[2], caseNames[3]);
        System.out.println("-".repeat(86));
        for (int a = 0; a < 5; a++) {
            System.out.printf("%-16s | %,11d us | %,11d us | %,11d us | %,11d us%n",
                    algoNames[a], results[a][0], results[a][1], results[a][2], results[a][3]);
        }
    }
}
