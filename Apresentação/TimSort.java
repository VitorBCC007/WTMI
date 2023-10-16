import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TimSortFromFile {
    public static void main(String[] args) {
        try {
            // Ler os dados do arquivo
            List<Integer> dataList = readDataFromFile("dados500_mil.txt");
            int[] array = new int[dataList.size()];

            // Converter a lista para um array
            for (int i = 0; i < dataList.size(); i++) {
                array[i] = dataList.get(i);
            }

            int[] timSortArray = array.clone();

            long startTime = System.currentTimeMillis();
            int timSortMovements = timSort(timSortArray);
            long timSortTime = System.currentTimeMillis() - startTime;
            System.out.println("");
            System.out.println("Lista 500 mil dados desordenados");
            System.out.println("Tempo de execução do TimSort: " + formatTime(timSortTime));
            System.out.println("Número de movimentos do TimSort: " + timSortMovements);
            System.out.println("");

            // Salvar os dados ordenados em um novo arquivo
            saveDataToFile("dados_ordenados_tim_sort.txt", timSortArray);
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

    public static int timSort(int[] array) {
        int movements = 0;
        int n = array.length;
        int minRun = 32;

        for (int i = 0; i < n; i += minRun) {
            int end = Math.min((i + minRun - 1), (n - 1));
            insertSort(array, i, end);
        }

        for (int size = minRun; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = Math.min((left + size - 1), (n - 1));
                int right = Math.min((left + 2 * size - 1), (n - 1));

                if (mid < right) {
                    movements += merge(array, left, mid, right);
                }
            }
        }

        return movements;
    }

    public static int merge(int[] array, int left, int mid, int right) {
        int movements = 0;
        int len1 = mid - left + 1;
        int len2 = right - mid;

        int[] leftArray = new int[len1];
        int[] rightArray = new int[len2];

        for (int i = 0; i < len1; i++) {
            leftArray[i] = array[left + i];
        }

        for (int j = 0; j < len2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < len1 && j < len2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
            movements++;
        }

        while (i < len1) {
            array[k] = leftArray[i];
            i++;
            k++;
            movements++;
        }

        while (j < len2) {
            array[k] = rightArray[j];
            j++;
            k++;
            movements++;
        }

        return movements;
    }

    public static void insertSort(int[] array, int left, int right) {
        int n = right - left + 1;
        for (int i = 1; i < n; i++) {
            int key = array[left + i];
            int j = i - 1;

            while (j >= 0 && array[left + j] > key) {
                array[left + j + 1] = array[left + j];
                j--;
            }
            array[left + j + 1] = key;
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
