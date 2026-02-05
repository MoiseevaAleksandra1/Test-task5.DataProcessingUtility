import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistic {
    public static ArrayList<String> Statistic(ArrayList<Person> persons) { // Агрегированная статистика
        ArrayList<String> statistic = new ArrayList<>();
        statistic.add("\n\t\tАгрегированная статистика");
        statistic.addAll(StatisticAge(persons));
        statistic.addAll(StatisticPhone(persons));
        statistic.addAll(StatisticTariffs(persons));
        return statistic;
    }

    private static ArrayList<String> StatisticAge(ArrayList<Person> persons) { // Статистика по возрастам
        ArrayList<String> statistic = new ArrayList<>();
        Integer minAge = Integer.MAX_VALUE;
        Integer maxAge = 0;
        Integer sumAge = 0;

        HashMap<String, Integer> ageGroups = new HashMap<>();
        ageGroups.put("0-12", 0);
        ageGroups.put("13-17", 0);
        ageGroups.put("18-24", 0);
        ageGroups.put("25-44", 0);
        ageGroups.put("45-59", 0);
        ageGroups.put("60-74", 0);
        ageGroups.put("75+", 0);

        for (Person person : persons) {
            if (person.getDate() == null) continue;
            Integer age = Period.between(person.getDate(), LocalDate.now()).getYears();

            if (age <= 12) {
                ageGroups.put("0-12", ageGroups.get("0-12") + 1);
            } else if (age <= 17) {
                ageGroups.put("13-17", ageGroups.get("13-17") + 1);
            } else if (age <= 24) {
                ageGroups.put("18-24", ageGroups.get("18-24") + 1);
            } else if (age <= 44) {
                ageGroups.put("25-44", ageGroups.get("25-44") + 1);
            } else if (age <= 59) {
                ageGroups.put("45-59", ageGroups.get("45-59") + 1);
            } else if (age <= 74) {
                ageGroups.put("60-74", ageGroups.get("60-74") + 1);
            } else {
                ageGroups.put("75+", ageGroups.get("75+") + 1);
            }

            maxAge = Math.max(maxAge, age);
            minAge = Math.min(minAge, age);
            sumAge += age;
        }

        statistic.add("\n\t\tСтатистика по возрастам");
        statistic.add("Минимальный возраст: " + (minAge == Integer.MAX_VALUE ? 0 : minAge));
        statistic.add("Максимальный возраст: " + maxAge);
        statistic.add("Средний возраст: " + String.format("%.1f", (double) sumAge / persons.size()));

        statistic.add("\nРаспределение по возрастным группам:");
        String[] groups = {"0-12", "13-17", "18-24", "25-44", "45-59", "60-74", "75+"};
        for (String group : groups) {
            statistic.add(group + " лет: " + ageGroups.get(group) + " чел.");
        }
        return statistic;
    }

    private static ArrayList<String> StatisticPhone(ArrayList<Person> persons) { // Статистика по номерам телефонов
        ArrayList<String> statistic = new ArrayList<>();
        Map<String, Integer> phoneCodes = new HashMap<>();

        for (Person person : persons) {
            String number = person.getNumber();

            if (number.startsWith("+7")) {
                String code = number.substring(2, 5);
                phoneCodes.put(code, phoneCodes.getOrDefault(code, 0) + 1);
            } else if (number.startsWith("8")) {
                String code = number.substring(1, 4);
                phoneCodes.put(code, phoneCodes.getOrDefault(code, 0) + 1);
            }
        }

        statistic.add("\n\t\tСтатистика по номерам телефонов");

        List<Map.Entry<String, Integer>> sortedCodes = new ArrayList<>(phoneCodes.entrySet());
        sortedCodes.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });

        statistic.add("Количество кодов операторов");
        for (Map.Entry<String, Integer> entry : sortedCodes) {
            statistic.add("Код: " + entry.getKey() + " кол-во: " + entry.getValue());
        }
        return statistic;
    }

    private static ArrayList<String> StatisticTariffs(ArrayList<Person> persons){ // Статистика по тарифам
        ArrayList<String> statistic = new ArrayList<>();
        statistic.add("\n\t\tСтатистика по тарифам");
        Map<String, double[]> tariffData = new HashMap<>();

        for (Person person: persons){
            String tariff = person.getTariff();
            double[] data = tariffData.getOrDefault(tariff, new double[3]);

            if (person.getSms() != null) {
                data[0] += person.getSms();
            }
            if (person.getCalls() != null) {
                data[1] += person.getCalls();
            }
            data[2]++;
            tariffData.put(tariff, data);
        }

        statistic.add("Взаимосвязь тарифа и количества СМС и звонков в месяц");
        for (Map.Entry<String, double[]> entry : tariffData.entrySet()) {
            String tariff = entry.getKey();
            double[] data = entry.getValue();

            double avgSms = data[0] / data[2];
            double avgCalls = data[1] / data[2];
            String line = String.format("Тариф: %-10s Среднее кол-во СМС: %5.1f Среднее кол-во минут: %5.1f Кол-во людей: %3d",
                    tariff, avgSms, avgCalls, (int) data[2]);
            statistic.add(line);
        }
        return statistic;
    }
}