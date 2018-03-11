/**
 * @author    Alexey Sur
 * @version   0.1
 * @see       Selenium 3.9.1
 * @see       JDK 1.8
 *
 *
 */

import org.openqa.selenium.support.events.EventFiringWebDriver;
import pages.DashBoardPage;
import pages.LoginPage;
import utils.BaseEnvTest;


public class Main extends BaseEnvTest {
  public static void main(String[] args) throws InterruptedException {

    EventFiringWebDriver driver = getConfiguredDriver();

    LoginPage loginPage = new LoginPage();
    loginPage.openPage(driver);
    loginPage.fillEmailInput(driver);
    loginPage.fillPasswordInput(driver);
    loginPage.clickLoginButton(driver);

    System.out.println("Login into administration panel");

    DashBoardPage dashBoardPage = new DashBoardPage(driver);
    dashBoardPage.selectCategoryItem();
    dashBoardPage.addCategory();
    dashBoardPage.checkNewCategory();

    System.out.println("Add and check new category foods");
    dashBoardPage.clickOnUserIcon();
    dashBoardPage.clickOnExitBtn();
    quitDriver(driver);
    System.out.println("Exit from administation panel successfull");
  }

}