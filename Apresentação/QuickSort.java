import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuickSortFromFile {
    public static void main(String[] args) {
        try {
            // Ler os dados do arquivo
            List<Integer> dataList = readDataFromFile("dados500_mil.txt");
            int[] array = new int[dataList.size()];

            // Converter a lista para um array
            for (int i = 0; i < dataList.size(); i++) {
                array[i] = dataList.get(i);
            }

            int[] quicksortArray = array.clone();

            long startTime = System.currentTimeMillis();
            int quicksortMovements = quicksort(quicksortArray, 0, quicksortArray.length - 1);
            long quicksortTime = System.currentTimeMillis() - startTime;
            System.out.println("");
            System.out.println("Lista 500 mil dados desordenados");
            System.out.println("Tempo de execução do QuickSort: " + formatTime(quicksortTime));
            System.out.println("Número de movimentos do QuickSort: " + quicksortMovements);
            System.out.println("");

            // Salvar os dados ordenados em um novo arquivo
            saveDataToFile("dados_ordenados.txt", quicksortArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> readDataFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        List<Integer> dataList = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            // Remover os colchetes e quebrar a linha em números
            String[] numbers = line.replace("[", "").replace("]", "").split(", ");

            for (String numberStr : numbers) {
                int number = Integer.parseInt(numberStr);
                dataList.add(number);
            }
        }

        reader.close();

        return dataList;
    }

    public static void saveDataToFile(String fileName, int[] data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        for (int i = 0; i < data.length; i++) {
            writer.write(Integer.toString(data[i]));
            writer.newLine();
        }

        writer.close();
    }

    public static int quicksort(int[] array, int low, int high) {
        int movements = 0;
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            movements += high - low + 1;
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
