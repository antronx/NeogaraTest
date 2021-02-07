package com.test.app;


import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Epic("Epic: Tests to verify Main Menu items")
@ExtendWith(com.test.app.TestWatcher.class)
public class UITests {
    public static ChromeDriver driver;
    static WebDriverWait waitVar;
    private static TestForm testForm;


    @BeforeAll
    static void setUp() {
        ChromeOptions options = new ChromeOptions();
        //options.setHeadless(true);
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver_win32/chromedriver.exe");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        waitVar = new WebDriverWait(driver, 10);
        /**
         * driver.navigate().to использует html файл нахдящийся в com/test/app/test.html
         */
        driver.navigate().to("file:///" + System.getProperty("user.dir") + "\\src\\main\\java\\com\\test\\app\\test.html");
        driver.manage().window().maximize();
    }


    @Test
    @DisplayName("Happy Path UI Test")
    @Description("Happy path сценарий. Вводим валидные данные во все поля и отправляем. Проверяем, что не появляется алертов.")
    public void happyPath() {
        Random r = new Random();
        testForm = new TestForm(driver);
        testForm.enterFirstName("TestFirst");
        testForm.enterLastName("TestLast");
        testForm.enterEmail(r + "test@testmail.com");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsNotDisplayed();
    }

    ///////////////////////////////////
    /*
    Проверки для полей First Name и Last Name
     */
    ///////////////////////////////////
    @Test
    @DisplayName("Send a form with blank First and Last Names")
    @Description("Проверка на то, First Name и Last Name могут быть пустыми")
    public void emptyNamesTest() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("");
        testForm.enterLastName("");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsNotDisplayed();
    }

    @Test
    @DisplayName("First Name, Last Name. 60 chars is accepted.")
    @Description("Проверка, что в поле First Name и Last Name можно ввести 60 символов")
    public void upperBoundaryNamesValidTest() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("Abcdtgvghvhgvhgvghvhgvhgvhgvghvhgvhgvhgvhgvhgvhgvhgvhgvhgvhg");
        testForm.enterLastName("Abcdtgvghvhgvhgvghvhgvhgvhgvghvhgvhgvhgvhgvhgvhgvhgvhgvhgvhg");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsNotDisplayed();
        String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
     }

    @Test
    @DisplayName("Numbers and special characters as a First Name")
    @Description("Проверка на то, что First Name не может состоять из символов и цыфр")
    public void specialCharsInFirstName() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("!@#$%^&**()1234567890");
        testForm.enterLastName("Petrov");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Введённое имя должно быть строкой");
    }

    @Test
    @DisplayName("First Name. 61 chars is not accepted.")
    @Description("Проверка ошибки при вводе 61 символа в поле First Name")
    public void upperBoundaryExceededFirstName() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("Abcdtgvghvhgvhgvghvhgvhgvhgvghvhgvhgvhgvhgvhgvhgvhgvhgvhgvhgv");
        testForm.enterLastName("Petrov");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Введённое имя должно быть короче 60 символов");
    }

    @Test
    @DisplayName("Numbers and special characters as a Last Name")
    @Description("Проверка на то, что Last Name не может состоять из символов и цыфр")
    public void specialCharsInLastName() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("Ivan");
        testForm.enterLastName("!@#$%^&**()1234567890");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("123456789");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Введённая фамилия должна быть строкой");
    }
    @Test
    @DisplayName("Last Name. 61 chars is not accepted.")
    @Description("Проверка ошибки при вводе 61 символа в поле Last Name")
    public void upperBoundaryExceededLastName() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("JanClaude");
        testForm.enterLastName("Abcdtgvghvhgvhgvghvhgvhgvhgvghvhgvhgvhgvhgvhgvhgvhgvhgvhgvhgv");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Введённая фамилия должна быть короче 60 символов");
    }
    ///////////////////////////////////
    /*
    Проверки для поля Email
     */
    ///////////////////////////////////
    @Test
    @DisplayName("Send a form with no Email")
    @Description("Проверка на то, что поле Email должно быть уникальным  при повторной отправке формы. Ошибка при попытке ввести уже зарегистрированный Email.")
    public void emailIsEmpty(){
        testForm = new TestForm(driver);
        testForm.enterFirstName("TestFirst");
        testForm.enterLastName("TestLast");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Поле Email не может быть пустым");
    }

    @Test
    @DisplayName("Resend form with same email")
    @Description("Проверка на то, что поле Email должно быть уникальным  при повторной отправке формы. Ошибка при попытке ввести уже зарегистрированный Email.")
    public void emailNotUniqueSameForm() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("TestFirst");
        testForm.enterLastName("TestLast");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsNotDisplayed();
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Данный E-mail уже зарегистрирован в системе, для регистрации введите другой E-mail");
    }

    @Test
    @DisplayName("Register email that's already in the system")
    @Description("Проверка на уникальность зарегистрированного E-mail. Ошибка при попытке ввести ранее зарегистрированный Email после рефреша страницы.")
    public void emailNotUniquePageRefresh() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("TestFirst");
        testForm.enterLastName("TestLast");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsNotDisplayed();
        testForm.refreshThePage();
        testForm.enterFirstName("TestFirst1");
        testForm.enterLastName("TestLast1");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("123456789");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Данный E-mail уже зарегистрирован в системе, для регистрации введите другой E-mail");
    }

    @Test
    @DisplayName("Catch toastr message. Fails by mistake.")
    @Description("Проверка появления ошибки если поле Email не содержит @. Не могу найти локатор для тостера, поэтому тест падает")
    public void
    checkAlert() {
        testForm = new TestForm(driver);
        testForm.enterFirstName("TestFirst");
        testForm.enterLastName("TestLast");
        testForm.enterEmail("test");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
    }


    /*
    Проверки для поля номер телефона
     */
    @ParameterizedTest
    @DisplayName("Check boundaries for phone")
    @Description("Правильный номер телефона принимается системой (проверка с 8 и 25 символами)")
    @ValueSource(strings = {"12345678", "1234567890123456789012345","(097) - 555 - 17","(123) - 456 - 78901234567"})
    public void correctPhoneTest() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("TestFirst");
        testForm.enterLastName("TestLast");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("12345678");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsNotDisplayed();
    }

    @Test
    @DisplayName("phone too short")
    @Description("Ошибка показывается если номер телефона меньше 8 символов. Проверка на правильность текста ошибки")
    public void shortPhone() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("TestFirst");
        testForm.enterLastName("TestLast");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("1234567");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Номер телефона должен иметь более 7 символов");
        testForm.dismissAlert();
    }

    @Test
    @DisplayName("phone too long")
    @Description("Ошибка показывается если номер телефона больше 25 символов. Проверка на правильность текста ошибки")
    public void longPhone() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("TestFirst");
        testForm.enterLastName("TestLast");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("(123) - 456 - 789012345671");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Номер телефона должен иметь меньше или ровно 25 символов");
        testForm.dismissAlert();
    }

    @ParameterizedTest
    @DisplayName("phone contain symbols")
    @Description("Ошибка показывается если номер телефона содержит символы.")
    @ValueSource(strings = {"a", "a111111111", "aaaaaaaaaaa"})
    public void charactersAsPhone(String phoneChar) {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("TestFirst");
        testForm.enterLastName("TestLast");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber(phoneChar);
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Телефон не должен быть строчного типа");
        testForm.refreshThePage();
    }

    @Test
    @Description("Ошибка показывается если номер телефона больше 25 символов")
    public void phoneTooBig() {
        Random r = new Random();
        int randomInt = r.nextInt();
        testForm = new TestForm(driver);
        testForm.enterFirstName("TestFirst");
        testForm.enterLastName("TestLast");
        testForm.enterEmail(randomInt + "test@testmail.com");
        testForm.enterPhoneNumber("12345678901234567890123451");
        testForm.clickSubmitButton();
        testForm.verifyThatAlertIsDisplayed();
        testForm.verifyThatAlertTextIsCorrect("Телефон должен быть меньше или равен 25 символов");
    }

    @AfterEach
    void refresh() {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.navigate().refresh();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
