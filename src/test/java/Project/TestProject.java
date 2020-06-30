package Project;

import PageView.HomePage;
import PageView.LoginPage;
import Support.SetupServer;
import org.testng.annotations.*;

public class TestProject extends SetupServer {
    LoginPage signin;
    HomePage home;
    String email = "admin@test.com";
    String pass = "test123";

    @BeforeClass
    public void setup() {
        SetUp("safari", "http://ktvn-test.s3-website.us-east-1.amazonaws.com/");
        signin = new LoginPage(this);
        home = new HomePage(this);
    }

    @Test
    public void RunningAllTests() {
        signin.Login(email, pass);
        home.Verify_filter_Student_Access_Request_with_INACTIVE();
        home.Verify_sorting_of_First_Name_column();
    }

    @AfterClass
    public void cleanUp() {
        driver.close();
    }

}
