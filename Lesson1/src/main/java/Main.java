/**
 * @author    Alexey Sur
 * @version   0.1
 * @see       Selenium 3.9.1
 * @see       JDK 1.8
 *
 *
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class Main {

 public static void initWebCromeDriver() {
  String propertyChrome = System.getProperty("user.dir") + "//drivers//chromedriver.exe";
  System.setProperty("webdriver.chrome.driver", propertyChrome);
 }

 public static void initWebIEDriver(){
  String propertyIE=System.getProperty("user.dir")+"//drivers//IEDriverServer.exe";
  System.setProperty("webdriver.ie.driver",propertyIE);
 }

 public static void initWebFirefoxDriver(){
  String propertyFirefox=System.getProperty("user.dir")+"//drivers//geckodriver.exe";
  System.setProperty("webdriver.gecko.driver",propertyFirefox);
 }

  public static void main(String[] args) {

    System.out.println("Welcome to Maven World");

    initWebCromeDriver();
    initWebIEDriver();
    initWebFirefoxDriver();

    WebDriver driverForChrome = new ChromeDriver();
    WebDriver driverForIE = new InternetExplorerDriver();
    WebDriver driverForFirefox = new FirefoxDriver();

    WebDriver.Options managerForChrome = driverForChrome.manage();
    WebDriver.Options managerForIE = driverForIE.manage();
    WebDriver.Options managerForFirefox = driverForFirefox.manage();

    managerForChrome.window().maximize();
    driverForChrome.get("https://google.com");

    managerForIE.window().maximize();
    driverForIE.navigate().to("https://bing.com");

   managerForFirefox.window().maximize();
   driverForFirefox.get("https://google.com");


    driverForChrome.quit();
    driverForIE.quit();
    driverForFirefox.quit();
  }
}
