package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.Keys;
import com.codeborne.selenide.Condition;

import java.time.Duration;


public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }

    public void cleanStrings() {
        loginField.doubleClick().sendKeys(Keys.DELETE);
        passwordField.doubleClick().sendKeys(Keys.DELETE);
    }

    public void errorNotificationVisible() {
        errorNotification.shouldBe(visible);
    }

    public void getBlockError() {
        errorNotification.shouldHave(Condition.text("Вы ввели неверный пароль 3 раза. Возможность входа в личный кабинет заблокирована. Обратитесь в службу поддержки банка.")).shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

}