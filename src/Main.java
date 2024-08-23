import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int countFiles = 0;
        int countLines = 0;
        int maxLineLength = 0;
        int minLineLength = 1024;

        for (; ; ) {

            System.out.println("Введите путь к файлу");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();

            if (!fileExists || isDirectory) {
                System.out.println("Файл не существует или указанный путь является путём к папке");
                continue;
            } else {
                System.out.println("Путь указан верно");
                countFiles++;
            }
            System.out.println("Это файл номер " + countFiles);

            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;

                while ((line = reader.readLine()) != null) {
                    int length = line.length();

                    if (length > 1024) {
                        throw new StringLenghException("В файле найдена строка длиной более 1024 символов");
                    }

                    countLines++;

                    if (length > maxLineLength) {
                        maxLineLength = length;
                    }

                    if (length < minLineLength) {
                        minLineLength = length;
                    }
                }

                System.out.println("Количество строк в файле: " + countLines);
                System.out.println("Максимальная длина строки в файле в символах: " + maxLineLength);
                System.out.println("Минимальная длина строки в файле в символах: " + minLineLength);
                System.out.println();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}



