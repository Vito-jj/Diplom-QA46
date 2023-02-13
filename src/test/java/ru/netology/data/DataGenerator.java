package ru.netology.data;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.time.LocalDate;
import java.time.Year;
import java.util.Locale;

public class DataGenerator {
    public static Faker faker = new Faker(new Locale("en"));

    public static String getApprovedNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidCardNumber() {
        return "1111 4444 4444 8888";
    }

    public static String getShortCardNumber() {
        return "4444 4444 4444 444";
    }

    public static String getEmptyNumber() {
        return null;
    }

    public static String getCurrentMonth() {
        LocalDate localDate = LocalDate.now();
        return String.format("%02d", localDate.getMonthValue());
    }

    //отнимает не месяц, а год, как сделать месяц?
    public static String getLastMonth() {
        LocalDate localDate = LocalDate.now();
        LocalDate lastMonth = localDate.minusMonths(1);
        return String.valueOf(lastMonth);

    }

    public static String getInvalidMonth() {
        return "00";
    }

    public static String getNonExistingMonth() {
        return "15";
    }

    public static String getEmptyMonth() {
        return null;
    }

    public static String getCurrentYear() {
        return String.format("%ty", Year.now());
    }

    public static String getLastYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.minusYears(1));
    }

    public static String getNextYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.plusYears(1));
    }

    public static String getEmptyYear() {
        return null;

    }

    public static String getValidName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getValidNameInLatinLetters() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getOnlyName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName();
    }

    public static String getOnlyNameInLatinLetters() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    public static String getOnlySurname() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().lastName();
    }

    public static String getOnlySurnameInLatinLetters() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().lastName();
    }

    public static String getNameWithDash() {
        return "Виталий-Шилкин";
    }

    public static String getTooLongName() {
        return faker.lorem().fixedString(200);
    }

    public static String getNameWithNumbers() {
        return faker.number().digits(9);
    }

    public static String getNameWithOneLetter() {
        return faker.lorem().characters(1);
    }

    public static String getEmptyName() {
        return null;
    }

    public static String getNameWithSpace() {
        return " ";
    }

    public static String getValidCvc() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("###");
    }

    public static String getCvcWithOneDigit() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("#");
    }

    public static String getCvcWithTwoDigits() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("##");
    }

    public static String getEmptyCvc() {
        return null;
    }
}
