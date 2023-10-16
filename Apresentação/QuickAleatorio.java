import java.util.Random;

public class QuickSort {
    public static void main(String[] args) {
        int[] array = generateRandomArray(50000000);
        int[] quicksortArray = array.clone();

        long startTime = System.currentTimeMillis();
        int quicksortMovements = quicksort(quicksortArray, 0, quicksortArray.length - 1);
        long quicksortTime = System.currentTimeMillis() - startTime;
        System.out.println("");
        System.out.println("Lista 500 mil dados desordenados");
        System.out.println("Tempo de execução do QuickSort: " + formatTime(quicksortTime));
        System.out.println("Número de movimentos do QuickSort: " + quicksortMovements);
        System.out.println("");
    }

    public static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000000000); // Generate random numbers between 0 and 999999999
        }
        return array;
    }

    public static int quicksort(int[] array, int low, int high) {
        int movements = 0;
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            movements += high - low + 1; // Movimentos para a esquerda e para a direita, incluindo o próprio pivô
            movements += quicksort(array, low, pivotIndex - 1);
            movements += quicksort(array, pivotIndex + 1, high);
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

    public static String formatTime(long timeInMilliseconds) {
        long milliseconds = timeInMilliseconds % 1000;
        long seconds = (timeInMilliseconds / 1000) % 60;
        long minutes = (timeInMilliseconds / (1000 * 60)) % 60;
        long hours = (timeInMilliseconds / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }
}
