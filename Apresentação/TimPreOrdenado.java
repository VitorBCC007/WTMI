import java.util.Arrays;

public class TimSortPreOrdenado {
    public static void main(String[] args) {
        int[] array = generatePreSortedArray(10000000); // Aumente o tamanho da lista conforme necessário
        int[] timSortArray = array.clone();

        long startTime = System.currentTimeMillis();
        int timSortMovements = timSort(timSortArray);
        long timSortTime = System.currentTimeMillis() - startTime;

        System.out.println("Tempo de execução do Tim Sort: " + formatTime(timSortTime));
        System.out.println("Número de movimentos do Tim Sort: " + timSortMovements);
    }

    public static int[] generatePreSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i; // Gera números em ordem crescente
        }
        return array;
    }

    public static int timSort(int[] array) {
        int[] temp = new int[array.length];
        int movements = 0;

        for (int i = 0; i < array.length; i += 32) {
            insertionSort(array, i, Math.min((i + 31), (array.length - 1)));
        }

        for (int size = 32; size < array.length; size = size * 2) {
            for (int left = 0; left < array.length; left += size * 2) {
                int mid = Math.min((left + size - 1), (array.length - 1));
                int right = Math.min((left + 2 * size - 1), (array.length - 1));
                movements += merge(array, temp, left, mid, right);
            }
        }
        return movements;
    }

    public static int merge(int[] array, int[] temp, int left, int mid, int right) {
        int movements = 0;
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
            movements++;
        }

        while (i <= mid) {
            temp[k++] = array[i++];
            movements++;
        }

        while (j <= right) {
            temp[k++] = array[j++];
            movements++;
        }

        for (i = left; i <= right; i++) {
            array[i] = temp[i];
        }
        return movements;
    }

    public static void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= left && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
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
