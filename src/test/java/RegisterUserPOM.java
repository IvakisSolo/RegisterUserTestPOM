import factory.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class RegisterUserPOM {
    ChromeDriver webDriver;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(){
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod(alwaysRun = true)
        public void afterTest(){
        webDriver.close();
    }

    @Test
    public void registerUser(){
        Header header = new Header(webDriver);
        HomePage homePage = new HomePage(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        ProfilePage profilePage = new ProfilePage(webDriver);
        RegisterPage registerPage = new RegisterPage(webDriver);

        homePage.navigateTo();
        Assert.assertTrue(homePage.isUrlLoaded(), "Home page is not loaded");

        header.clickLogin();
        Assert.assertTrue(loginPage.isUrlLoaded(), "Current page is not Login");

        loginPage.clickRegister();
        Assert.assertTrue(registerPage.isUrlLoaded(), "Current page is not register");

        registerPage.fillUserName(DataProvider.generateRandomUsername());
        registerPage.fillEmail(DataProvider.generateRandomEmail());
        registerPage.fillBirthDate(DataProvider.generateRandomDOB());
        registerPage.fillPassword(DataProvider.generateRandomPassword());
        registerPage.fillConfirmPassword(DataProvider.generateRandomConfirmPassword());
        registerPage.fillPublicInfo(DataProvider.generateRandomInfo());

        registerPage.clickSignIn();

        header.clickProfile();
        Assert.assertTrue(profilePage.isUrlLoaded(), "Current page is not profile page");
    }
}
