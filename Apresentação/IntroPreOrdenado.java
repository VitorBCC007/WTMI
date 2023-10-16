import java.util.Arrays;

public class IntroSortPreOrdenado {
    public static void main(String[] args) {
        int[] array = generatePreSortedArray(1000000);
        int[] introSortArray = array.clone();

        long startTime = System.currentTimeMillis();
        int introSortMovements = introSort(introSortArray);
        long introSortTime = System.currentTimeMillis() - startTime;

        System.out.println("Tempo de execução do IntroSort: " + formatTime(introSortTime));
        System.out.println("Número de movimentos do IntroSort: " + introSortMovements);
    }

    public static int[] generatePreSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i; // Gera números em ordem crescente
        }
        return array;
    }

    public static int introSort(int[] array) {
        int movements = 0;
        int maxDepth = (int) (2 * Math.log(array.length) / Math.log(2));
        return quickSort(array, 0, array.length - 1, maxDepth, movements);
    }

    public static int quickSort(int[] array, int low, int high, int maxDepth, int movements) {
        if (low < high) {
            if (maxDepth == 0) {
                movements += heapSort(array, low, high);
                return movements;
            }

            int pivotIndex = partition(array, low, high);
            movements += high - low + 1;

            movements = quickSort(array, low, pivotIndex - 1, maxDepth - 1, movements);
            movements = quickSort(array, pivotIndex + 1, high, maxDepth - 1, movements);
        }
        return movements;
    }

    public static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

    public static int heapSort(int[] array, int low, int high) {
        int movements = 0;
        for (int i = (high - low + 1) / 2 - 1; i >= 0; i--) {
            movements = maxHeapify(array, i, high - low + 1, low, movements);
        }
        for (int i = high - low; i > 0; i--) {
            int temp = array[low];
            array[low] = array[low + i];
            array[low + i] = temp;
            movements++;
            movements = maxHeapify(array, 0, i, low, movements);
        }
        return movements;
    }

    public static int maxHeapify(int[] array, int i, int heapSize, int low, int movements) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        if (left < heapSize && array[low + left] > array[low + largest]) {
            largest = left;
        }
        if (right < heapSize && array[low + right] > array[low + largest]) {
            largest = right;
        }
        if (largest != i) {
            int temp = array[low + i];
            array[low + i] = array[low + largest];
            array[low + largest] = temp;
            movements++;
            movements = maxHeapify(array, largest, heapSize, low, movements);
        }
        return movements;
    }

    public static String formatTime(long timeInMilliseconds) {
        long milliseconds = timeInMilliseconds % 1000;
        long seconds = (timeInMilliseconds / 1000) % 60;
        long minutes = (timeInMilliseconds / (1000 * 60)) % 60;
        long hours = (timeInMilliseconds / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }
}
