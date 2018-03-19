package pages;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utils.BaseEnvTest;
import utils.EventHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class ProductCreationTest extends BaseEnvTest {

  EventFiringWebDriver driver;

  private String officialSite = "http://prestashop-automation.qatestlab.com.ua/";
  private String email = "webinar.test@gmail.com";
  private String password = "Xcg7299bnSmMuRLp9ITw";

  private By catalogTab = By.id("subtab-AdminCatalog");
  private By catalogSubTab = By.id("subtab-AdminProducts");
  private By addProduct = By.id("page-header-desc-configuration-add");
  private By randomName = By.name("form[step1][name][1]");
  private By randomQty = By.id("form_step1_qty_0_shortcut");
  private By random_Price = By.id("form_step1_price_shortcut");


  private  Random rand = new Random();
  private  String randName;
  private  int randQuantity;
  private  double randPrice;
  SoftAssert softAssertion= new SoftAssert();
  private  List<String> products = new ArrayList<String>();


  @BeforeClass
  @Parameters("selenium.browser")
  public void getConfiguredDriver(@Optional("chrome") String browser){
    WebDriver wDriver = getWebDriver(browser);
    wDriver.manage().window().maximize();
    wDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver = new EventFiringWebDriver(wDriver);
    driver.register(new EventHandler());

  }

  @AfterClass
  public void quitDriver() {
    if (driver != null) {
      driver.quit();
    }
  }

  @DataProvider
  public Object[][] getLoginData() {
    return new String[][] {
            {email, password}
    };
  }
  @Test(dataProvider = "getLoginData")
  public  void loginTest(String email, String password) {
    LoginPage loginPage = new LoginPage();
    loginPage.openPage(driver);
    loginPage.fillEmailInput(driver, email);
    loginPage.fillPasswordInput(driver, password);
    loginPage.clickLoginButton(driver);

  }



  @Test(dependsOnMethods = "loginTest")
  public void checkProductCreation() {
    Reporter.log("Open Catalog section <br />");

    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("employee_infos")));
    Actions actions = new Actions(driver);
    WebElement orderTabElement = driver.findElement(By.id("subtab-AdminParentOrders"));
    actions.moveToElement(orderTabElement).build().perform();
    wait.until(ExpectedConditions.elementToBeClickable(By.id("employee_infos")));
    WebElement catalogTabElement = driver.findElement(catalogTab);
    actions.moveToElement(catalogTabElement).perform();
    WebElement catalogSubTabElement = driver.findElement(catalogSubTab);
    catalogSubTabElement.click();

    driver.findElement(addProduct).click();

    WebDriverWait wait2 = new WebDriverWait(driver, 10);
    wait2.until(ExpectedConditions.elementToBeClickable(randomName));

    randName = getProductName(10);
    driver.findElement(randomName).sendKeys(randName);
    System.out.println("putProductName = " + randName);

    randQuantity = randomQuantity();
    String get_randQuantity = Integer.toString(randQuantity);
    driver.findElement(randomQty).sendKeys(Keys.BACK_SPACE);
    driver.findElement(randomQty).sendKeys(get_randQuantity);
    System.out.println("put_randomQuantity = " + get_randQuantity);


    randPrice = randomPrice();
    String get_randomPrice = Double.toString(randPrice);
    driver.findElement(random_Price).sendKeys(Keys.BACK_SPACE);
    driver.findElement(random_Price).sendKeys(Keys.BACK_SPACE);
    driver.findElement(random_Price).sendKeys(Keys.BACK_SPACE);
    driver.findElement(random_Price).sendKeys(Keys.BACK_SPACE);
    driver.findElement(random_Price).sendKeys(Keys.BACK_SPACE);
    driver.findElement(random_Price).sendKeys(Keys.BACK_SPACE);
    driver.findElement(random_Price).sendKeys(Keys.BACK_SPACE);
    driver.findElement(random_Price).sendKeys(Keys.BACK_SPACE);

    char[] charArray = get_randomPrice.toCharArray();
    for (int i=0; i<charArray.length; i++) {
      driver.findElement(random_Price).sendKeys(String.valueOf(charArray[i]));
    }

    System.out.println("put_randomPrice = " + get_randomPrice);

    driver.findElement(random_Price).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector("div.col-lg-5 > div")).click();

    verifyAlert();
    driver.findElement(By.cssSelector("div > button.btn.btn-primary.js-btn-save"));
    verifyAlert();
  }

  @Test(dependsOnMethods = "checkProductCreation")
  public void verifyCreatedProduct() {
    driver.navigate().to(officialSite);
    driver.findElement(By.cssSelector("#content > section > a")).click();
    WebElement forward;
    String color;
    boolean productPresent = false;

    do {
      forward = driver.findElement(By.cssSelector("#js-product-list > nav > div.col-md-6 > ul > li > a.next"));
      color = forward.getCssValue("color");

      WebElement element = driver.findElement(By.xpath("//*[@id=\"js-product-list-top\"]/div[1]/p"));
      String prod = element.getText();
      prod = prod.replaceAll("\\D+","");
      int productsQ = 0;
      try {
        productsQ = Integer.parseInt(prod);
      } catch (NumberFormatException e) {
        Reporter.log("Некорректный числовой формат <br />");
      }
      List<WebElement> elements = driver.findElements(By.xpath("//h1/a"));
      for (int i = 0; i < productsQ; i++) {
        products.add(elements.get(i).getText().toLowerCase());
      }
      if (products.contains(randName)) {
        productPresent = true;
        Reporter.log("Созданный товар найден! <br />");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1/a[.='" + randName + "']")));
        driver.findElement(By.xpath("//h1/a[.='" + randName + "']")).click();
        WebElement nameEl = driver.findElement(By.cssSelector("div.row > div:nth-child(2) > h1"));
        String name = nameEl.getText().toLowerCase();
        softAssertion.assertEquals(name, randName, "Некорректное название товара: ");

        String priceElement = driver.findElement(By.cssSelector("div.product-price.h5 > div > span")).getAttribute("content");

        double price = 0;
        try {
          price = Double.parseDouble(priceElement);
        } catch (NumberFormatException e) {
          Reporter.log("Цена товара указана с ошибкой!");
        }
        softAssertion.assertEquals(price, randPrice, "Указана неверная цена на товар!: ");

        String quantityElement = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[1]/span")).getText();
        quantityElement = quantityElement.replaceAll("\\D+","");
        int quantity = 0;
        try {
          quantity = Integer.parseInt(quantityElement);
        } catch (NumberFormatException e) {
          Reporter.log("Ошибка в формате указания количество товара");
        }
        softAssertion.assertEquals(quantity, randQuantity, "Колличество товара указаное неверно!: ");
        softAssertion.assertAll();

      } else {
        forward.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"js-product-list-top\"]/div[1]/p")));
      }
    } while (color == "rgba(0,0,0,1)" && productPresent == false);

    if (productPresent == false) {
      Reporter.log("Созданный товар НЕ найден! <br />");
    }

  }


  public void  waitPage() {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.titleContains("Dashboard"));
  }


  public String getProductName(int count) {
    String chars = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    StringBuilder builder = new StringBuilder();
    while (count-- != 0) {
      int index = (int) (rand.nextDouble() * chars.length());
      builder.append(chars.charAt(index));
    }
    randName = builder.toString();
    return randName;
  }

  public void verifyAlert() {
    try {
      WebDriverWait wait = new WebDriverWait(driver, 2);
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      String message = alert.getText();
      softAssertion.assertEquals(message, "Настройки обновлены.", "Alert message is incorrect");
      alert.dismiss();
    } catch (Exception e) {
      Reporter.log("Alert not present! <br />");
    }
  }

  public int randomQuantity() {
    int rQy = new RandomDataGenerator().nextInt(1, 100);
    return rQy;
  }

  public double randomPrice() {
    double price = 0.1 + rand.nextDouble() * (100 - 0.1);
    double roundPrice = new BigDecimal(price).setScale(2, RoundingMode.UP).doubleValue();
    return roundPrice;
  }

}
