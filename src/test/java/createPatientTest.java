import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class createPatientTest {

    //initilizing the functions object
    Functions function = new Functions();

    //creating driver
    WebDriver driver= function.createDriver();

    // This test case will create a new patient record in EMR system and verify if its saved successfully.
   @Test
    public void createPatient(){


    //initializing the objects
    Objects data = new Objects();

    //initializing test data object
        TestData testdata=new TestData();



    //navigating to URL
        driver.get(data.EMR_URL);

        // Wait for element present
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //log into the system
        function.loginToEMR(driver,testdata.username,testdata.password);

        //Wait for element
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(data.NIC_name)));

        //reading patient data from xml and stoing it in a arraylist
       ArrayList patientdata=new ArrayList();
       patientdata=function.getPatientData(testdata.patientDataFilePath,"patient","NIC","33000000v");

       //Assigning the values to variables
       String nic = (String) String.valueOf(patientdata.get(0));
       String remarkIfMinor = String.valueOf(patientdata.get(1));
       String firstName= (String) String.valueOf(patientdata.get(2));
       String lastName = String.valueOf(patientdata.get(3));
       String dob = String.valueOf(patientdata.get(4));
       String occupation = String.valueOf(patientdata.get(5));
       String email= String.valueOf(patientdata.get(6));
       String address = String.valueOf(patientdata.get(7));
       String landline = String.valueOf(patientdata.get(8));
       String mobile = String.valueOf(patientdata.get(9));
       String primaryConsultant = String.valueOf(patientdata.get(10));
       String secondaryConsultant = String.valueOf(patientdata.get(11));
       String confidentialNotes = String.valueOf(patientdata.get(12));
       String status = String.valueOf(patientdata.get(13));

       //filling the form
       function.fillPatientData(driver,nic,remarkIfMinor,firstName,lastName,dob,occupation,email,address,landline,mobile,primaryConsultant,secondaryConsultant,confidentialNotes,status);

        //clicking on search button
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(data.searchBtn_id)));
       driver.findElement(By.id(data.searchBtn_id)).click();

       // verify if the search popup is present
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(data.searchResultPopup_xpath)));
       int outcome= function.validateRecordExist(driver,nic);
      // Assert.assertTrue(outcome==1);
       System.out.println("The outcome is "+ outcome);
      Assert.assertEquals(outcome,1);

   }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}


