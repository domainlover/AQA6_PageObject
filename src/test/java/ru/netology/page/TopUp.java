package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TopUp {

    private SelenideElement error = $(byText("Недостаточно денег на счете"));

    public void getError() {
        error.shouldBe(Condition.visible);
    }

    public void transferMoney(String amount, DataHelper.CardInfo info) {
        $("[data-test-id='amount'] .input__control").setValue(amount);
        $("[data-test-id='from'] .input__control").setValue(info.getCardNumber());
        $("[data-test-id='action-transfer']").click();
    }
}
