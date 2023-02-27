package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.Card;
import ru.netology.data.DbUtils;
import ru.netology.page.PaymentPage;
import ru.netology.page.StartPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;
import static ru.netology.data.DataGenerator.getValidName;

public class PaymentPageUIAndDbTest {
    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        DbUtils.clearTables();
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    //HappyPath
    //passed
    @Test
    @Order(1)
    void shouldBuyInPaymentGate() throws SQLException {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkSuccessNotification();
        assertEquals("APPROVED", DbUtils.getPaymentStatus()); //TODO Не корректно работает тест!!!
    }

    //failed
    @Test
    @Order(2)
    void shouldBuyInPaymentGateWithNameInLatinLetters() throws SQLException {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidNameInLatinLetters(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkSuccessNotification();
        assertEquals("APPROVED", DbUtils.getPaymentStatus()); //TODO Не корректно работает тест!!!
    }

    //failed
    @Test
    @Order(3)
    void shouldNotBuyInPaymentGateWithDeclinedCardNumber() throws SQLException {
        Card card = new Card(getDeclinedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkDeclineNotification();
        assertEquals("DECLINED", DbUtils.getPaymentStatus());
    }


    //CardNumberField
    //failed
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithInvalidCardNumber() throws SQLException {
        Card card = new Card(getInvalidCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkDeclineNotification();

    }

    //passed
    @Test
    @Order(2)
    void shouldNotBuyInPaymentGateWithShortCardNumber() {
        Card card = new Card(getShortCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidFormat();
    }

    //failed
    @Test
    @Order(3)
    void shouldNotBuyInPaymentGateWithEmptyCardNumber() {
        Card card = new Card(null, getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkRequiredField();
    }


    //MonthField
    //failed
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithInvalidMonth() {
        Card card = new Card(getApprovedNumber(), "00", getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidDate();
    }

    //passed
    @Test
    @Order(2)
    void shouldNotBuyInPaymentGateWithNonExistingMonth() {
        Card card = new Card(getApprovedNumber(), "13", getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidDate();

    }

    //failed
    @Test
    @Order(3)
    void shouldNotBuyInPaymentGateWithExpiredMonth() {
        Card card = new Card(getApprovedNumber(), getLastMonth(), getCurrentYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkExpiredDate();
    }

    //failed
    @Test
    @Order(4)
    void shouldNotBuyInPaymentGateWithEmptyMonth() {
        Card card = new Card(getApprovedNumber(), null, getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkRequiredField();
    }


    //YearField
    //passed
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithExpiredYear() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getLastYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkExpiredDate();
    }

    //failed
    @Test
    @Order(2)
    void shouldNotBuyInPaymentGateWithEmptyYear() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), null, getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkRequiredField();
    }


    //NameField
    //failed
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithOnlyName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlyName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidName();
    }

    //failed
    @Test
    @Order(2)
    void shouldNotBuyInPaymentGateWithOnlyNameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlyNameInLatinLetters(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidName();
    }

    //failed
    @Test
    @Order(3)
    void shouldNotBuyInPaymentGateWithOnlySurname() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlySurname(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidName();
    }

    //failed
    @Test
    @Order(4)
    void shouldNotBuyInPaymentGateWithOnlySurnameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlySurnameInLatinLetters(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidName();
    }

    //failed
    @Test
    @Order(5)
    void shouldNotBuyInPaymentGateWithNameAndSurnameWithDash() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), "Иван-Иванов", getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidFormat();
    }

    //failed
    @Test
    @Order(6)
    void shouldNotBuyInPaymentGateWithTooLongName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getTooLongName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkLongName();
    }

    //failed
    @Test
    @Order(7)
    void shouldNotBuyInPaymentGateWithDigitsInName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithNumbers(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidDataName();
    }

    //failed
    @Test
    @Order(8)
    void shouldNotBuyInPaymentGateWithTooShortName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithOneLetter(), getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkShortName();
    }

    //passed
    @Test
    @Order(9)
    void shouldNotBuyInPaymentGateWithEmptyName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), null, getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkRequiredField();
    }

    //failed
    @Test
    @Order(10)
    void shouldNotBuyInPaymentGateWithSpaceInsteadOfName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), " ", getValidCvc());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidDataName();
    }


    //CVC/CVVField
    //failed
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithOneDigitInCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getCvcWithOneDigit());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidCvc();
    }

    //failed
    @Test
    @Order(2)
    void shouldNotBuyInPaymentGateWithTwoDigitsInCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getCvcWithTwoDigits());
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkInvalidCvc();
    }

    //passed
    @Test
    @Order(3)
    void shouldNotBuyInPaymentGateWithEmptyCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), null);
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkRequiredField();
    }


    //AllEmptyFields
    //failed
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithAllEmptyFields() {
        Card card = new Card(null, null, null, null, null);
        val startPage = new StartPage();
        startPage.buy();
        val paymentPage = new PaymentPage();
        paymentPage.fulfillData(card);
        paymentPage.checkAllFieldsAreRequired();
    }

}
