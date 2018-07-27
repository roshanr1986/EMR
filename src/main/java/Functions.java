import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Functions {

    public static WebDriver createDriver(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\pcadmin\\IdeaProjects\\selenium\\src\\main\\chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        return driver;
    }

    public static int validateRecordExist( WebDriver driver,String nic){


        Objects data = new Objects();
        //loop through the table and search for specific record.

        //defining table

        WebElement table = driver.findElement(By.xpath(data.searchResultTable_xpath));

        //storing the list of table rows in the defined table in a List of webelements
        List<WebElement> TotalRowCount=table.findElements(By.xpath("//*[@id=\'result\']/table/tbody/tr"));


        // printing the number of table row count
        System.out.println("No. of Rows in the Table: "+TotalRowCount.size());

        //Iterating through the table rows
        int RowIndex=1;
        int searchOutcome=0;
        ArrayList<String> colData = new ArrayList<String>();

        for(WebElement rowElement: TotalRowCount){

            //count the number of columns in the table
            List<WebElement> TotalColumnCount=rowElement.findElements(By.xpath("td"));
            int ColumnIndex=1;

            //loop within the column
            for(WebElement colElement:TotalColumnCount){

                //colData.add(ColumnIndex,colElement.getText());
                System.out.println("Row "+RowIndex+" Column "+ColumnIndex+" Data "+colElement.getText());
                //colData.add(ColumnIndex,colElement.getText());

                //System.out.println("Column value is ==================  "+colData.get(0)+ " ========================");
                if(colElement.getText().equalsIgnoreCase(nic)) {
                   // System.out.println("======= Found Matching result ====  Exp - "+nic+ " ==== Actual - "+colElement.getText());


                    searchOutcome=1;
                    break;
                }
                else{
                  //  System.out.println("==== No matching results === Exp - "+nic+ " ==== Actual - "+colElement.getText());
                }


                ColumnIndex=ColumnIndex+1;

            }




            RowIndex=RowIndex+1;
        }

        return searchOutcome;
    }

    public static void fillPatientData(
            WebDriver driver,
            String nic,
            String remarkIfMinor,
            String firstName,
            String lastName,
            String dob,
            String occupation,
            String email,
            String address,
            String landline,
            String mobile,
            String primaryConsultant,
            String secondaryConsultant,
            String confidentialNotes,
            String status
            ){
        //initializing object from Objects class(web elements)
        Objects data = new Objects();

        // Initializing wait object
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElement(By.name(data.NIC_name)).sendKeys(nic);
        driver.findElement(By.name(data.remarkIfMinor_name)).sendKeys(remarkIfMinor);
        driver.findElement(By.name(data.firstName_name)).sendKeys(firstName);
        driver.findElement(By.name(data.lastName_name)).sendKeys(lastName);
        driver.findElement(By.name(data.dob_name)).sendKeys(dob);
        driver.findElement(By.name(data.occupation_name)).sendKeys(occupation);
        driver.findElement(By.name(data.email_name)).sendKeys(email);
        driver.findElement(By.name(data.address_name)).sendKeys(address);
        driver.findElement(By.name(data.landiline_name)).sendKeys(landline);
        driver.findElement(By.name(data.mobile_name)).sendKeys(mobile);
        driver.findElement(By.name(data.primaryCon_name)).sendKeys(primaryConsultant);
        driver.findElement(By.name(data.secondaryCon_name)).sendKeys(secondaryConsultant);

        driver.findElement(By.xpath(data.showConfidentialNotesBtn_xpath)).click();
        driver.findElement(By.name(data.confidentialNotes_name)).sendKeys(confidentialNotes);

        driver.findElement(By.xpath(data.showStatusBtn_xpath)).click();
        driver.findElement(By.name(data.statusNotes_name)).sendKeys(status);

        //click on save
        driver.findElement(By.name(data.saveBtn_name)).click();

        try {
            //wait for popup to be present
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            // accepting alert
            alert.accept();
            System.out.println("Accepted the patient save alert successfully.");

        }
        catch (Throwable e){
            System.err.println("Error came while waiting for the alert popup. "+e.getMessage());
        }
    }

    public static ArrayList getPatientData(String filepath, String nodename, String tagname, String tagvalue){

        ArrayList patientdata = null;
        try {
            File xmlfile = new File(filepath);
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();
            Document doc = dbuilder.parse(xmlfile);

            doc.getDocumentElement().normalize();
            NodeList nlist = doc.getElementsByTagName(nodename);

            patientdata = new ArrayList();

            for (int i = 0; i < nlist.getLength(); i++) {
                Node node = nlist.item(i);

                //checking the node type and if its a element, then print the elements;

                if (node.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eEliment = (Element) node;

                    if (eEliment.getNodeName().contains(nodename) & eEliment.getAttribute(tagname).equalsIgnoreCase(tagvalue)) {


                        patientdata.add(0,eEliment.getElementsByTagName("nic").item(0).getTextContent());
                        patientdata.add(1,eEliment.getElementsByTagName("remarkIfMinor").item(0).getTextContent());
                        patientdata.add(2,eEliment.getElementsByTagName("firstName").item(0).getTextContent());
                        patientdata.add(3,eEliment.getElementsByTagName("lastName").item(0).getTextContent());
                        patientdata.add(4,eEliment.getElementsByTagName("dob").item(0).getTextContent());
                        patientdata.add(5,eEliment.getElementsByTagName("occupation").item(0).getTextContent());
                        patientdata.add(6,eEliment.getElementsByTagName("email").item(0).getTextContent());
                        patientdata.add(7,eEliment.getElementsByTagName("address").item(0).getTextContent());
                        patientdata.add(8,eEliment.getElementsByTagName("landline").item(0).getTextContent());
                        patientdata.add(9,eEliment.getElementsByTagName("mobile").item(0).getTextContent());
                        patientdata.add(10,eEliment.getElementsByTagName("primaryConsultant").item(0).getTextContent());
                        patientdata.add(11,eEliment.getElementsByTagName("secondaryConsultant").item(0).getTextContent());
                        patientdata.add(12,eEliment.getElementsByTagName("confidentialNotes").item(0).getTextContent());
                        patientdata.add(13,eEliment.getElementsByTagName("status").item(0).getTextContent());

                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return patientdata;
    }

    public static ArrayList getDatafromXML(String filepath, String nodename, String tagname, String tagvalue) {

        ArrayList data = null;
        try {
            File xmlfile = new File(filepath);
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();
            Document doc = dbuilder.parse(xmlfile);

            doc.getDocumentElement().normalize();
            NodeList nlist = doc.getElementsByTagName(nodename);
            System.out.println("---------------------------");

            data = new ArrayList();

            for (int i = 0; i < nlist.getLength(); i++) {
                Node node = nlist.item(i);

                //checking the node type and if its a element, then print the elements;

                if (node.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eEliment = (Element) node;

                    if (eEliment.getNodeName().contains(nodename) & eEliment.getAttribute(tagname).equalsIgnoreCase(tagvalue)) {
                        System.out.println("=============Reading data set " + i+ "==========");

                        //reading the file and counting the child elements
                      //  System.out.println("Staff id: "+eEliment.getAttribute("id"));
                       data.add(0,eEliment.getElementsByTagName("name").item(0).getTextContent());
                       data.add(1,eEliment.getElementsByTagName("email").item(0).getTextContent());
                     //   System.out.println("Email: "+ eEliment.getElementsByTagName("email").item(0).getTextContent());

                        //  data.add(i, eEliment.getElementsByTagName("name").item(0).getTextContent());
                       // data.add(i+1, eEliment.getElementsByTagName("email").item(0).getTextContent());
                    }
                    // System.out.println("Staff id: "+eEliment.getAttribute("id"));
                    // System.out.println("Name: "+ eEliment.getElementsByTagName("name").item(1).getTextContent());
                    // System.out.println("Email: "+ eEliment.getElementsByTagName("email").item(0).getTextContent());
                }


            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return data;
    }

    public static void loginToEMR(WebDriver driver, String username,String password){


        //initializing the objects
        Objects data = new Objects();

        // Wait for element present
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(data.signup_name)));

        //enter username and password
        driver.findElement(By.name(data.username_name)).sendKeys(username);
        driver.findElement(By.name(data.password_name)).sendKeys(password);
        driver.findElement(By.name(data.signup_name)).click();

    }

}
