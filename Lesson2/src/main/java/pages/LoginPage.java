package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

  private  static String ADMIN_PANEL_URL = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/";
  String email = "webinar.test@gmail.com";
  String password = "Xcg7299bnSmMuRLp9ITw";

  By emailField = By.id("email");
  By passwordField = By.id("passwd");
  By loginButton = By.name("submitLogin");

  public static void openPage(WebDriver driver) {
    driver.navigate().to(ADMIN_PANEL_URL);
  }

  public void fillEmailInput(WebDriver driver) {
    driver.findElement(emailField).sendKeys(email);
  }

  public void fillPasswordInput(WebDriver driver) {
    driver.findElement(passwordField).sendKeys(password);
  }

  public void clickLoginButton(WebDriver driver) {
    driver.findElement(loginButton).click();
  }

}
