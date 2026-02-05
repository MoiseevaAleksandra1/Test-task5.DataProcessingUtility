import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;

public class Person {
    private Integer ID;
    private String FullName;
    private LocalDate Date;
    private String Number;
    private String Tariff;
    private Integer Calls;
    private Integer Sms;
    boolean correct = false;
    private ArrayList<String> Errors = new ArrayList<String>();

    public Person(Integer id, String fullName, LocalDate date, String number, String tariff, Integer calls, Integer sms) {
        setID(id);
        setName(fullName);
        setDate(date);
        setNumber(number);
        setTariff(tariff);
        setCalls(calls);
        setSms(sms);

        correct = Errors.isEmpty();
    }
    public void setID(Integer id) {
        if (id == null) {
            Errors.add("Пустое значение ID");
        } else if (id <= 0) {
            Errors.add("Значение ID < 0");
        }
        ID = id;
    }

    public void setName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            Errors.add("Пустое значение имени");
        } else {
            String trimmedName = fullName.trim();
            String[] name = trimmedName.split("\\s+");
            if (name.length != 3) {
                Errors.add("Не полное ФИО");
            } else {
                for (String word : name) {
                    if (word.isEmpty()) continue;

                    char firstLetter = word.charAt(0);
                    if (Character.isLowerCase(firstLetter)) {
                        Errors.add("ФИО начинается с маленькой буквы");
                    }

                    // Разрешаем дефисы в именах/фамилиях
                    if (!word.matches("[А-ЯЁ][а-яё-]*")) {
                        Errors.add("ФИО содержит недопустимые символы");
                    }
                }
            }
        }
        this.FullName = fullName;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            Errors.add("Пустое значение даты рождения");
        } else {
            LocalDate now = LocalDate.now();
            if (date.isAfter(now)) {
                Errors.add("Дата рождения больше нынешней даты");
            }

            LocalDate minDate = now.minusYears(14);
            if (date.isAfter(minDate)) {
                Errors.add("Возраст должен быть 14 лет и старше");
            }

            LocalDate maxDate = now.minusYears(140);
            if (date.isBefore(maxDate)) {
                Errors.add("Нереалистично больной возраст (более 140 лет)");
            }
        }
        this.Date = date;
    }

    public void setNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            Errors.add("Пустое значение номера телефона");
        } else {
            if (!number.matches("[0-9+]+")) {
                Errors.add("Номер телефона должен содержать только цифры и знак +");
            }
            if (number.startsWith("+7")) {
                if (number.length() != 12) {
                    Errors.add("Номер телефона должен быть длиной 12 символов (включая +7)");
                }
                String digits = number.substring(2);
            } else if (number.startsWith("8")) {
                if (number.length() != 11) {
                    Errors.add("Номер телефона должен быть длиной 11 символов (начиная с 8)");
                }
            } else {
                Errors.add("Номер телефона должен начинаться с +7 или 8");
            }
        }
        Number = number;
    }

    public void setTariff(String tariff){
        if (tariff == null || tariff.trim().isEmpty()){
            Errors.add("Значение тарифа пустое");
        } else {
            String[] validTariffs = {"Безлимит", "Эконом", "Бизнес", "Стандарт", "Премиум"};
            if (!Arrays.asList(validTariffs).contains(tariff.trim())) {
                Errors.add("Несуществующий тариф");
            }
        }
        Tariff = tariff;
    }

    public void setCalls(Integer calls){
        if (calls == null) {
            Errors.add("Пустое значение количества звонков");
        } else if (calls < 0) {
            Errors.add("Количество звонков не может быть отрицательным");
        } else if (calls > 500) {
            Errors.add("Нереалистично большое количество звонков");
        }
        Calls = calls;
    }

    public void setSms(Integer sms){
        if (sms == null) {
            Errors.add("Пустое значение количества СМС");
        } else if (sms < 0) {
            Errors.add("Количество СМС не может быть отрицательным");
        } else if (sms > 10000) {
            Errors.add("Нереалистично большое количество СМС");
        }
        Sms = sms;
    }

    public String getPerson() {
        return String.format("ID: %s ФИО: %s Дата рождения: %s Номер телефона: %s Тариф: %s Кол-во звонков: %s Кол-во СМС: %s",
                ID, FullName, Date, Number, Tariff, Calls, Sms);
    }

    public Integer getID() { return ID; }

    public String getName() { return FullName; }

    public LocalDate getDate() { return Date; }

    public String getNumber() { return Number; }

    public ArrayList<String> getErrors() { return Errors; }

    public String getTariff(){
        return Tariff;
    }

    public Integer getCalls(){
        return Calls;
    }

    public Integer getSms(){
        return Sms;
    }

    public ArrayList<String> printErrors(){
        ArrayList<String> result = new ArrayList<>();
        result.add("Ошибки:");
        for (String error : Errors) {
            result.add("  - " + error);
        }
        return result;
    }
}