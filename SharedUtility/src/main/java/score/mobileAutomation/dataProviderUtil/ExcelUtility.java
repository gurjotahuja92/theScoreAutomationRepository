package score.mobileAutomation.dataProviderUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {

    public static String[][] getExcelData(String fileName, String sheetName, String methodName){
        String data[][] = null;
        List<List<String>> methodData= new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream(fileName);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet(sheetName);
            XSSFRow row = sh.getRow(0); //headers
            int noOfRows = sh.getLastRowNum();
            int noOfCols = row.getLastCellNum();
            Cell cell = null;
            for(int i =1; i<=noOfRows;i++){
                List<String> rowData = new ArrayList<>();
                row = sh.getRow(i);
                if(methodName.equals(row.getCell(0).getStringCellValue())) {
                    for (int j = 1; j < noOfCols; j++) {
                        DataFormatter formatter = new DataFormatter();
                        cell = row.getCell(j);
                        rowData.add(formatter.formatCellValue(cell));
                    }
                    methodData.add(rowData);
                }else{
                    throw new RuntimeException("No match found in excel with the method name provided from class file.");
                }
            }
            data = methodData.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
