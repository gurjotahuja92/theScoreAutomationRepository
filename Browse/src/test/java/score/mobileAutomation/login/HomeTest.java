package score.mobileAutomation.login;

import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import score.mobileAutomation.baseTest.DriverFactory;
import score.mobileAutomation.dataProviderUtil.DataProviderUtil;

public class HomeTest extends DriverFactory {

    @Test(dataProvider = "BrowseDataProvider", dataProviderClass = DataProviderUtil.class)
    public void leagueHomepageValidationTest(String league,String team) {
        AppiumDriver driver = getAppiumDriver();
        HomePage homePage=new HomePage(driver);
        Assert.assertTrue(homePage.homepageValidation(league,team),"Home page validation failed");
        Assert.assertTrue(homePage.navigationToLeagueOrTeam(team),"Navigation to team failed");
        Assert.assertTrue(homePage.validateTeamStats(),"Team stats validation failed");
    }
}

