package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import data.DataHelper;
import data.SQLHelper;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanDatabase;

public class Tests {

    @AfterAll
    public static void tearDown() {
        cleanDatabase();
    }

    @Test
    @DisplayName("Авторизация с валидным логином и паролем")
    void shouldBeSuccessfulEntry() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }


    @Test
    @DisplayName("Авторизация с рандомным логином и паролем")
    void shouldBeShownError() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.user();
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationVisible();
    }

    @Test
    @DisplayName("Невалидный пользователь")
    void shouldBeInvalidUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.user();
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationVisible();
    }


    @Test
    @DisplayName("Невалидный пароль")
    public void shouldNotValidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = new DataHelper.AuthInfo(DataHelper.user().getLogin(), DataHelper.getAuthInfo().getPassword());
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationVisible();
    }

    @Test
    @DisplayName("Три раза неправильный пароль")
    public void shouldNotValidPasswordThreeTimes() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfoFirst = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(), DataHelper.user().getPassword());
        loginPage.validLogin(authInfoFirst);
        loginPage.errorNotificationVisible();
        loginPage.cleanStrings();
        var authInfoSecond = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(), DataHelper.user().getPassword());
        loginPage.validLogin(authInfoSecond);
        loginPage.errorNotificationVisible();
        loginPage.cleanStrings();
        var authInfoThird = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(), DataHelper.user().getPassword());
        loginPage.validLogin(authInfoThird);
        loginPage.getBlockError();
    }
}