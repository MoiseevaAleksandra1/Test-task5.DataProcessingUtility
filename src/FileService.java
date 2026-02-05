import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class FileService {
    public static ArrayList<Person> readFromFile(String filePath) {
        BufferedReader reader;
        String line = "";
        HashSet<Integer> uniqueID = new HashSet<>();
        ArrayList<Person> persons = new ArrayList<Person>();

        try {
            reader = new BufferedReader(new FileReader(filePath));
            line = reader.readLine();

            while (line != null) {
                String[] lines = line.split("\\|", -1);
                Integer id = null;
                Integer calls = null;
                Integer sms = null;

                if (!lines[0].isEmpty()) {
                    try {
                        id = Integer.parseInt(lines[0]);
                    } catch (NumberFormatException e) {}
                }

                if (!lines[5].isEmpty()) {
                    try {
                        calls = Integer.parseInt(lines[5]);
                    } catch (Exception e) {}
                }

                if (!lines[6].isEmpty()) {
                    try {
                        sms = Integer.parseInt(lines[6]);
                    } catch (Exception e) {}
                }

                LocalDate date = null;
                if (!lines[2].isEmpty()) {
                    String[] dateStr = lines[2].split("\\.", -1);
                    if (dateStr.length == 3) {
                        try {
                            int year = Integer.parseInt(dateStr[0]);
                            int month = Integer.parseInt(dateStr[1]);
                            int day = Integer.parseInt(dateStr[2]);
                            date = LocalDate.of(year, month, day);
                        } catch (Exception e) {}
                    }
                }

                Person person = new Person(id, lines[1], date, lines[3], lines[4], calls, sms);

                if (id != null) {
                    if (uniqueID.contains(id)) {
                        person.getErrors().add("Не уникальный ID");
                        person.correct = false;
                    } else {
                        uniqueID.add(id);
                    }
                }

                persons.add(person);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("\n\n\t\tВсе записи");
        for (Person person : persons){
            System.out.println(person.getPerson());
        }
        return persons;
    }

    // Запись в файл
    public static void writeToFile(ArrayList<String> report, String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String str : report) {
                bw.write(str + "\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}