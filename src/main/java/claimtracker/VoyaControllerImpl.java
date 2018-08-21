package claimtracker;

/*
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
*/

import java.util.List;

/**
 * Question number reference ---
 * 0: PIN number
 * 1: Which claim?
 * 2: would you like to address NIGO item?
 * 3: say NIGO... would you like to respond to this NIGO
 * 4: respond to next NIGO item
 * 5: confirm input
 * 6: Would you like to check the status of another claim?
 */
public class VoyaControllerImpl implements VoyaController {
    @Override
    public VoyaResponse getResponse(VoyaRequest sessionData) {
        String speech = "";
        String reprompt = "";
        int questionNumber = sessionData.getQuestionNo();
        int claimIndex = sessionData.getClaimIndex();
        int nigoIndex = sessionData.getNIGOIndex();
        int voyaPin = 0;
        String dateOfBirth = "";
        boolean shouldSessionEnd = false;

        switch (sessionData.getRequestType()) {
            case LAUNCH_REQUEST:
                speech = "Hi, Welcome to Voya E.B. Claims tracker service. To get started, please say the 4 digit pin" +
                        " you set up when enabling the skill";
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
                //TODO: Make this relevant
                speech = "In order to track a claim, we need your claim number, the last four of your ssn, " +
                        "and your date of birth";
                break;
            case SESSION_END_REQUEST:
                speech = "OK, have a nice day!";
                break;
        }
        return new VoyaResponseImpl(questionNumber, voyaPin, claimIndex, nigoIndex, "", speech, reprompt, shouldSessionEnd);
    }

    private VoyaResponse handleIntent(VoyaRequest sessionData) {
        String speech = "";
        String reprompt = "";
        int questionNumber = sessionData.getQuestionNo();
        int claimIndex = sessionData.getClaimIndex();
        int nigoIndex = sessionData.getNIGOIndex();
        int userPIN = sessionData.getUserPIN();
        boolean shouldSessionEnd = false;
        String nigoResponse = sessionData.getNIGOResponse();
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
                    switch(questionNumber) {
                        case 0:
                            speech = "OK, have a nice day";
                            shouldSessionEnd = true;
                            break;
                        case 1:
                            speech = "OK, have a nice day";
                            shouldSessionEnd = true;
                            break;
                        case 2:
                            speech = "OK, would you like to check the status of another claim?";
                            questionNumber = 6;
                            nigoIndex = 0;
                            break;
                        case 3:
                            nigoIndex ++;
                            if(nigoIndex >= this.getData(userPIN).getClaim(claimIndex - 1).getNumNIGOEvents()) {
                                speech = "OK, that's all the events for this claim, would you like to check the status " +
                                        "of another claim?";
                                questionNumber = 6;
                            }
                            else {
                                VoyaNIGOEvent event = this.getData(userPIN).getClaim(claimIndex - 1).getNIGOEvent(nigoIndex);
                                speech = "OK, here's the next item. " + event.getText();
                                if(event.getFixable()) {
                                    speech += ". Would you like to respond to this item now?";
                                }
                                else {
                                    speech += ". " + event.getSteps() + ". Would you like to hear the next item?";
                                }
                                questionNumber = 2;
                                shouldSessionEnd = false;
                            }
                            break;
                        case 4:
                            speech = "OK, would you like to check the status of another claim?";
                            questionNumber = 5;
                            break;
                        case 5:
                            speech = "OK, I'm sorry, could you say it again?";
                            questionNumber = 3;
                            break;
                        case 6:
                            speech = "OK, have a nice day!";
                            shouldSessionEnd = true;
                    }
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
                            VoyaNIGOEvent event = this.getData(userPIN).getClaim(claimIndex - 1).getNIGOEvent(nigoIndex);
                            if(nigoIndex > 0) {
                                nigoIndex ++;
                                event = this.getData(userPIN).getClaim(claimIndex - 1).getNIGOEvent(nigoIndex);
                                speech = "OK, here is the next item. " + event.getText();
                            }
                            else {
                                speech = "OK, here is the first item. " + event.getText();
                            }
                            if(event.getFixable()) {
                                speech += ". Would you like to respond to this item now?";
                            }
                            else {
                                speech += ". " + event.getSteps();
                            }
                            questionNumber = 3;
                            shouldSessionEnd = false;
                            break;
                        case 3:
                            String type;
                            event = this.getData(userPIN).getClaim(claimIndex - 1).getNIGOEvent(nigoIndex);
                            if(event.getFixable()) {
                                switch(event.getFulfillmentType()) {
                                    case LONG_NUMBER:
                                        type = "number";
                                        break;
                                    case NUMBER:
                                        type = "number";
                                        break;
                                    case NAME:
                                        type = "name";
                                        break;
                                    case DATE:
                                        type = "date";
                                        break;
                                    case ADDRESS:
                                        type = "address";
                                        break;
                                    default:
                                        throw new IllegalArgumentException("Unknown fulfillment type for NIGO event," +
                                                " the controller must not have the latest information about what types" +
                                                " are possible");
                                }
                                speech = "OK, go ahead and say the " + type;
                                questionNumber = 4;
                                shouldSessionEnd = false;
                            }
                            else {
                                this.submitNIGOResponse(nigoResponse);
                                speech = "Thanks for your response, it has been sent! ";
                                nigoIndex ++;
                                event = this.getData(userPIN).getClaim(claimIndex - 1).getNIGOEvent(nigoIndex);
                                speech += "OK, here's the next item. " + event.getText();
                                if(event.getFixable()) {
                                    speech += ". Would you like to respond to this item now?";
                                }
                                else {
                                    speech += ". " + event.getSteps();
                                }
                                questionNumber = 3;
                                shouldSessionEnd = false;
                            }
                            break;
                        case 4:
                            speech = "Just say it.";
                            break;
                        case 5:
                            VoyaUserDataObject userDataObject = this.getData(userPIN);
                            speech = "OK, thanks for your response.";
                            nigoIndex ++;
                            if(claimIndex == 0 || nigoIndex >= userDataObject.getNIGOEvents(claimIndex - 1).size()) {
                                speech += "That's everything for this claim. Thanks for helping resolve those issues." +
                                        "Would you like to check the status of another claim?";
                                questionNumber = 6;
                                nigoIndex = 0;
                                claimIndex = 0;
                            }
                            else {
                                event = userDataObject.getClaim(claimIndex - 1).getNIGOEvent(nigoIndex);
                                speech += "OK, here's the next item. " + event.getText();
                                if(event.getFixable()) {
                                    speech += ". Would you like to respond to this item now?";
                                }
                                else {
                                    speech += ". " + event.getSteps() + " Would you like to hear the next item?";
                                }
                                questionNumber = 2;
                                shouldSessionEnd = false;
                            }
                            break;
                        case 6:
                            userDataObject = this.getData(userPIN);
                            speech = "OK, Here are all of the claims that you have submitted. You have: ";
                            List<VoyaClaim> claimList = userDataObject.getClaims();
                            if (claimList.size() == 0) {
                                speech += "No claims.";
                            } else {
                                String claimType = "";
                                String claimState = "";
                                int count = 1;
                                for (VoyaClaim claim : claimList) {
                                    switch (claim.getClaimType()) {
                                        case ACCIDENT:
                                            claimType = "accident";
                                            break;
                                        case WELLNESS:
                                            claimType = "wellness";
                                            break;
                                    }
                                    switch (claim.getState()) {
                                        case PAID:
                                            claimState = "paid";
                                            break;
                                        case APPROVED:
                                            claimState = "approved";
                                            break;
                                        case DENIED:
                                            claimState = "denied";
                                            break;
                                        case RECIEVED:
                                            claimState = "recieved";
                                            break;
                                    }
                                    speech += String.format("Claim %d %s claim has been %s.\n", count, claimType, claimState);
                                    count++;
                                }
                            }
                            speech += "Which claim do you want to get status on?";
                            questionNumber = 1;
                            break;
                    }
                    break;
                case PIN:
                    VoyaUserDataObject userDataObject = this.getData(userPIN);
                    speech = "Here are all of the claims that you have submitted. You have: ";
                    List<VoyaClaim> claimList = userDataObject.getClaims();
                    if(claimList.size() == 0) {
                        speech += "No claims.";
                    }
                    else {
                        String claimType = "";
                        String claimState = "";
                        int count = 1;
                        for(VoyaClaim claim: claimList) {
                            switch(claim.getClaimType()) {
                                case ACCIDENT:
                                    claimType = "accident";
                                    break;
                                case WELLNESS:
                                    claimType = "wellness";
                                    break;
                            }
                            switch(claim.getState()) {
                                case PAID:
                                    claimState = "paid";
                                    break;
                                case APPROVED:
                                    claimState = "approved";
                                    break;
                                case DENIED:
                                    claimState = "denied";
                                    break;
                                case RECIEVED:
                                    claimState = "recieved";
                                    break;
                            }
                            speech += String.format("Claim %d %s claim has been %s.\n", count, claimType, claimState);
                            count ++;
                        }
                    }
                    speech += "Which claim do you want to get status on?";
                    questionNumber = 1;
                    break;
                case CHOOSE_CLAIM:
                    userDataObject = this.getData(userPIN);
                    VoyaClaim claim;
                    claimIndex = sessionData.getClaimIndex();

                    if(claimIndex > userDataObject.getClaims().size()) {
                        speech = "sorry, can you pick one of the ones I listed? there were " +
                                userDataObject.getClaims().size() + " of them";
                    }
                    else {
                        claim = userDataObject.getClaim(claimIndex - 1);
                        speech += claim.getText();
                        if (claim.getNumNIGOEvents() > 0) {
                            speech +=
                                    String.format("\n There are %d issues with this claim, %d of which can be resolved now. " +
                                            "Would you like to hear them?", claim.getNumNIGOEvents(), claim.getNumFixableNIGOEvents());
                            questionNumber = 2;
                        }
                        else {
                            speech += " Would you like to get the status of another claim?";
                            questionNumber = 6  ;
                        }
                    }

                    break;
                case NIGO_RESPONSE:
                    if(questionNumber == 4) {
                        userDataObject = this.getData(userPIN);
                        speech = "OK, just cofirming, the response you gave is " + sessionData.getNIGOResponse() +", right?";
                        questionNumber = 5;
                    }
                    else {
                        speech = "I'm sorry, im not sure what you mean by that.";
                    }
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
        return new VoyaResponseImpl(questionNumber, userPIN, claimIndex, nigoIndex, nigoResponse, speech, reprompt, shouldSessionEnd);
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
        VoyaClaim claim1 = new VoyaClaim("We recieved this claim by mail on 08/03/2018. We are currently  reviewing" +
                " your claim. This step should be completed no later than 8/17/2018", VoyaClaimType.ACCIDENT, VoyaClaimState.RECIEVED);
        VoyaClaim claim2 = new VoyaClaim("Your accident claim for $10,000 has been approved. Payment will be sent by" +
                " check within 24-48 hours", VoyaClaimType.ACCIDENT, VoyaClaimState.APPROVED);
        VoyaClaim claim3 = new VoyaClaim("Your accident claim has been paid. The payment amount of $10,000 has" +
                " been sent by check on 08/13/2018. Please allow 7-10 business days for mailing time.", VoyaClaimType.ACCIDENT, VoyaClaimState.PAID);

        claim1.addNIGOEvent(new VoyaNIGOEvent(true, NIGO_Fulfillment_Type.DATE,"There was a date missing", "Provide the date"));
        claim1.addNIGOEvent(new VoyaNIGOEvent(false, null, "need a document", "Please submit the thing the way you're supposed to"));
        claim1.addNIGOEvent(new VoyaNIGOEvent(true, NIGO_Fulfillment_Type.ADDRESS, "We need an address", "Provide the address"));
        UserDataObjectImpl retObj = new UserDataObjectImpl();
        retObj.addClaim(claim1);
        retObj.addClaim(claim2);
        retObj.addClaim(claim3);

        return retObj;
    }

    private void submitNIGOResponse(String nigoResponse) {
        //here we submit the response however that has to be done.
    }
}
