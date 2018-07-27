import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.List;

public class openBrowserTest {

    @Test
    public void test(){
       // System.setProperty("webdriver.chrome.driver", "C:\\Users\\pcadmin\\IdeaProjects\\selenium\\src\\main\\chromedriver.exe");
       // WebDriver driver=new ChromeDriver();
        Functions function = new Functions();
        WebDriver driver = function.createDriver();

        driver.get("https://www.wikipedia.org/");
        driver.findElement(By.id("js-link-box-en")).click();

           List<WebElement> links = driver.findElement(By.id("mp-topbanner")).findElements(By.tagName("a"));

           System.out.print("The Div has "+ links.size() + " Links");

           for(WebElement element: links){
              String linkNames= element.getText();
              System.out.println(linkNames);
           }

           driver.close();
           driver.quit();
    }


}
