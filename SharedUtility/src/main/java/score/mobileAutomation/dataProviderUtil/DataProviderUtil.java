package score.mobileAutomation.dataProviderUtil;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class DataProviderUtil {

    @DataProvider(name ="BrowseDataProvider")
    public Object[][] TestDataProvider(ITestContext context, Method m) throws IOException {

        String filePath = context.getCurrentXmlTest().getParameter("testdata.path");
        String sheetName=null;
        if(m.getName().toLowerCase().contains("Homepage"))
            sheetName="Homepage";
        if(m.getName().toLowerCase().contains("registration"))
            sheetName="Registration";
        if(m.getName().toLowerCase().contains("homepage"))
            sheetName="HomePage";
        Object[][] data = ExcelUtility.getExcelData(System.getProperty("user.dir")+ File.separator+filePath
                , sheetName, m.getName());
        return data;
    }
}
