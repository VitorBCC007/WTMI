public class MergeSortPreOrdenado {
    public static void main(String[] args) {
        int[] array = generatePreSortedArray(10000000); // Aumente o tamanho da lista conforme necessário
        int[] mergeSortArray = array.clone();

        long startTime = System.currentTimeMillis();
        int mergeSortMovements = mergeSort(mergeSortArray);
        long mergeSortTime = System.currentTimeMillis() - startTime;

        System.out.println("Tempo de execução do Merge Sort: " + formatTime(mergeSortTime));
        System.out.println("Número de movimentos do Merge Sort: " + mergeSortMovements);
    }

    public static int[] generatePreSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i; // Gera números em ordem crescente
        }
        return array;
    }

    public static int mergeSort(int[] array) {
        int[] temp = new int[array.length];
        return mergeSort(array, temp, 0, array.length - 1);
    }

    public static int mergeSort(int[] array, int[] temp, int left, int right) {
        int movements = 0;
        if (left < right) {
            int mid = (left + right) / 2;
            movements += mergeSort(array, temp, left, mid);
            movements += mergeSort(array, temp, mid + 1, right);
            movements += merge(array, temp, left, mid, right);
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

    public static String formatTime(long timeInMilliseconds) {
        long milliseconds = timeInMilliseconds % 1000;
        long seconds = (timeInMilliseconds / 1000) % 60;
        long minutes = (timeInMilliseconds / (1000 * 60)) % 60;
        long hours = (timeInMilliseconds / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }
}
