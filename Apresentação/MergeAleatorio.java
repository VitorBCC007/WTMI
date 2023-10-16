import java.util.Random;
import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] array = generateRandomArray(50000000);
        int[] mergesortArray = array.clone();

        long startTime = System.currentTimeMillis();
        int mergesortMovements = mergesort(mergesortArray, 0, mergesortArray.length - 1);
        long mergesortTime = System.currentTimeMillis() - startTime;

        System.out.println("Tempo de execução do MergeSort: " + formatTime(mergesortTime));
        System.out.println("Número de movimentos do MergeSort: " + mergesortMovements);
    }

    public static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000000000); // Generate random numbers between 0 and 999999999
        }
        return array;
    }

    public static int mergesort(int[] array, int low, int high) {
        int movements = 0;
        if (low < high) {
            int mid = (low + high) / 2;
            movements += mergesort(array, low, mid);
            movements += mergesort(array, mid + 1, high);
            movements += merge(array, low, mid, high);
        }
        return movements;
    }

    public static int merge(int[] array, int low, int mid, int high) {
        int movements = 0;
        int leftLength = mid - low + 1;
        int rightLength = high - mid;

        int[] leftArray = Arrays.copyOfRange(array, low, low + leftLength);
        int[] rightArray = Arrays.copyOfRange(array, mid + 1, mid + 1 + rightLength);

        int i = 0, j = 0, k = low;

        while (i < leftLength && j < rightLength) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
                movements++; // Incrementa o contador apenas quando um elemento é movido da metade direita para o array original.
            }
        }

        while (i < leftLength) {
            array[k++] = leftArray[i++];
        }

        while (j < rightLength) {
            array[k++] = rightArray[j++];
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
