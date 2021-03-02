package classes;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import  org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReusableMethods
{
    //properties for reusable
	private RemoteWebDriver driver;
	private ExtentReports er;
	private ExtentTest et;
	
	//constructer methods
	public ReusableMethods(String bn)
	{
		if(bn.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}
		else if(bn.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver=new InternetExplorerDriver();
		}
		else if(bn.equalsIgnoreCase("ie"))
		{ 
			//set zoom level at 100%
			WebDriverManager.iedriver().setup();
			driver=new InternetExplorerDriver();
		}
		else if(bn.equalsIgnoreCase("opera"))
		{
			WebDriverManager.operadriver().setup();
			driver=new OperaDriver();
		}
		else if(bn.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
		}
		else
		{
			System.out.println("Wrong browser name");
			System.exit(0); //stopped forcibly
		}
		//create extent report for results in html file
		er=new ExtentReports("target//gmailtestresults.html",false);//append results
		et=er.startTest("Gmail Site Login Testing In Local Host");
	}
	public ReusableMethods(String bn, String version, String os)
	{
		//Give cloud (sause labs) details
		String USERNAME="maheshredy";
		String ACCESSKEY="97e4bfa0-aacf-4cf9-a42f-b1dc3a87f22d";
		String URI="http://"+USERNAME+":"+ACCESSKEY+"@ondemand.saucelabs.com:80/wd/hub";
		//Desired capabilites to give required capabilities to test environment details
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setBrowserName(bn);
		dc.setCapability("version",version);
		try
		{
			if(os.equalsIgnoreCase("windows"))
			{
				dc.setCapability("platform",Platform.VISTA);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				dc.setCapability("platform",Platform.MAC);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				dc.setCapability("platform",Platform.LINUX);
			}
			else 
			{
				System.out.println("Wrong os name");
				System.exit(0); //stopped execution forcibly
			}
			URL u=new URL(URI);
			driver=new RemoteWebDriver(u,dc);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			System.exit(0); //stopped execution forcibly
		}
		
	     //create object for extent report for results in html file
		 er=new ExtentReports("target//gmailtestresults.html",false);//append results
		 et=er.startTest("Gmail Site Login Testing In cloud");
		}
	    //operational methods
	   public void LaunchSite(String url) throws Exception
	   {
		   Thread.sleep(5000);
		   driver.get(url);
		   driver.manage().window().maximize();
		   Thread.sleep(5000);
	   }
	   public void fillandvalidatelogin(String uid, String uc, String p, String pc) throws Exception
	   {
		   driver.findElement(By.name("identifier")).sendKeys(uid);
		   driver.findElement(By.xpath("//span[text()='Next']/parent::button")).click();
		   Thread.sleep(5000);
	       try
	       {
	    	   if(uc.equalsIgnoreCase("blank") && driver.findElement(By.xpath("(//*[contains(text(),'Enter an email')])[2]")).isDisplayed())
	    	   {
	    		   et.log(LogStatus.PASS,"Blank userid test passed");
	    	   }
	    	   else if (uc.equalsIgnoreCase("invalid") && driver.findElement(By.xpath("(//*[contains(text(),'find your Google Account')])[2]")).isDisplayed())
	    	   {
	    		   et.log(LogStatus.PASS,"invalid userid test passed");
	    	   }
	    	   else if (uc.equalsIgnoreCase("valid") && driver.findElement(By.name("password")).isDisplayed())
	    	   {
	    		   //password testing
	    		   driver.findElement(By.name("password")).sendKeys(p);
	    		   driver.findElement(By.xpath("//span[text()='Next']/parent::button")).click();
	    		   Thread.sleep(5000);
	    		   if (pc.equalsIgnoreCase("blank") && driver.findElement(By.xpath("//*[text()='Enter a password']")).isDisplayed())
	    		   {
	    			   et.log(LogStatus.PASS,"Blank password test passed");
	    		   }
	    		   else if(pc.equalsIgnoreCase("invalid") && driver.findElement(By.xpath("//*[contains(text(),'Wrong password') or " + "contains(text(),'Your password was changed')]")).isDisplayed())
	    		   {
	    			   et.log(LogStatus.PASS,"invalid password test passed");
	    		   }
	    		   else if(uc.equalsIgnoreCase("valid") && driver.findElement(By.xpath("//*[text()='Compose']")).isDisplayed())
                  {
                	  et.log(LogStatus.PASS,"login test passed for valid credintials");
                  }
                  else //password testing else
                  {
                	  //take screen shot
                	  et.log(LogStatus.FAIL,"password test failed"+et.addScreenCapture(screenshot()));
                  }
                	  
	    	   }
	    	   else //userid testing else
	    	   {
	    		   //take screen shot
             	  et.log(LogStatus.FAIL,"userid test failed and try to watch"+et.addScreenCapture(screenshot()));
	    	   }
	       }
	       catch(Exception ex) 
	       {
	    	   //take screen shot
          	  et.log(LogStatus.FAIL,ex.getMessage()+et.addScreenCapture(screenshot()));
	       }
		
		}
	   public void closeSite()
	   {
		   driver.close();
		   er.flush(); //save results
		   er.endTest(et);
	   }
	   private String screenshot() throws Exception
	   {
       SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
       Date dt=new Date();
       String fn=sf.format(dt)+".png";
       File src=driver.getScreenshotAs(OutputType.FILE);
       File dest=new File(fn);
       FileHandler.copy(src,dest);
       return(dest.getAbsolutePath());
       
	}
}
