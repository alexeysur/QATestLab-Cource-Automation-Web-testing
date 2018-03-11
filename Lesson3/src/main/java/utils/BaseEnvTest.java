package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;




public abstract class BaseEnvTest {
  enum EnvironmentVariables {
    BASE_ADMIN_URL("env.admin.url"),
    BROWSER("browser");

    private String value;

    EnvironmentVariables(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  private static final String DEFAULT_BASE_ADMIN_URL = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/";
  private static final String DEFAULT_BROWSER = BrowserType.CHROME;

  public static String getBaseAdminUrl() {
      return System.getProperty(EnvironmentVariables.BASE_ADMIN_URL.toString(), DEFAULT_BASE_ADMIN_URL);
  }

  public static String getBrowser() {
    return System.getProperty(EnvironmentVariables.BROWSER.toString(), DEFAULT_BROWSER);
  }


  private static WebDriver getWebDriver() {
        String propertyDriver;
        String brower = getBrowser();
        switch (brower) {
           case "firefox":
                propertyDriver = System.getProperty("user.dir") + "//src//main//resources//geckodriver.exe";
                System.setProperty("webdriver.gecko.driver", propertyDriver);
                return new FirefoxDriver();
          case "IE":
          case "internet explorer":
          case "iexplore":
                propertyDriver = System.getProperty("user.dir") + "//src//main//resources//IEDriverServer.exe";
                System.setProperty("webdriver.ie.driver", propertyDriver);

            InternetExplorerOptions options = new InternetExplorerOptions();
            options.enableNativeEvents();
            options.requireWindowFocus();
            options.enablePersistentHovering();


                return new InternetExplorerDriver();
          case "chrome":
          default:
                propertyDriver = System.getProperty("user.dir") + "//src//main//resources//chromedriver.exe";
                System.setProperty("webdriver.chrome.driver", propertyDriver);
                return new ChromeDriver();
    }

  }
/*
  public static WebDriver getConfiguredDriver() {
    WebDriver driver = getWebDriver();
    driver.manage().window().maximize();
    return driver;
  }
*/
  public static EventFiringWebDriver getConfiguredDriver(){
    WebDriver driver = getWebDriver();
    driver.manage().window().maximize();


    EventFiringWebDriver wrappedDriver = new EventFiringWebDriver(driver);

    wrappedDriver.register(new EventHandler());

    return wrappedDriver;
  }


  public  static void quitDriver(WebDriver driver) {
    driver.quit();
  }


}
