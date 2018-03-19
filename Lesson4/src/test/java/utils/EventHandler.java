package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class EventHandler implements WebDriverEventListener {
  @Override
  public void beforeAlertAccept(WebDriver webDriver) {

  }

  @Override
  public void afterAlertAccept(WebDriver webDriver) {

  }

  @Override
  public void afterAlertDismiss(WebDriver webDriver) {

  }

  @Override
  public void beforeAlertDismiss(WebDriver webDriver) {

  }

  @Override
  public void beforeNavigateTo(String s, WebDriver webDriver) {

  }

  @Override
  public void afterNavigateTo(String s, WebDriver webDriver) {
    System.out.println("Open url: " + s);
  }

  @Override
  public void beforeNavigateBack(WebDriver webDriver) {

  }

  @Override
  public void afterNavigateBack(WebDriver webDriver) {

  }

  @Override
  public void beforeNavigateForward(WebDriver webDriver) {

  }

  @Override
  public void afterNavigateForward(WebDriver webDriver) {

  }

  @Override
  public void beforeNavigateRefresh(WebDriver webDriver) {

  }

  @Override
  public void afterNavigateRefresh(WebDriver webDriver) {

  }

  @Override
  public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
    System.out.println("Search for element: " + by.toString());

  }

  @Override
  public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {
    System.out.println("Element found successfully");
  }

  @Override
  public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
    System.out.println("Click on element: " + webElement.getTagName() + " " + webElement.getAttribute("name"));
  }

  @Override
  public void afterClickOn(WebElement webElement, WebDriver webDriver) {
    System.out.println("Element successfully clicked");
  }

  @Override
  public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
    System.out.println("Fill input " + webElement.getAttribute("id") + " with value " + charSequences.toString());
  }

  @Override
  public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
    System.out.println("Value successfully changed");
  }

  @Override
  public void beforeScript(String s, WebDriver webDriver) {
    System.out.println("Begin execute JavaScript code");
  }

  @Override
  public void afterScript(String s, WebDriver webDriver) {
    System.out.println("JavaScript code completed work");
  }

  @Override
  public void onException(Throwable throwable, WebDriver webDriver) {

  }
}
