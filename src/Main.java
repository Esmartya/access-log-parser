import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;

        for ( ; ; ) {

            System.out.println("������� ���� � �����");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();

            if (!fileExists || isDirectory) {
                System.out.println("���� �� ���������� ��� ��������� ���� �������� ���� � �����");
                continue;
            } else {
                System.out.println("���� ������ �����");
                count++;
            }
            System.out.println("��� ���� ����� " + count);
        }
    }
}

