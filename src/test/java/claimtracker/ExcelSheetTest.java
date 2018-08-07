package claimtracker;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ExcelSheetTest {
    @Test
    public void testReadSpreadsheet() {
        String xlFilePath = "data.xlsx";
        String dateOfBirth ="2019-01-01";
        String claimNumber = "ab201811111";
        int ssn = 1111;

        try {
            Workbook workbook = WorkbookFactory.create(new File(xlFilePath));
            Sheet sheet = workbook.getSheetAt(0);
            for(int i = 0; i < 5; i ++) {
                Row row = sheet.getRow(i);
                if (row.getCell(0) != null
                        && row.getCell(0).getStringCellValue().equalsIgnoreCase(claimNumber)
                        && row.getCell(2).getNumericCellValue() == ssn
                        && dateOfBirth.contains(row.getCell(1).getStringCellValue())) {
                    String notification = row.getCell(6).getStringCellValue();
                    String resolution = row.getCell(7).getStringCellValue();
                    System.out.println(notification);
                    System.out.println(resolution);
                    return;
                }
            }
            System.out.println("test");
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(InvalidFormatException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }
    }
}
