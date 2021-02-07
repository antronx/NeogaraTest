package com.test.app;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestForm {

    public static WebDriver driver;

    public TestForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Web elements
    @FindBy(name = "firstname")
    private WebElement firstNameTextBox;

    @FindBy(name = "lastname")
    private WebElement lastNameTextBox;

    @FindBy(name = "email")
    private WebElement emailTextBox;

    @FindBy(name = "phone_number")
    private WebElement phoneNumberTextBox;

    @FindBy(xpath = "//button[normalize-space()=\"Отправить\"]")
    private WebElement submitButton;


    //Steps
    @Step("Enter text in 'first name' text box")
    public void enterFirstName(String name) {
        firstNameTextBox.sendKeys(name);
        Assertions.assertTrue(firstNameTextBox.getAttribute("value").contains(name)); //Verify that text is entered correctly
    }

    @Step("Enter text in 'last name' text box")
    public void enterLastName(String lastName) {
        lastNameTextBox.sendKeys(lastName);
        Assertions.assertTrue(lastNameTextBox.getAttribute("value").contains(lastName)); //Verify that text is entered correctly
    }

    @Step("Enter text in 'email' text box")
    public void enterEmail(String email) {
        emailTextBox.sendKeys(email);
        Assertions.assertTrue(emailTextBox.getAttribute("value").contains(email)); //Verify that text is entered correctly
    }


    @Step("Enter text in 'phone' text box")
    public void enterPhoneNumber(String phone) {
        phoneNumberTextBox.sendKeys(phone);
        Assertions.assertTrue(phoneNumberTextBox.getAttribute("value").contains(phone)); //Verify that text is entered correctly
    }

    @Step("Click Submit button")
    public void clickSubmitButton() {
        submitButton.click();
        // Assertions.assertTrue(firstNameTextBox.getText().contains(phone)); //Verify that text is entered correctly
    }


    @Step("Verify alert presence")
    public boolean isAlertPresent() {
        try {
            Alert a = new WebDriverWait(driver, 2).until(ExpectedConditions.alertIsPresent());
            if (a != null) {
                System.out.println("Alert is present");
                return true;
            } else {
                throw new Throwable();
            }
        } catch (Throwable e) {
            System.err.println("Alert isn't present!!");
            return false;
        }
    }

    @Step("Verify that alert is displayed")
    public void verifyThatAlertIsDisplayed() {
        Assertions.assertTrue(isAlertPresent());
    }

    @Step("Verify that alert is not displayed")
    public void verifyThatAlertIsNotDisplayed() {
        Assertions.assertTrue(isAlertPresent() == false);
    }


    @Step("get alert text")
    public String getAlertText() {
        Alert a = new WebDriverWait(driver, 2).until(ExpectedConditions.alertIsPresent());
        String alertText = a.getText();
        return alertText;
    }

    @Step("Verify that alert text is correct")
    public void verifyThatAlertTextIsCorrect(String text){
        Assertions.assertTrue(getAlertText().contains(text));
    }

    @Step("Dismiss alert")
    public void dismissAlert(){
        driver.switchTo().alert().accept();
    }

    @Step("Refresh the page")
    public void refreshThePage() {
        driver.navigate().refresh();
    }

}
