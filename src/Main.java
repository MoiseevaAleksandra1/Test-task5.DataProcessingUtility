import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine();
        if (filePath.isEmpty()){
            System.out.println("Используется файл по умолчанию: Clients.txt");
            filePath = "Clients.txt";
        }

        ArrayList<Person> persons = FileService.readFromFile(filePath);
        ArrayList<String> report = Report.analyze(persons);

        System.out.println("\n");
        for (String str : report) {
            System.out.println(str);
        }

        FileService.writeToFile(report, "C:\\Severstal\\DataProcessingUtility\\Report.txt");
    }
}
