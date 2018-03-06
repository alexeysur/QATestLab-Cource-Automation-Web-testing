package BaseTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.beans.EventHandler;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public abstract class BaseEnvTest {

  /*
  private static WebDriver getWebDriver() {
        String propertyDriver;
        String brower = Properties.getBrower();
        switch (brower) {
      case "firefox":
        propertyDriver = System.getProperty("user.dir") + "src//main//resources//geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", propertyDriver);
        return FirefoxDriver();
      case "ie":
        propertyDriver = System.getProperty("user.dir") + "src//main//resources//IEDriverServer.exe";
        System.setProperty("webdriver..driver", propertyDriver);
        return new InternetExplorerDriver();
return;
      case "chrome":
        default:
          propertyDriver = System.getProperty("user.dir") + "src//main//resource//chromedriver.exe";
          System.setProperty("webdriver.chrome.driver", propertyDriver);
          return new ChromeDriver;
    }

  }

  public static WebDriver getConfiguredDriver() {
    WebDriver driver = getDriver();
    driver.manager().windows.maximize();
    return driver;
  }

*/

  public  static void quitDriver(WebDriver driver) {
    driver.quit();
  }



}
