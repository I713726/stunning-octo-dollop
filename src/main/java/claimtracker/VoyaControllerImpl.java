package claimtracker;

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
                break;
            case STOP_REQUEST:
                break;
            case HELP_REQUEST:
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
            return "here are the data";
    }
}
