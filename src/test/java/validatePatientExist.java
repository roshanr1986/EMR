import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class validatePatientExist {
    @Test
    public void validatePatient(){
        Functions function = new Functions();
        WebDriver driver=function.createDriver();

        int outcome=function.validateRecordExist(driver,"86397546581V");
        System.out.println(outcome);
    }
}
