package score.mobileAutomation.basePage;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BaseAppPage <T extends BaseAppPage<T>> {
    protected AppiumDriver appiumDriver;
    protected WebDriverWait wait = null;


    protected final Logger log = LoggerFactory.getLogger(getClass());


    public BaseAppPage(final AppiumDriver appiumDriver){
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver,this);
    }

    protected AppiumDriver getDriver() {
        return this.appiumDriver;
    }

    /**
     * clickIfExists.
     *
     * @param element element
     */
    public void clickIfExists(WebElement element) {
        String stepName = "Click  on element - " + element;
        log.info(stepName);
        try {
            if (element != null) {
                explicitWait(element,10);
                element.click();
            } else {
                log.info("Element Not found hence ignored.");
            }
        } catch (Exception e) {
            log.error("Unable to click element - "+element + "Exception thrown :::"+e.getMessage());
           throw e;
        }
    }

    /**
     * clickIfExists.
     *
     * @param element MobileElement
     */
    public boolean isElementVisible(WebElement element, int timeOutInSeconds) {
        String stepName = "Check visibility of element - " + element;
        log.info(stepName);
        try {
            return wait.withTimeout(Duration.ofSeconds(timeOutInSeconds)).
                    until(ExpectedConditions.visibilityOf(element)) != null;
        } catch (Exception e) {
            log.error("Unable to find element - "+element + "Exception thrown :::"+e.getMessage());
            return false;
        }
    }

    public void explicitWait(WebElement element,int timeout){
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        wait.until(ele -> element.isDisplayed());
    }

}
