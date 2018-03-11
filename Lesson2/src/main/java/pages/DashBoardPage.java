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

    List<WebElement> myList = driver.findElements(By.xpath("//li[@class='maintab  has_submenu']"));

    if (myList.size() > 0) {

     for (WebElement menu : myList) {

       String strTitlePage = menu.getText();
       System.out.println("Пункт главного меню: " + strTitlePage);
       menu.click();
       driver.navigate().refresh();

        wait.until(ExpectedConditions.visibilityOfElementLocated(userIcon));
        if (driver.getTitle().contains(strTitlePage)) {
          //Pass
          System.out.println("После обновления страницы загаловок страницы: " + strTitlePage);
        } else {
          //Fail
          System.out.println("После обновления страницы загаловок страницы стал: " + strTitlePage);
        }
        driver.navigate().back();

        }

    } else {
      System.out.println("В главном меню нет пунктов!");
    }
  }

}


