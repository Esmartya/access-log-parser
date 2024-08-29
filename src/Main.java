import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        int countFiles = 0;
        int countLines = 0;
        int countGoogleBotRequests = 0;
        int countYandexBotRequests = 0;
        int maxLineLength = 0;
        int minLineLength = 1024;
        Statistics statistics = new Statistics();

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

                    String[] parts = line.split(";");
                    if (parts.length >= 2) {
                        String fragment2 = parts[1];
                        String newFragment2 = fragment2.replaceAll(" ", "");

                        String[] partsOfFragment2 = newFragment2.split("/");
                        String botName = partsOfFragment2[0];

                        if (botName.equals("Googlebot")) {
                            countGoogleBotRequests++;
                        }

                        if (botName.equals("YandexBot")) {
                            countYandexBotRequests++;
                        }
                    }

                    LogEntry logEntry = new LogEntry(line);
                    System.out.println("IP-адрес: " + logEntry.getIpAddr());
                    System.out.println("HTTP-метод: " + logEntry.getMethod());
                    System.out.println("Путь: " + logEntry.getPath());
                    System.out.println("Referer: " + logEntry.getReferer());
                    System.out.println("Код ответа: " + logEntry.getResponseCode());
                    System.out.println("Трафик: " + logEntry.getResponseSize());
                    System.out.println("Дата и время: " + logEntry.getTime());
                    System.out.println("UserAgent: " + logEntry.getUserAgent());

                    UserAgent OSAndBrowser = new UserAgent(logEntry);
                    System.out.println("Операционная система: " + OSAndBrowser.getOperatingSystem());
                    System.out.println("Браузер: " + OSAndBrowser.getBrauser());
                    System.out.println();

                    statistics.addEntry(logEntry);
                }

                double googleBotReguestsShare = (double) countGoogleBotRequests / countLines;
                double yandexBotReguestsShare = (double) countYandexBotRequests / countLines;

                System.out.println("Количество строк в файле: " + countLines);
                System.out.println("Доля запросов от Googlebot: " + googleBotReguestsShare);
                System.out.println("Доля запросов от YandexBot: " + yandexBotReguestsShare);
                System.out.println("Средний объем трафика в час: " + statistics.getTrafficRate());
                System.out.println("Список существующих страниц (код ответа 200): " + statistics.getExistingPagesSet());
                System.out.println("Список несуществующих страниц (код ответа 404): " + statistics.getNotExistingPagesSet());
                System.out.println("Количество упоминаний ОС " + statistics.getOSStatistics());
                System.out.println("Доля упоминаний ОС " + statistics.getOSShares());
                System.out.println("Сумма долей упоминаний ОС " + (statistics.getOSShares().get("Windows") +
                        statistics.getOSShares().get("Linux") + statistics.getOSShares().get("Mac OS") +
                        statistics.getOSShares().get("Other")));
                System.out.println("Количество упоминаний браузеров " + statistics.getBrausersStatistics());
                System.out.println("Доля упоминаний браузеров " + statistics.getBrausersShares());
                System.out.println("Сумма долей упоминаний браузеров " + (statistics.getBrausersShares().get("Edge") +
                        statistics.getBrausersShares().get("Opera") + statistics.getBrausersShares().get("Firefox") +
                        statistics.getBrausersShares().get("Chrome") + statistics.getBrausersShares().get("Other")));

                System.out.println();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}



