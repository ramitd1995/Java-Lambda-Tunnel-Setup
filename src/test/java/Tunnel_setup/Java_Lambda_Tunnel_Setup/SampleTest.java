package Tunnel_setup.Java_Lambda_Tunnel_Setup;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
 
//@Listeners({RunTunnelListener.class})
public class SampleTest{
    public String username = "ramitdlambdatest";
    public String accesskey = "Ar7sr82ACbQXRi23ujktWaSuBRq9jOjInvBaelieyC00XavZUP";
    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    String status = "passed";
  
    @BeforeClass
    public void setUp() throws Exception {
       DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version", "75.0");
        capabilities.setCapability("platform", "WIN10"); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("build", "Java Tunnel Setup Build");
        capabilities.setCapability("name", "Sample Test");
        capabilities.setCapability("network", true); // To enable network logs
        capabilities.setCapability("visual", true); // To enable step by step screenshot
        capabilities.setCapability("video", true); // To enable video recording
        capabilities.setCapability("console", true); // To capture console logs
        capabilities.setCapability("tunnel", true);
        
        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
  
    @Test
    public void testSimple() throws Exception {
       try {
    	   
    	   driver.get("http://localhost/attendance/attendance_view.php");
		   Thread.sleep(2000);
		   
		   String title = driver.getTitle();
		   
		   System.out.println("WebPage Title:- " +title);
    	   
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
  
    @AfterClass
    public void tearDown() throws Exception {
       if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}