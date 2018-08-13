package claimtracker;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Question number reference ---
 * 0: PIN number
 * 1: Which claim?
 * 2: would you like to address NIGO items?
 * 3: respond to next NIGO item
 */
public class VoyaControllerImpl implements VoyaController {
    @Override
    public VoyaResponse getResponse(VoyaRequest sessionData) {
        String speech = "";
        String reprompt = "";
        int questionNumber = sessionData.getQuestionNo();
        int claimIndex = 0;
        int voyaPin = 0;
        String dateOfBirth = "";
        boolean shouldSessionEnd = false;

        switch (sessionData.getRequestType()) {
            case LAUNCH_REQUEST:
                speech = "Hi, Welcome to Voya E.B. Claims tracker service. To get started, please say the 4 digit pin" +
                        "you set up when enabling the skill";
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
                speech = "OK, have a nice day!";
                break;
        }
        return new VoyaResponseImpl(questionNumber, voyaPin, claimIndex, dateOfBirth, speech, shouldSessionEnd);
    }

    private VoyaResponse handleIntent(VoyaRequest sessionData){
        String speech = "";
        String reprompt = "";
        int questionNumber = sessionData.getQuestionNo();
        int claimIndex = sessionData.getClaimIndex();
        int userPIN = sessionData.getUserPIN();
        boolean shouldSessionEnd = false;
        if(userPIN == 0) {
            if(sessionData.getIntent() == VoyaIntentType.NO) {
                speech = "OK, have a nice day.";
                shouldSessionEnd = true;
            }
            else {
                speech = "OK, please say your PIN number";
                shouldSessionEnd = false;
            }
        }
        else {
            switch (sessionData.getIntent()) {

                case FALLBACK:
                    speech = "I'm sorry?";
                    break;
                case NO:
                    speech = "OK, have a nice day";
                    shouldSessionEnd = true;
                    break;
                case YES:
                    switch (questionNumber) {
                        case 0:
                            speech = "OK, go ahead and say your PIN";
                            shouldSessionEnd = false;
                            break;
                        case 1:
                            speech = "OK, you can pick one of the claims";
                            shouldSessionEnd = false;
                            break;
                        case 2:
                            speech = "OK, here's the first item. " + this.getData(userPIN).getNextNIGOEvent(claimIndex);
                            break;
                        case 3:
                            break;
                    }
                    break;
                case PIN:
                    VoyaUserDataObject userDataObject = this.getData(userPIN);
                case CHOOSE_CLAIM:
                    userDataObject = this.getData(userPIN);
                    speech = "description of " + userDataObject.presentClaim(claimIndex);
                    break;
                case NIGO_RESPONSE:
                    userDataObject = this.getData(userPIN);
                    this.submitNIGOResponse(sessionData.getNIGOResponse());
                    speech = "OK, thanks for your response.";
                    break;
                case HELP:
                    speech = "Help message";
                    break;
                case CANCEL:
                    speech = "OK, have a nice day!";
                    shouldSessionEnd = true;
                    break;
            }
        }
        return new VoyaResponseImpl(questionNumber, userPIN, claimIndex, speech, reprompt, shouldSessionEnd);
    }

    private VoyaUserDataObject getData(int userPin) {

        /*
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
        */

        return new VoyaUserDataObject() {

            @Override
            public String listClaims() {
                return "here are your claims";
            }

            @Override
            public String presentClaim(int index) {
                return "here is a claim";
            }

            @Override
            public String getFirstName() {
                return "John";
            }

            @Override
            public String getLastName() {
                return "Smith";
            }

            @Override
            public String getNextNIGOEvent(int claimIndex) {
                return "here is the next event";
            }

            @Override
            public void respondToCurrentNIGOEvent(int claimIndex) {

            }
        };
    }

    private void submitNIGOResponse(String nigoResponse) {
        //here we submit the response however that has to be done.
    }
}
