package utils;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseEnvTest {

  String device = "Nexus 5X";

  enum EnvironmentVariables {
    BASE_ADMIN_URL("env.admin.url"),
    BASE_URL("env.url"),
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
  private static final String DEFAULT_BASE_URL = "http://prestashop-automation.qatestlab.com.ua/";
  private static final String DEFAULT_BROWSER = BrowserType.CHROME;


  public static String getBaseAdminUrl() {
      return System.getProperty(EnvironmentVariables.BASE_ADMIN_URL.toString(), DEFAULT_BASE_ADMIN_URL);
  }

  public static String getBaseUrl() {
    return System.getProperty(EnvironmentVariables.BASE_URL.toString(), DEFAULT_BASE_URL);
  }

  public static String getBrowser() {
    return System.getProperty(EnvironmentVariables.BROWSER.toString(), DEFAULT_BROWSER);
  }

  public static WebDriver getWebDriver(String browser) {
        String propertyDriver;
        DesiredCapabilities capabilities;

        switch (browser) {
          case "firefox":
             System.setProperty(
                     "webdriver.ie.driver",
                     new File(BaseEnvTest.class.getResource("/IEDriverServer.exe").getFile()).getPath());
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
          case "mobile":
            System.setProperty(
                    "webdriver.chrome.driver",
                    new File(BaseEnvTest.class.getResource("/chromedriver.exe").getFile()).getPath());
            Map<String, String> mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", "Nexus 5X");
            Map<String, Object> chromeOptions = new HashMap<String, Object>();
            chromeOptions.put("mobileEmulation", mobileEmulation);
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

               return new ChromeDriver(capabilities);
          case "chrome":
          default:
            System.setProperty(
                    "webdriver.chrome.driver",
                    new File(BaseEnvTest.class.getResource("/chromedriver.exe").getFile()).getPath());
            ChromeOptions opt = new ChromeOptions();
            opt.addArguments("start-maximized");
            return new ChromeDriver(opt);
     }
  }

  public RemoteWebDriver getRemoteDriver(String hubUrl, String browser) throws MalformedURLException {
    DesiredCapabilities capabilities = null;
    if (browser.equals("firefox")) {
      capabilities = DesiredCapabilities.firefox();
    } else if (browser.equals("ie")) {
      capabilities = DesiredCapabilities.internetExplorer();
    } else if (browser.equals("mobile")) {
      Map<String, String> mobileEmulation = new HashMap<String, String>();
      mobileEmulation.put("deviceName", device);
      Map<String, Object> chromeOptions = new HashMap<String, Object>();
      chromeOptions.put("mobileEmulation", mobileEmulation);
      capabilities = DesiredCapabilities.chrome();
      capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    } else {
      ChromeOptions chromeDesctop = new ChromeOptions();
      chromeDesctop.addArguments("start-maximized");
      capabilities = DesiredCapabilities.chrome();
      capabilities.setCapability(ChromeOptions.CAPABILITY, chromeDesctop);
    }
    return new RemoteWebDriver(new URL(hubUrl), capabilities);
  }

}
