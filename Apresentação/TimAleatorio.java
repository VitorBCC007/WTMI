import java.util.Random;

public class TimSort {
    public static void main(String[] args) {
        int[] array = generateRandomArray(10000000);
        int[] timSortArray = array.clone();

        long startTime = System.currentTimeMillis();
        int[] movementsWrapper = new int[1]; // Usamos um array de tamanho 1 para simular uma variável passada por referência.
        timSort(timSortArray, 0, timSortArray.length - 1, movementsWrapper);
        long timSortTime = System.currentTimeMillis() - startTime;

        System.out.println("");
        System.out.println("Tempo de execução do Timsort: " + formatTime(timSortTime));
        System.out.println("Número de movimentos do Timsort: " + movementsWrapper[0]);
        
    }

    public static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000000000); // Generate random numbers between 0 and 999999999
        }
        return array;
    }

    public static void timSort(int[] array, int left, int right, int[] movementsWrapper) {
        if (left < right) {
            int mid = (left + right) / 2;
            timSort(array, left, mid, movementsWrapper);
            timSort(array, mid + 1, right, movementsWrapper);
            movementsWrapper[0] += merge(array, left, mid, right);
        }
    }

    public static int merge(int[] array, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = array[left + i];
        }

        for (int i = 0; i < rightSize; i++) {
            rightArray[i] = array[mid + 1 + i];
        }

        int i = 0, j = 0, k = left;
        int movements = 0;

        while (i < leftSize && j < rightSize) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
                movements++; // Incrementa o contador quando um elemento é movido da metade direita para o array original.
            }
        }

        while (i < leftSize) {
            array[k++] = leftArray[i++];
        }

        while (j < rightSize) {
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
