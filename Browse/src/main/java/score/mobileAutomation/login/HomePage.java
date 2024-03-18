package score.mobileAutomation.login;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import score.mobileAutomation.basePage.BaseAppPage;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BaseAppPage {


    public HomePage(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    @FindAll({
            @FindBy(id = "com.fivemobile.thescore:id/btn_primary")
    })
    public WebElement getStartedBtn;

    @FindAll({
            @FindBy(id = "com.fivemobile.thescore:id/txt_sign_in")
    })
    public WebElement signInBtn;

    @FindAll({
            @FindBy(id = "com.fivemobile.thescore:id/btn_primary")
    })
    public WebElement continueBtn;

    @FindAll({
            @FindBy(id = "com.fivemobile.thescore:id/action_button_text")
    })
    public WebElement doneBtn;

    @FindAll({
            @FindBy(id = "com.fivemobile.thescore:id/dismiss_modal")
    })
    public WebElement dismissBtn;

    @FindAll({
            @FindBy(id = "com.fivemobile.thescore:id/label")
    })
    private List<WebElement> homePageLabels;

    @FindAll({
            @FindBy(xpath = "//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/team_name']")
    })
    private WebElement teamNameInProfile;

    @FindAll({
            @FindBy(id = "com.fivemobile.thescore:id/txt_away_city")
    })
    private List<WebElement> awayCity;

    @FindAll({
            @FindBy(id = "com.fivemobile.thescore:id/txt_home_city")
    })
    private List<WebElement> homeCity;

    @FindAll({
            @FindBy(xpath = "//android.widget.TextView[@text='TEAM STATS']")
    })
    private WebElement teamStats;

    @FindAll({
            @FindBy(id = "com.fivemobile.thescore:id/view_progress")
    })
    private List<WebElement> viewProgress;

    @FindAll({
            @FindBy(id = "com.fivemobile.thescore:id/text_category_name")
    })
    private List<WebElement> categoryName;

     String favTeamOrLeagueTxt = "//*[@resource-id='com.fivemobile.thescore:id/txt_name' and @text='{0}']";
     String teamLogoBtnTxt="//*[@resource-id='com.fivemobile.thescore:id/label' and @text='{0}']";

     //validates home page via data driven approach. Params coming from data provider
    public boolean homepageValidation(String league, String team) {
        boolean status=true;
        isElementVisible(signInBtn, 10);
        clickIfExists(getStartedBtn);
        clickIfExists(prepareWebElementWithDynamicXpath(favTeamOrLeagueTxt, league));
        clickIfExists(continueBtn);
        clickIfExists(prepareWebElementWithDynamicXpath(favTeamOrLeagueTxt, team));
        clickIfExists(continueBtn);
        clickIfExists(doneBtn);
        clickIfExists(dismissBtn);
        if(!validateHomePageReached())
            status=false;
        return status;
    }

    //Validate team stats tab
    public boolean validateTeamStats(){
        boolean status=true;
        try{
            log.info("Validate team stats inside the team page");
            clickIfExists(teamStats);
            isElementVisible(viewProgress.get(0),10);
            isElementVisible(categoryName.get(0),10);
            if(viewProgress.size() == 0 && categoryName.size() == 0){
               log.error("Progress bar and category names not displayed in stats table");
                status=false;
            }
        }catch (Exception e){
            log.error("something went wrong in validating team stats");
            e.printStackTrace();
            status=false;
        }
        return status;
    }

    //validates navigation to team page
    public boolean navigationToLeagueOrTeam(String team) {
        boolean status=true;
        int countOfTeam=0;
        try{
            clickIfExists(prepareWebElementWithDynamicXpath(teamLogoBtnTxt,fetchTeamCode(team)));
            isElementVisible(teamNameInProfile,10);
            Assert.assertEquals(teamNameInProfile.getText(),team);
            List<String> homeCityList=homeCity.stream().map(WebElement::getText).collect(Collectors.toList());
            List<String> awayCityList=awayCity.stream().map(WebElement::getText).collect(Collectors.toList());
            for(String ele:homeCityList){
                if(!ele.contains(fetchTeamCode(team))){
                    countOfTeam++;
                }
            }
            for(String ele:awayCityList){
                if(!ele.contains(fetchTeamCode(team))){
                    countOfTeam++;
                }
            }
            if(countOfTeam==0){
                log.error("There is no occurrence of "+fetchTeamCode(team)+" in the match list");
                status=false;
            }
        }catch (Exception e){
            log.error("Exception occured on navigating to team page");
            e.printStackTrace();
            status=false;
        }
        return status;
    }

    //validates user has reached the homepage
    public boolean validateHomePageReached(){
        try{
            log.info("Validate team and league icon on the home page");
            List<String> listOfLabels=homePageLabels.stream()
                    .map(ele -> ele.getText())
                    .collect(Collectors.toList());
            return listOfLabels.size()==3?true:false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //this method will be used to use dynamic xpaths in page factory model
    public WebElement prepareWebElementWithDynamicXpath(String xpathValue, String substitutionValue) {
        return appiumDriver.findElement(By.xpath(xpathValue.replace("{0}", substitutionValue)));
    }

    //defines static codes for all the teams, taken only 2 for now.
    public String fetchTeamCode(String team){
        switch(team) {
            case "Montreal Canadiens":
                return "MTL";
            case "Toronto Maple Leafs":
                return "TOR";
        }
        return "";
    }
}
