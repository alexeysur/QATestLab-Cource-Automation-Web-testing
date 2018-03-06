package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.sleep;

public class DashBoardPage {
    private WebDriver driver;
    private By userIcon = By.id("employee_infos");
    private By exitBtn = By.id("header_logout");
    private By dashBoard = By.id("dashboard");

    private By orders= By.id("subtab-AdminParentOrders");
    private By catalog = By.id("subtab-AdminParentCustomer");
    private By customers = By.id("subtab-AdminParentCustomer");
    private By services_support = By.id("subtab-AdminParentCustomerThreads");
    private By statistic = By.id("subtab-AdminStats");
    private By modules = By.id("subtab-AdminParentModulesSf");
    private By design = By.id("subtab-AdminParentThemes");
    private By delivery = By.id("subtab-AdminParentShipping");
    private By type_pay = By.id("subtab-AdminParentPayment");
    private By International = By.id("subtab-AdminInternational");
    private By shop_parameters = By.id("subtab-ShopParameters");
    private By configuration  = By.id("subtab-AdminAdvancedParameters");

    public DashBoardPage(WebDriver driver) {
      this.driver = driver;
    }

    public void clickOnUserIcon() {
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(ExpectedConditions.visibilityOfElementLocated(userIcon));
      driver.findElement(userIcon).click();
    }

    public void clickOnExitBtn() {
      driver.findElement(exitBtn).click();
    }

  public void CheckPage() {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(userIcon));

    List<WebElement> myList = driver.findElements(By.className("menu"));

    if (myList.size() > 0) {
      String strTitlePage = myList.get(1).getText();
      System.out.println(strTitlePage);
      String delimeter = "\n";
      String[] subStr = strTitlePage.split(delimeter);

      for (int i = 0; i < myList.size(); i++) {

        myList.get(i).click();
        driver.navigate().refresh();

        wait.until(ExpectedConditions.visibilityOfElementLocated(userIcon));
        System.out.println(driver.getTitle());

        if (driver.getTitle().contains(subStr[i])) {
          //Pass
          System.out.println("Page title contains" + subStr[i]);
        } else {
          //Fail
          System.out.println("Page title doesn't contains" + subStr[i]);
        }
        driver.navigate().back();
      }

    } else {
      System.out.println("Dashboard doesn't contains any items!");
    }
  }

}


