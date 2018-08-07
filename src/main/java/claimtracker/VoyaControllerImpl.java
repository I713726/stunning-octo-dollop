package claimtracker;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
/**
 * Question number reference ---
 * 0: Claim number
 * 1: SSN
 * 2: DOB
 */
public class VoyaControllerImpl implements VoyaController {
    @Override
    public VoyaResponse getResponse(VoyaRequest sessionData) {
        String speech = "";
        String reprompt = "";
        int questionNumber = sessionData.getQuestionNo();
        String claimNumber = "";
        int ssn = 0;
        String dateOfBirth = "";
        boolean shouldSessionEnd = false;

        switch (sessionData.getRequestType()) {
            case LAUNCH_REQUEST:
                speech = "OK, Please say your claim number one letter at a time, I will confirm each letter to make sure" +
                        "it's correct";
                break;
            case INTENT_REQUEST:
                return this.handleIntent(sessionData);
            case CANCEL_REQUEST:
                speech = "OK, have a nice day!";
                shouldSessionEnd = true;
                break;
            case STOP_REQUEST:
                speech = "OK, have a nice day!";
                shouldSessionEnd = true;
                break;
            case HELP_REQUEST:
                speech = "In order to track a claim, we need your claim number, the last four of your ssn, " +
                        "and your date of birth";
                break;
            case SESSION_END_REQUEST:
                break;
        }
        return new VoyaResponseImpl(questionNumber, claimNumber, ssn, dateOfBirth, speech, reprompt, shouldSessionEnd);
    }

    private VoyaResponse handleIntent(VoyaRequest sessionData){
        String speech = "";
        String reprompt = "";
        int questionNumber = sessionData.getQuestionNo();
        String claimNumber = sessionData.getClaimNumber();
        int ssn = sessionData.getSSN();
        String dateOfBirth = sessionData.getDOB();
        boolean shouldSessionEnd = false;
        switch(sessionData.getIntent()) {
            case FALLBACK:
                speech = "I'm sorry?";
                break;
            case NUMBER:
                speech = "" + claimNumber.charAt(claimNumber.length() - 1);
                reprompt = "say the next number";
                if(claimNumber.length() == 11) {
                    questionNumber ++;
                    speech = "OK, now please say the last four of your social security number";
                }
                break;
            case LETTER:
                speech = "" + claimNumber.charAt(claimNumber.length() - 1) + "";

                reprompt = "say the next letter";
                if(claimNumber.length() == 11) {
                    questionNumber ++;
                    speech = "OK, now please say the last four of your social security number";
                }
                break;
            case SSN:
                speech = "OK, now please say your date of birth";
                break;
            case BIRTH_MONTH_DAY:
                speech = this.getData(claimNumber, ssn, dateOfBirth);
                shouldSessionEnd = true;
                break;
        }
        return new VoyaResponseImpl(questionNumber, claimNumber, ssn, dateOfBirth, speech, reprompt, shouldSessionEnd);
    }

    private String getData(String claimNumber, int ssn, String dateOfBirth) {
            String xlFilePath = "data.xlsx";
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

                        return "OK, " + notification + " " + resolution;
                    }
                }
            }
            catch(FileNotFoundException e) {
                return "Cannot find data file";
            }
            catch(InvalidFormatException e) {
                return "Data file has invalid format";
            }
            catch(IOException e) {
                return "I O Exception caught";
            }
            catch(NullPointerException e) {
                return "null pointer exception";
            }

        return "I'm sorry, we could not identify a claim based on the information you provided.";
    }
}
