import java.util.ArrayList;

public class Report {
    public static ArrayList<String> analyze(ArrayList<Person> persons) { // Отчет
        int count = persons.size();
        ArrayList<String> report = new ArrayList<>();
        ArrayList<Person> correctPersons = new ArrayList<>();
        ArrayList<Person> incorrectPersons = new ArrayList<>();

        report.add("\n\t\tВсе записи:");
        for (Person person : persons) {
            if (person.correct) {
                correctPersons.add(person);
            } else {
                incorrectPersons.add(person);
            }
            report.add(person.getPerson());
        }
        report.add("\n\n\t\tОтчет");
        report.add("\nОбщее количество записей: " + count);
        report.add("Количество корректных записей: " + correctPersons.size());
        report.add("Количество некорректных записей: " + incorrectPersons.size());
        report.add("\nСписок ошибок с указанием причины:");
        for (Person person : incorrectPersons) {
            report.add(person.getPerson());
            report.addAll(person.printErrors());
        }

        if (!correctPersons.isEmpty()) {
            report.addAll(Statistic.Statistic(correctPersons));
        } else {System.out.println("\nНет корректных записей для ститистики");}
        return report;
    }
}
