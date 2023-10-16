import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MergeSortFromFile {
    public static void main(String[] args) {
        try {
            // Ler os dados do arquivo
            List<Integer> dataList = readDataFromFile("dados500_mil.txt");
            int[] array = new int[dataList.size()];

            // Converter a lista para um array
            for (int i = 0; i < dataList.size(); i++) {
                array[i] = dataList.get(i);
            }

            int[] mergeSortArray = array.clone();

            long startTime = System.currentTimeMillis();
            int mergeSortMovements = mergeSort(mergeSortArray);
            long mergeSortTime = System.currentTimeMillis() - startTime;
            System.out.println("");
            System.out.println("Lista 500 mil dados desordenados");
            System.out.println("Tempo de execução do MergeSort: " + formatTime(mergeSortTime));
            System.out.println("Número de movimentos do MergeSort: " + mergeSortMovements);
            System.out.println("");

            // Salvar os dados ordenados em um novo arquivo
            saveDataToFile("dados_ordenados_merge_sort.txt", mergeSortArray);
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

    public static int mergeSort(int[] array) {
        int[] temp = new int[array.length];
        return mergeSort(array, temp, 0, array.length - 1);
    }

    public static int mergeSort(int[] array, int[] temp, int low, int high) {
        int movements = 0;
        if (low < high) {
            int mid = (low + high) / 2;
            movements += mergeSort(array, temp, low, mid);
            movements += mergeSort(array, temp, mid + 1, high);
            movements += merge(array, temp, low, mid, high);
        }
        return movements;
    }

    public static int merge(int[] array, int[] temp, int low, int mid, int high) {
        int movements = 0;
        int i = low;
        int j = mid + 1;
        int k = low;

        while (i <= mid && j <= high) {
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

        while (j <= high) {
            temp[k++] = array[j++];
            movements++;
        }

        for (i = low; i <= high; i++) {
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
