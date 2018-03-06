/**
 * @author    Alexey Sur
 * @version   0.1
 * @see       Selenium 3.9.1
 * @see       JDK 1.8
 *
 *
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DashBoardPage;
import pages.LoginPage;
import BaseTest.BaseEnvTest;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


public class Main extends BaseEnvTest {
  public static void main(String[] args) throws InterruptedException {

    WebDriver driver;
    LoginPage loginPage;

    String propertyDriver = System.getProperty("user.dir") + "//src//main//resources//chromedriver.exe";
    System.setProperty("webdriver.chrome.driver", propertyDriver);

    driver = new ChromeDriver();
    driver.manage().window().maximize();

    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    //First part task
    loginPage = new LoginPage();
    loginPage.openPage(driver);
    loginPage.fillEmailInput(driver);
    loginPage.fillPasswordInput(driver);
    loginPage.clickLoginButton(driver);

    System.out.println("Login into administration panel");

    DashBoardPage dashBoardPage = new DashBoardPage(driver);
    dashBoardPage.clickOnUserIcon();
    dashBoardPage.clickOnExitBtn();

    quitDriver(driver);
    System.out.println("Exit from administation panel ater successfull task #1");

   //Second part task
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    loginPage = new LoginPage();
    loginPage.openPage(driver);
    loginPage.fillEmailInput(driver);
    loginPage.fillPasswordInput(driver);
    loginPage.clickLoginButton(driver);

    System.out.println("Cycle traversal of list items");

    dashBoardPage.CheckPage();

    dashBoardPage.clickOnUserIcon();
    dashBoardPage.clickOnExitBtn();

    quitDriver(driver);
    System.out.println("Exit from administation panel ater successfull task #2");

  }


}
