package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFrom0002To0001() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val firstCardStartBalance = dashboard.getFirstCardBalance();
        val secondCardStartBalance = dashboard.getSecondCardBalance();
        val amount = 10000;
        val firstCardExpectedBalance = firstCardStartBalance + amount;
        val secondCardExpectedBalance = secondCardStartBalance - amount;
        val topup = dashboard.topup0001();
        topup.transferMoney(String.valueOf(amount), DataHelper.getCardNumberSecond());
        assertEquals(firstCardExpectedBalance, dashboard.getFirstCardBalance());
        assertEquals(secondCardExpectedBalance, dashboard.getSecondCardBalance());
    }


    @Test
    void shouldTransferMoneyFrom0001To0002() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val firstCardStartBalance = dashboard.getFirstCardBalance();
        val secondCardStartBalance = dashboard.getSecondCardBalance();
        val amount = 10000;
        val firstCardExpectedBalance = firstCardStartBalance - amount;
        val secondCardExpectedBalance = secondCardStartBalance + amount;
        val topup = dashboard.topup0002();
        topup.transferMoney(String.valueOf(amount),DataHelper.getCardNumberFirst());
        assertEquals(secondCardExpectedBalance, dashboard.getSecondCardBalance());
        assertEquals(firstCardExpectedBalance, dashboard.getFirstCardBalance());
    }

    @Test
    void shouldNotTransferMoneyFrom0002To0001() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val amount = dashboard.getSecondCardBalance() + 20000;
        val topup = dashboard.topup0001();
        topup.transferMoney(String.valueOf(amount), DataHelper.getCardNumberSecond());
        topup.getError();
    }
}
