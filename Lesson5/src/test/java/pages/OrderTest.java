package pages;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utils.BaseEnvTest;
import utils.CustomReporter;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import model.ProductData;
import utils.EventHandler;


public class OrderTest extends BaseEnvTest {

  EventFiringWebDriver driver;
  WebDriverWait wait;

  int randomNumber;
  int productsQ;

  String nameP = "";
  float priceP = 0;
  int previousQuantity;
  ProductData basketProduct;

  SoftAssert asser = new SoftAssert();
  Random rand = new Random();
  String randName;
  String domain = "gmail.com";
  List<String> products = new ArrayList<String>();
  List<WebElement> elements;
  int id;

  String str_price = "div.product-price.h5 > div > span";
  String str_contentSection = "#content > section > a";
  String str_countProductInPage = "//div[@class=\"col-md-4\"]";
  String str_nowProductQuantity = "//h1/a";
  String str_Quantity = "//div[@class=\"product-quantities\"]";
  String str_AddButton = "div.add > button";
  String str_modalButton = "div.modal-body > div > div:nth-child(2) > div > a";
  String str_qty = "//*[@id=\"cart-subtotal-products\"]/span[1]";
  String str_basketName = "div.product-line-info";

  // form with personal data
  String str_form = "div.text-xs-center > a";
  String str_form_firstname = "firstname";
  String str_form_lastname = "lastname";
  String str_form_email = "email";
  String str_form_continue = "continue";
  String str_form_address1 = "address1";
  String str_form_postcode = "postcode";
  String str_form_city = "city";
  String str_form_cofirm_addresses  = "confirm-addresses";
  String str_form_confirmDeliveryOption = "confirmDeliveryOption";
  String str_form_payment_optin_2 = "payment-option-2";
  String str_form_conditions_to_approve_terms_and_conditions = "conditions_to_approve[terms-and-conditions]";
  String str_description_product = "li:nth-child(2) > a.nav-link";
  String str_lastName= "div.col-sm-4.col-xs-9.details > span";
  String str_payment_button = "#payment-confirmation > div.ps-shown-by-js > button";
  String str_message = "h3.h1.card-title";
  String str_lastQ = "div > div.col-xs-2";
  String str_lastP = "//div[@class=\"col-xs-5 text-sm-right text-xs-left\"]";
  String str_find_product = "//h1/a[.='";
  String str_right_bracket = "']";
  //String str_finalQString = "//div[@class=\"product-quantities\"]";
  String str_content_hook_order = "#content-hook-order-confirmation-footer > section > a";
  String str_div_product = "div.product-quantities > span";
  String str_div_product_quantities = "//div[@class=\"product-quantities\"]";
  String str_div_product_description_a = "//ul[@class=\"nav nav-tabs\"]/li[@class=\"nav-item\"][2]/a";

  @BeforeClass
  @Parameters({"selenium.hub", "selenium.browser"})
  public void getConfiguredDriver(@Optional("http://localhost:4444/wd/hub")  String hubURL, @Optional("chrome") String browser) throws MalformedURLException {
   // hubURL = "http://localhost:4444/wd/hub";
    WebDriver wDriver  = hubURL.isEmpty() ? getWebDriver(browser) : getRemoteDriver(hubURL, browser);

    wDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    wDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

    driver = new EventFiringWebDriver(wDriver);
    driver.register(new EventHandler());

    wait = new WebDriverWait(driver, 5);
  }

  @AfterClass
  public void quitDriver() {
    if (driver != null) {
      driver.quit();
    }
  }


  @Test
  @Parameters("selenium.browser")
  public void checkVersionSite(@Optional("chrome") String browser) {
    CustomReporter.logAction("Check version test starts");

    driver.navigate().to(BaseEnvTest.getBaseUrl());
    if (browser.equals("mobile") && driver.findElement(By.id("menu-icon")).isDisplayed()) {
      CustomReporter.log("PASSED: Mobile version is opened correctly");
    } else if ((browser.equals("chrome") || (browser.equals("firefox")) || (browser.equals("ie"))) && !(driver.findElement(By.id("menu-icon")).isDisplayed())) {
      CustomReporter.log("PASSED: Desktop version opened correctly");
    } else if (browser.equals("mobile") && !(driver.findElement(By.id("menu-icon")).isDisplayed())) {
      CustomReporter.log("FAILED: Mobile version opened incorrectly");
    } else if (browser.equals("chrome") && driver.findElement(By.id("menu-icon")).isDisplayed()) {
      CustomReporter.log("FAILED: Desktop version opened incorrectly");
    }
  }

  @Test(dependsOnMethods = "checkVersionSite")
  public void makeOrder() {
    CustomReporter.logAction("checkOrder test starts");
    CustomReporter.logAction("Click All products button");

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(str_contentSection)));
    driver.findElement(By.cssSelector(str_contentSection)).click();

    WebElement element = driver.findElement(By.xpath(str_countProductInPage));

    String prod = element.getText();
    prod = prod.substring(prod.lastIndexOf(" ") + 1);
    prod = prod.replaceAll("\\D+", "");

    try {
      productsQ = Integer.parseInt(prod);
    } catch (NumberFormatException e) {
      CustomReporter.log("Number Format Exception <br />");
    }
    int nowProductQuantity = 0;
    for (int i = 0; i < productsQ; i++) {
      if (i< 7) {
        nowProductQuantity++;
        elements = driver.findElements(By.xpath(str_nowProductQuantity));
        products.add(elements.get(i).getText());
      }
    }

    CustomReporter.logAction("Click random product");
    id = randomQuantity(nowProductQuantity);
//    scrollUntilVisible(elements.get(id));
    elements.get(id).click();

    CustomReporter.logAction("Click info about product");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(str_description_product)));
    driver.findElement(By.cssSelector(str_description_product)).click();

    //Информация по товару на странице детального описания товара
    //Количество

    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(str_Quantity)));
    driver.findElement(By.xpath(str_Quantity)).click();

    String previousQString = driver.findElement(By.xpath(str_Quantity)).getText();
    previousQString = previousQString.replaceAll("\\D+", "");

    try {
      previousQuantity = Integer.parseInt(previousQString);
      System.out.println("previousQuantity = "+ previousQuantity);
    } catch (NumberFormatException e) {
      CustomReporter.log("Ошибка в формате указания количество товара");
    }

    //Цена
    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(str_price)));
    String priceElement = driver.findElement(By.cssSelector(str_price)).getText();
    priceElement = priceElement.replaceAll("[^,0-9]","");
    priceElement = priceElement.replaceAll("[,]",".");

    try {
      priceP = Float.parseFloat(priceElement);
    } catch (NumberFormatException e) {
      CustomReporter.log("Цена товара указана с ошибкой!");
    }

    //Наименование
    nameP = driver.getTitle();
    CustomReporter.logAction("Добавление товара в корзину");

    basketProduct = new ProductData(nameP, 1, priceP);

    driver.findElement(By.cssSelector(str_AddButton)).click();
    By modalButton = By.cssSelector(str_modalButton);
    wait.until(ExpectedConditions.visibilityOfElementLocated(modalButton));
    CustomReporter.logAction("Click Make Order button");
    driver.findElement(modalButton).click();
  }




  @Test(dependsOnMethods = "makeOrder")
  public void checkOrder() {
    CustomReporter.logAction("Compare added product with the basket product");

    //Количество товара в корзине
    String qty = driver.findElement(By.xpath(str_qty)).getText();
    qty = qty.replaceAll("\\D+","");

    int quant = 0;
    try {
      quant = Integer.parseInt(qty);
    } catch (NumberFormatException e) {
      CustomReporter.log("Number Format Exception <br />");
    }

    asser.assertEquals(quant, basketProduct.getQty(), "Неверное количество товара в корзине, должно быть = 1: ");
    //Название товара в корзине

    String basketName = driver.findElement(By.cssSelector(str_basketName)).getText();
    System.out.println("basketProduct Name = "+ basketProduct.getName());
    asser.assertEquals(basketName, basketProduct.getName(), "Неверное название товара в корзине: ");

    //Цена товара в корзине
    String actualPrice = driver.findElement(By.cssSelector("strong")).getText();
    actualPrice = actualPrice.replaceAll("[^,0-9]","");
    actualPrice = actualPrice.replaceAll("[,]",".");
    float basketPrice = 0;
    try {
      basketPrice = Float.parseFloat(actualPrice);
    } catch (NumberFormatException e) {
      CustomReporter.log("Price Format Exception");
    }

    asser.assertEquals(basketPrice, basketProduct.getPrice(), "Неверно указана цена для товара в корзине: ");

    CustomReporter.logAction("Заполняем персональную информацию для заказа");

    driver.findElement(By.cssSelector(str_form)).click();
    driver.findElement(By.name(str_form_firstname)).sendKeys(getName(randomNumber()));
    driver.findElement(By.name(str_form_lastname)).sendKeys(getName(randomNumber()));
    driver.findElement(By.name(str_form_email)).sendKeys(getEmail(randomNumber(), domain));
    driver.findElement(By.name(str_form_continue)).click();
    wait.until(ExpectedConditions.elementToBeClickable(By.name(str_form_address1)));
    driver.findElement(By.name(str_form_address1)).sendKeys(getName(randomNumber()));
    driver.findElement(By.name(str_form_postcode)).sendKeys(index());
    driver.findElement(By.name(str_form_city)).sendKeys(getName(randomNumber()));
    driver.findElement(By.name(str_form_cofirm_addresses)).click();
    driver.findElement(By.name(str_form_confirmDeliveryOption)).click();
    driver.findElement(By.id(str_form_payment_optin_2)).click();
    driver.findElement(By.name(str_form_conditions_to_approve_terms_and_conditions)).click();

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(str_payment_button)));

    driver.findElement(By.cssSelector(str_payment_button)).click();
    String expectedMessage = "\uE876ВАШ ЗАКАЗ ПОДТВЕРЖДЁН";

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(str_message)));

    String message = driver.findElement(By.cssSelector(str_message)).getText();
    asser.assertEquals(message, expectedMessage, "Некорректное подтверждающие сообщение о принятии заказа: ");

    String lastQ = driver.findElement(By.cssSelector(str_lastQ)).getText();
    asser.assertEquals(Integer.parseInt(lastQ), basketProduct.getQty(), "Неправильно указано количество заказанного товара: ");

    String lastP = driver.findElement(By.xpath(str_lastP)).getText();
    lastP = lastP.replaceAll("[^,0-9]","");
    lastP = lastP.replaceAll("[,]",".");
    float lastPrice = 0;
    try {
      lastPrice = Float.parseFloat(lastP);
    } catch (NumberFormatException e) {
      CustomReporter.log("Price Format Exception");
    }
    asser.assertEquals(lastPrice, basketProduct.getPrice(), "Цена в описании товара и в корзине разная: ");

    String lastName = driver.findElement(By.cssSelector(str_lastName)).getText();
    if(lastName.contains(basketProduct.getName())) {
      CustomReporter.log("Назввние товара в детельном описании корректно");
    } else {
      CustomReporter.log("Назввние товара в детельном описании НЕ корректно");
    }
    CustomReporter.logAction("Click Save button");
    driver.findElement(By.cssSelector(str_content_hook_order)).click();

    nameP = WordUtils.capitalizeFully(basketProduct.getName());

    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(str_find_product + nameP + str_right_bracket)));

    driver.findElement(By.xpath(str_find_product + nameP + str_right_bracket)).click();

    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(str_div_product_description_a)));
    driver.findElement(By.xpath(str_div_product_description_a)).click();

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(str_div_product)));
    driver.findElement(By.xpath(str_div_product_quantities)).click();

    String finalQString = driver.findElement(By.xpath(str_div_product_quantities)).getText();
    finalQString = finalQString.replaceAll("\\D+","");
    int finalQuantity = 0;
    try {
      finalQuantity = Integer.parseInt(finalQString);
      System.out.println("finalQuantity = " + finalQuantity);
    } catch (NumberFormatException e) {
      CustomReporter.log("Final Quantity Format Exception");
    }
    int prevQuantity = previousQuantity -1;
    asser.assertEquals(finalQuantity, prevQuantity, "Неверное количество товара в магазине: ");
    asser.assertAll();

  }

  public String index() {
    int ind = 10000 + rand.nextInt(90000);
    String index = Integer.toString(ind);
    return index;
  }


  public int randomNumber() {
    randomNumber = new RandomDataGenerator().nextInt(1, productsQ - 1);
    return randomNumber;
  }

  public String getName(int count) {
    String chars = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    StringBuilder builder = new StringBuilder();
    while (count-- != 0) {
      int index = (int) (rand.nextDouble() * chars.length());
      builder.append(chars.charAt(index));
    }
    String name = builder.toString();
    return name;
  }

  public String getEmail(int count, String domain) {
    String chars = "abcdefghijklmnopqrstuvwxyz" + count;
    StringBuilder builder = new StringBuilder();
    while (count-- != 0) {
      int index = (int) (rand.nextDouble() * chars.length());
      builder.append(chars.charAt(index));
    }
    randName = builder.toString();
    randName = randName + "@" + domain;
    return randName;
  }


  public void scrollUntilVisible(WebElement element){
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
  }

  public int randomQuantity(int prdductQ) {
    int rQy = new RandomDataGenerator().nextInt(1, prdductQ);
    return rQy;
  }




}
