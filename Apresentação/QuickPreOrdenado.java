public class QuickPreOrdenado {
    public static void main(String[] args) {
        int[] array = generatePreSortedArray(1000000); // Aumente o tamanho da lista conforme necessário
        int[] quicksortArray = array.clone();

        long startTime = System.currentTimeMillis();
        int quicksortMovements = quicksortIterative(quicksortArray);
        long quicksortTime = System.currentTimeMillis() - startTime;

        System.out.println("Tempo de execução do QuickSort: " + formatTime(quicksortTime));
        System.out.println("Número de movimentos do QuickSort: " + quicksortMovements);
    }

    public static int[] generatePreSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i; // Gera números em ordem crescente
        }
        return array;
    }

    public static int quicksortIterative(int[] array) {
        int movements = 0;
        int low = 0;
        int high = array.length - 1;
        int[] stack = new int[high - low + 1];
        int top = -1;
        
        stack[++top] = low;
        stack[++top] = high;
 
        while (top >= 0) {
            high = stack[top--];
            low = stack[top--];
 
            int pivotIndex = partition(array, low, high);
            movements += high - low + 1;
 
            if (pivotIndex - 1 > low) {
                stack[++top] = low;
                stack[++top] = pivotIndex - 1;
            }
 
            if (pivotIndex + 1 < high) {
                stack[++top] = pivotIndex + 1;
                stack[++top] = high;
            }
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
