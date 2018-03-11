package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class DashBoardPage {
  private EventFiringWebDriver driver;
    private By userIcon = By.id("employee_infos");
    private By exitBtn = By.id("header_logout");
    private By catalogTab = By.id("subtab-AdminCatalog");
    private By catalogSubTab = By.id("subtab-AdminCategories");
    private By addCategory = By.id("page-header-desc-category-new_category");
    private By nameNewCategory = By.id("name_1");
    private By saveNewCategory = By.id("category_form_submit_btn");
    private By alertSuccess = By.xpath("//div[@class=\"bootstrap\"][2]/div[@class=\"alert alert-success\"]");
    private By filterList = By.xpath("//input[@name=\"categoryFilter_name\"]");
    private By searchCategoryBtn = By.id("submitFilterButtoncategory");
    private String checkNewCategoryInList = "//table[@id=\"table-category\"]/tbody/tr";
    private By clickButtonGroupActions = By.xpath("//div[@class=\"btn-group bulk-actions dropup\"]");
    private By deleteChechNewCategory = By.xpath("//div[@class=\"btn-group bulk-actions dropup open\"]//ul[@class=\"dropdown-menu\"]/li[7]");
    private By submitDeleteButton = By.xpath("//div[@class=\"panel\"]//button[@class=\"btn btn-default\"]//i[@class=\"icon-trash text-danger\"]");



    private String newCategory = "Категория №999";

    public DashBoardPage(EventFiringWebDriver driver) {
      this.driver = driver;
    }


    //Wait until page is loaded
  public void  waitPage() {
     WebDriverWait wait = new WebDriverWait(driver, 10);
     wait.until(ExpectedConditions.elementToBeClickable(userIcon));
  }

  // Add New Category
  public  void addCategory() {
      waitPage();
      driver.findElement(addCategory).click();
      waitPage();
      driver.findElement(nameNewCategory).sendKeys(newCategory);
      scrollPageDown();
      saveNewCategory();
      waitPage();
      try {
        driver.findElement(alertSuccess);
        System.out.println("Сообщение об успешном добавлении категории появилось на странице управления категориями");
      } catch (NoSuchElementException e){
        System.out.println("Сообщение об успешном добавлении категории НЕ появилось на странице управления категориями");
      }

    }

  public void checkNewCategory() {
      waitPage();
      driver.findElement(filterList).sendKeys(newCategory);
      driver.findElement(searchCategoryBtn).click();
      waitPage();
      List<WebElement> myList = driver.findElements(By.xpath(checkNewCategoryInList));
      if (myList.size() > 0) {
        System.out.println("Новая категория присутствует в списке. В количестве: " + myList.size());
/* /////////////////////////////////////////////////
   Чтобы не засорять создаваемыми пустыми категориями список, он будет почишен.
   ////////////////////////////////////////////////
*/
        for (int i=0; i < myList.size(); i++) {
          WebElement dCat = myList.get(i).findElement(By.xpath(checkNewCategoryInList + "[" + (i+1)+ "]" + "//input[@type=\"checkbox\"]"));
          if (!dCat.isSelected()) {
              dCat.click();
          }
        }

        WebElement clickButtonGroupActionsElement = driver.findElement(clickButtonGroupActions);
        clickButtonGroupActionsElement.click();

        WebElement deleteCheckNewCategoryElement = driver.findElement(deleteChechNewCategory);
        deleteCheckNewCategoryElement.click();

        //работа с плывающим диалоговым окном.
        Alert alert = driver.switchTo().alert();
        alert.accept();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(submitDeleteButton));

        driver.findElement(submitDeleteButton).click();

        System.out.println("Созданные категории удаляются из списка, за ненадобностью!");
      } else {
        System.out.println("Новая категория не добавлена в список категорий. Ищите ошибку в коде!");
      }

    }


    public  void saveNewCategory() {
      waitPage();
      driver.findElement(saveNewCategory).click();
    }

    public void clickOnUserIcon() {
      waitPage();
      driver.findElement(userIcon).click();
    }

    public void clickOnExitBtn() {
      driver.findElement(exitBtn).click();
    }

   public boolean scrollPageDown(){
//    WaitPage();
    JavascriptExecutor executor = (JavascriptExecutor)driver;
    boolean scrollResult = (boolean) executor.executeScript(
            "var scrollBefore = $(window).scrollTop();" +
                    "window.scrollTo(scrollBefore, document.body.scrollHeight);" +
                    "return $(window).scrollTop() > scrollBefore;");
    return scrollResult;
  }

  public void selectCategoryItem(){
/*  This code excellent work in CHROME, but don't work in FireFox 58.0.2(64bit)
    Properties: geckodriver 0.19.1,
                os.name: 'Windows 7',
                os.arch: 'amd64',
                os.version: '6.1',
                java.version: '1.8.0_60'

    Because, JavaScript code in navigation menu work with error.

    waitPage();
    Actions actions = new Actions(driver);
    WebElement catalogTabElement = driver.findElement(catalogTab);
    actions.moveToElement(catalogTabElement).build().perform();
    WebElement catalogSubTabElement = driver.findElement(catalogSubTab);
    actions.moveToElement(catalogTabElement).moveToElement(catalogSubTabElement).click().build().perform();

*/
    waitPage();
    Actions actions = new Actions(driver);
    WebElement orderTabElement = driver.findElement(By.id("subtab-AdminParentOrders"));
    actions.moveToElement(orderTabElement).build().perform();
    waitPage();
    WebElement catalogTabElement = driver.findElement(catalogTab);
    actions.moveToElement(catalogTabElement).perform();
    WebElement catalogSubTabElement = driver.findElement(catalogSubTab);
    catalogSubTabElement.click();

  }

}


