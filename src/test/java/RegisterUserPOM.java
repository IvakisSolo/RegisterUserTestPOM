import factory.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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

    @DataProvider(name = "getUser")
    public Object [][] getUsers(){
        return new Object[][]{
                {"Natalia", "natalia@obv.bg", "12.12.1221", "Password12", "Password12", "Very cool person"}
        };
    }

    @Test(dataProvider = "getUser")
    public void registerUser(String username, String email, String birthdate, String password, String confirmpassword, String publicinfo){
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

        registerPage.fillUserName(username);
        registerPage.fillEmail(email);
        registerPage.fillBirthDate(birthdate);
        registerPage.fillPassword(password);
        registerPage.fillConfirmPassword(confirmpassword);
        registerPage.fillPublicInfo(publicinfo);

        registerPage.clickSignIn();

        header.clickProfile();
        Assert.assertTrue(profilePage.isUrlLoaded(), "Current page is not profile page");
    }
}
