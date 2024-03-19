package score.mobileAutomation.baseTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    public AppiumDriver driver;

    private static final ThreadLocal<AppiumDriver> appiumDriverHolder = new InheritableThreadLocal<AppiumDriver>();

    public  Map<String, String> testParams = new HashMap<>();
    ThreadLocal<URL> url = new ThreadLocal<URL>();
    private final ThreadLocal<AppiumDriverLocalService> service = new ThreadLocal<AppiumDriverLocalService>();

    public Map<String, String>  getTestParams(){
        return testParams;
    }

    public void setTestParams(Map<String, String> testParams){
        this.testParams=testParams;
    }

    /**
     * Clears the thread local cache.
     */
    protected void clearThreadLocals() {
        try {
            appiumDriverHolder.remove();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /*
    This method will return the appium driver on the runtime with Singleton design patter approach.
     */
    protected AppiumDriver getAppiumDriver() {
        if (appiumDriverHolder.get() instanceof AppiumDriver) {
            return appiumDriverHolder.get();
        } else {
            initWebDriver(getTestParams());
            return appiumDriverHolder.get();
        }
    }

    /*
    This method will initiate the new driver and sets in appium driver holder object
     */
    private AppiumDriver initWebDriver(Map<String, String> testNGParams) {
        if (driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {
            try {
                // use parameterized url for multi-threading : String url
                if (testNGParams.get("platformName").equalsIgnoreCase("android")) {
                    driver = new AndroidDriver(new URL(url.get().toString()),
                            getAndroidCapabilities(testNGParams));
                } else {
                    driver = new IOSDriver(new URL(testNGParams.get("url")),
                            getiOSCapabilities(testNGParams));
                }
                appiumDriverHolder.set(driver);
            } catch (SessionNotCreatedException e) {
                e.printStackTrace();
                throw new SessionNotCreatedException("Driver session not created successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return driver;
    }

    /*
    Capabilities in Android are defined here
     */
    private UiAutomator2Options getAndroidCapabilities(Map<String, String> testNGParams) {
        UiAutomator2Options capabilities = new UiAutomator2Options();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, testNGParams.get("automationName"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, testNGParams.get("platformVersion"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, testNGParams.get("platformName"));
        capabilities.setCapability(MobileCapabilityType.APP, testNGParams.get("app"));
        capabilities.setCapability("autoGrantPermissions","true");
        return capabilities;
    }

    /*
     Capabilities in iOS will be defined here - IOS not implemented for now
    */
    private XCUITestOptions getiOSCapabilities(Map<String, String> testNGParams) {
        XCUITestOptions iOSCaps = new XCUITestOptions();
        iOSCaps.setCapability(MobileCapabilityType.AUTOMATION_NAME, testNGParams.get("automationName"));
            /*
            Define all iOS caps
             */
        return iOSCaps;
    }

    //starts appium service programmatically, no need to start service locally
    public void startAppiumService () {
        AppiumDriverLocalService appiumService;
        appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1").usingAnyFreePort().withTimeout(Duration.ofSeconds(200)));
        url.set(appiumService.getUrl());
        service.set(appiumService);
        service.get().start();
    }

    @BeforeSuite
    public void initTest(ITestContext testContext) {
        Map<String, String> temp = testContext.getCurrentXmlTest().getAllParameters();
        setTestParams(temp);
    }

    @BeforeMethod
    public void initSetup(ITestContext test) {
        startAppiumService();
        clearThreadLocals();
    }

    @AfterMethod
    public void cleanup(){
        getAppiumDriver().quit();
    }

}
