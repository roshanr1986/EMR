import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class readFromXML {

    @Test
    public static void ReadFromXML() {
    Functions func =new Functions();
        ArrayList data=new ArrayList();
        data=func.getDatafromXML("src/main/data.xml","staff","id","1");
       // for(int i=0;i<data.size();i++){
      //  System.out.println(data.get(i));
       // }
        System.out.println(data.get(0));
        System.out.println(data.get(1));
    }
}
