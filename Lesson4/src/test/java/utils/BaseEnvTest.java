package utils;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;


public abstract class BaseEnvTest {
  enum EnvironmentVariables {
    BASE_ADMIN_URL("env.admin.url");

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


  public static String getBaseAdminUrl() {
      return System.getProperty(EnvironmentVariables.BASE_ADMIN_URL.toString(), DEFAULT_BASE_ADMIN_URL);
  }


  public static WebDriver getWebDriver(String brower) {
        String propertyDriver;

        switch (brower) {
           case "firefox":
             propertyDriver = System.getProperty("user.dir") + "//src//test//resources//geckodriver.exe";
             System.setProperty("webdriver.gecko.driver", propertyDriver);
             return new FirefoxDriver();
          case "ie":
          case "internet explorer":
          case "iexplore":
            propertyDriver = System.getProperty("user.dir") + "//src//test//resources//IEDriverServer.exe";
            System.setProperty("webdriver.ie.driver", propertyDriver);

            InternetExplorerOptions options = new InternetExplorerOptions().requireWindowFocus()
                    .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT).
                    enablePersistentHovering().
                    destructivelyEnsureCleanSession();
            options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

                return new InternetExplorerDriver(options);
          case "chrome":
          default:
            propertyDriver = System.getProperty("user.dir") + "//src//test//resources//chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", propertyDriver);
            return new ChromeDriver();
    }

  }


}
