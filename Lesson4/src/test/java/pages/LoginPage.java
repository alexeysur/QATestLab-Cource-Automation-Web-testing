package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.BaseEnvTest;



public class LoginPage extends BaseEnvTest {

  private  By emailField = By.id("email");
  private  By passwordField = By.id("passwd");
  private  By loginButton = By.name("submitLogin");


  public  void openPage(EventFiringWebDriver driver) {
    driver.get(getBaseAdminUrl());
  }

  public  void fillEmailInput(EventFiringWebDriver driver, String email) {
    driver.findElement(emailField).sendKeys(email);
  }

  public  void fillPasswordInput(EventFiringWebDriver driver, String password) {
    driver.findElement(passwordField).sendKeys(password);
  }


  public void clickLoginButton(EventFiringWebDriver driver) {
    driver.findElement(loginButton).click();
  }

}
