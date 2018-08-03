package claimtracker;

/**
 * This class is an implementation of the VoyaResponse interface.
 */
public class VoyaResponseImpl implements VoyaResponse {

    int questionNumber;
    String claimNumber;
    int ssn;
    String dateOfBirth;
    String speech;
    String reprompt;
    boolean shouldSessionEnd;

    public VoyaResponseImpl(int questionNumber, String claimNumber, int ssn, String dateOfBirth,
                            String speech, String reprompt, boolean shouldSessionEnd) {
        this.questionNumber = questionNumber;
        this.claimNumber = claimNumber;
        this.ssn = ssn;
        this.dateOfBirth = dateOfBirth;
        this.speech = speech;
        this.reprompt = reprompt;
        this.shouldSessionEnd = shouldSessionEnd;
    }

    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public String getClaimNumber() {
        return claimNumber;
    }

    @Override
    public int getSSN() {
        return ssn;
    }

    @Override
    public String getSpeech() {
        return this.speech;
    }

    @Override
    public String getReprompt() {
        return this.reprompt;
    }

    @Override
    public boolean getShouldSessionEnd() {
        return this.shouldSessionEnd;
    }

    @Override
    public String getDOB() {
        return dateOfBirth;
    }


}
