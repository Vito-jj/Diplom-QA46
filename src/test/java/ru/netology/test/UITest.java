package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.Card;
import ru.netology.page.CreditPage;
import ru.netology.page.PaymentPage;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.*;

public class UITest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //HappyPath
    @Test
    @Order(1)
    void shouldBuyInPaymentGate() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.successNotification();

    }

    @Test
    @Order(2)
    void shouldBuyInCreditGate() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.successNotification();
    }

    @Test
    @Order(3)
    void shouldBuyInPaymentGateWithNameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidNameInLatinLetters(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.successNotification();
    }

    @Test
    @Order(4)
    void shouldBuyInCreditGateWithNameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidNameInLatinLetters(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.successNotification();
    }

    @Test
    @Order(5)
    void shouldNotBuyInPaymentGateWithDeclinedCardNumber() {
        Card card = new Card(getDeclinedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.declineNotification();
    }

    @Test
    @Order(6)
    void shouldNotBuyInCreditGateWithDeclinedCardNumber() {
        Card card = new Card(getDeclinedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.declineNotification();
    }

    //CardNumberField
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithInvalidCardNumber() {
        Card card = new Card(getInvalidCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.declineNotification();
    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithInvalidCardNumber() {
        Card card = new Card(getInvalidCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.declineNotification();
    }

    @Test
    @Order(3)
    void shouldNotBuyInPaymentGateWithShortCardNumber() {
        Card card = new Card(getShortCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidFormat();
    }

    @Test
    @Order(4)
    void shouldNotBuyInCreditGateWithShortCardNumber() {
        Card card = new Card(getShortCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidFormat();
    }

    @Test
    @Order(5)
    void shouldNotBuyInPaymentGateWithEmptyCardNumber() {
        Card card = new Card(getEmptyNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.requiredField();
    }

    @Test
    @Order(6)
    void shouldNotBuyInCreditGateWithEmptyCardNumber() {
        Card card = new Card(getEmptyNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.requiredField();
    }

    //MonthField
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithInvalidMonth() {
        Card card = new Card(getApprovedNumber(), getInvalidMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidDate();
    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithInvalidMonth() {
        Card card = new Card(getApprovedNumber(), getInvalidMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidDate();
    }

    @Test
    @Order(3)
    void shouldNotBuyInPaymentGateWithNonExistingMonth() {
        Card card = new Card(getApprovedNumber(), getNonExistingMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidDate();

    }

    @Test
    @Order(4)
    void shouldNotBuyInCreditGateWithNonExistingMonth() {
        Card card = new Card(getApprovedNumber(), getNonExistingMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidDate();

    }

    @Test
    @Order(5)
    void shouldNotBuyInPaymentGateWithExpiredMonth() {
        Card card = new Card(getApprovedNumber(), getLastMonth(), getCurrentYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.expiredDate();
    }

    @Test
    @Order(6)
    void shouldNotBuyInCreditGateWithExpiredMonth() {
        Card card = new Card(getApprovedNumber(), getLastMonth(), getCurrentYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.expiredDate();
    }

    @Test
    @Order(7)
    void shouldNotBuyInPaymentGateWithEmptyMonth() {
        Card card = new Card(getApprovedNumber(), getEmptyMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.requiredField();
    }

    @Test
    @Order(8)
    void shouldNotBuyInCreditGateWithEmptyMonth() {
        Card card = new Card(getApprovedNumber(), getEmptyMonth(), getNextYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.requiredField();
    }

    //
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithExpiredYear() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getLastYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.expiredDate();
    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithExpiredYear() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getLastYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.expiredDate();
    }

    @Test
    @Order(3)
    void shouldNotBuyInPaymentGateWithEmptyYear() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getEmptyYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.requiredField();
    }

    @Test
    @Order(4)
    void shouldNotBuyInCreditGateWithEmptyYear() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getEmptyYear(), getValidName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.requiredField();
    }

    //NameField
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithOnlyName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlyName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidName();
    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithOnlyName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlyName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidName();
    }

    @Test
    @Order(3)
    void shouldNotBuyInPaymentGateWithOnlyNameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlyNameInLatinLetters(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidName();
    }

    @Test
    @Order(4)
    void shouldNotBuyInCreditGateWithOnlyNameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlyNameInLatinLetters(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidName();
    }

    @Test
    @Order(5)
    void shouldNotBuyInPaymentGateWithOnlySurname() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlySurname(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidName();
    }

    @Test
    @Order(6)
    void shouldNotBuyInCreditGateWithOnlySurname() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlySurname(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidName();
    }

    @Test
    @Order(7)
    void shouldNotBuyInPaymentGateWithOnlySurnameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlySurnameInLatinLetters(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidName();
    }

    @Test
    @Order(8)
    void shouldNotBuyInCreditGateWithOnlySurnameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlySurnameInLatinLetters(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidName();
    }

    @Test
    @Order(9)
    void shouldNotBuyInPaymentGateWithNameAndSurnameWithDash() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithDash(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidFormat();
    }

    @Test
    @Order(10)
    void shouldNotBuyInCreditGateWithNameAndSurnameWithDash() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithDash(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidFormat();
    }

    @Test
    @Order(11)
    void shouldNotBuyInPaymentGateWithTooLongName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getTooLongName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.longName();
    }

    @Test
    @Order(12)
    void shouldNotBuyInCreditGateWithTooLongName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getTooLongName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.longName();
    }

    @Test
    @Order(13)
    void shouldNotBuyInPaymentGateWithDigitsInName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithNumbers(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidDataName();
    }

    @Test
    @Order(14)
    void shouldNotBuyInCreditGateWithDigitsInName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithNumbers(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidDataName();
    }

    @Test
    @Order(15)
    void shouldNotBuyInPaymentGateWithTooShortName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithOneLetter(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.shortName();
    }

    @Test
    @Order(16)
    void shouldNotBuyInCreditGateWithTooShortName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithOneLetter(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.shortName();
    }

    @Test
    @Order(17)
    void shouldNotBuyInPaymentGateWithEmptyName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getEmptyName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.requiredField();
    }

    @Test
    @Order(18)
    void shouldNotBuyInCreditGateWithEmptyName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getEmptyName(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.requiredField();
    }

    @Test
    @Order(19)
    void shouldNotBuyInPaymentGateWithSpaceInsteadOfName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithSpace(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidDataName();
    }

    @Test
    @Order(20)
    void shouldNotBuyInCreditGateWithSpaceInsteadOfName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithSpace(), getValidCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidDataName();
    }

    //CVC/CVVField
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithOneDigitInCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getCvcWithOneDigit());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidCvc();
    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithOneDigitInCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getCvcWithOneDigit());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidCvc();
    }

    @Test
    @Order(3)
    void shouldNotBuyInPaymentGateWithTwoDigitsInCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getCvcWithTwoDigits());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.invalidCvc();
    }

    @Test
    @Order(4)
    void shouldNotBuyInCreditGateWithTwoDigitsInCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getCvcWithTwoDigits());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.invalidCvc();
    }

    @Test
    @Order(5)
    void shouldNotBuyInPaymentGateWithEmptyCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getEmptyCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.requiredField();
    }

    @Test
    @Order(6)
    void shouldNotBuyInCreditGateWithEmptyCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getEmptyCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.requiredField();
    }

    //AllEmptyFields
    @Test
    @Order(1)
    void shouldNotBuyInPaymentGateWithAllEmptyFields() {
        Card card = new Card(getEmptyNumber(), getEmptyMonth(), getEmptyYear(), getEmptyName(), getEmptyCvc());
        val StartPage = new StartPage();
        StartPage.buy();
        val PaymentPage = new PaymentPage();
        PaymentPage.fulfillData(card);
        PaymentPage.requiredField();
    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithAllEmptyFields() {
        Card card = new Card(getEmptyNumber(), getEmptyMonth(), getEmptyYear(), getEmptyName(), getEmptyCvc());
        val StartPage = new StartPage();
        StartPage.buyInCredit();
        val CreditPage = new CreditPage();
        CreditPage.fulfillData(card);
        CreditPage.requiredField();

    }
}
