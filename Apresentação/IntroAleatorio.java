import java.util.Random;
import java.util.Arrays;

public class Intro {
    public static void main(String[] args) {
        int[] array = generateRandomArray(10000000);
        int[] introsortArray = array.clone();

        long startTime = System.currentTimeMillis();
        int introsortMovements = introsort(introsortArray);
        long introsortTime = System.currentTimeMillis() - startTime;

        System.out.println("Tempo de execução do Introsort: " + formatTime(introsortTime));
        System.out.println("Número de movimentos do Introsort: " + introsortMovements);
    }

    public static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000000000); // Generate random numbers between 0 and 999999999
        }
        return array;
    }

    public static int introsort(int[] array) {
        int maxDepth = (int) (Math.log(array.length) * 2.5);
        return introsort(array, 0, array.length - 1, maxDepth);
    }

    public static int introsort(int[] array, int low, int high, int maxDepth) {
        if (low < high) {
            if (maxDepth == 0) {
                heapsort(array, low, high);
                return high - low;
            }

            int pivotIndex = partition(array, low, high);
            int movements = high - low;

            movements += introsort(array, low, pivotIndex, maxDepth - 1);
            movements += introsort(array, pivotIndex + 1, high, maxDepth - 1);

            return movements;
        }
        return 0;
    }

    public static int partition(int[] array, int low, int high) {
        int pivot = array[low];
        int i = low - 1;
        int j = high + 1;

        while (true) {
            do {
                i++;
            } while (array[i] < pivot);

            do {
                j--;
            } while (array[j] > pivot);

            if (i >= j) {
                return j;
            }

            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static void heapsort(int[] array, int low, int high) {
        int n = high - low + 1;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, low, i, n);
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = array[low];
            array[low] = array[low + i];
            array[low + i] = temp;

            heapify(array, low, 0, i);
        }
    }

    public static void heapify(int[] array, int low, int i, int n) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[low + left] > array[low + largest]) {
            largest = left;
        }

        if (right < n && array[low + right] > array[low + largest]) {
            largest = right;
        }

        if (largest != i) {
            int swap = array[low + i];
            array[low + i] = array[low + largest];
            array[low + largest] = swap;

            heapify(array, low, largest, n);
        }
    }

    public static String formatTime(long timeInMilliseconds) {
        long milliseconds = timeInMilliseconds % 1000;
        long seconds = (timeInMilliseconds / 1000) % 60;
        long minutes = (timeInMilliseconds / (1000 * 60)) % 60;
        long hours = (timeInMilliseconds / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }
}
