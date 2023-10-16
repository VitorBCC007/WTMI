import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Introsort {
    public static void main(String[] args) {
        // Leitura dos valores do arquivo
        int[] array = readValuesFromFile("dados500_mil.txt");

        if (array == null) {
            System.err.println("Erro ao ler os valores do arquivo.");
            return;
        }

        // Clone o array para cada método de ordenação
        int[] introsortArray = array.clone();

        // Execute o Introsort e meça o tempo e os movimentos
        long startTime = System.currentTimeMillis();
        int introsortMovements = introsort(introsortArray);
        long introsortTime = System.currentTimeMillis() - startTime;
        System.out.println("");
        System.out.println("Lista 500 mil dados desordenados");
        System.out.println("Tempo de execução do Introsort: " + formatTime(introsortTime));
        System.out.println("Número de movimentos do Introsort: " + introsortMovements);
        System.out.println("");
    }

    public static int introsort(int[] array) {
        int maxDepth = (int) (Math.log(array.length) * 2);
        return introsort(array, 0, array.length - 1, maxDepth);
    }

    public static int introsort(int[] array, int low, int high, int maxDepth) {
        if (low < high) {
            if (maxDepth == 0) {
                heapsort(array, low, high);
                return 0;
            } else {
                int pivotIndex = partition(array, low, high);
                int movements = high - low;
                movements += introsort(array, low, pivotIndex - 1, maxDepth - 1);
                movements += introsort(array, pivotIndex + 1, high, maxDepth - 1);
                return movements;
            }
        }
        return 0;
    }

    public static void heapsort(int[] array, int low, int high) {
        // Implemente o algoritmo heapsort aqui
    }

    public static int[] readValuesFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            line = line.replace("[", "").replace("]", ""); // Remover colchetes
            String[] values = line.split(", "); // Dividir a string em valores individuais
            int[] array = new int[values.length];

            for (int i = 0; i < values.length; i++) {
                array[i] = Integer.parseInt(values[i]); // Converter cada valor em um número inteiro
            }

            return array;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
