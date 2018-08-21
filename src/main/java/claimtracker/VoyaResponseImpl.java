package claimtracker;

/**
 * This class is an implementation of the VoyaResponse interface.
 */
public class VoyaResponseImpl implements VoyaResponse {

    int questionNumber;
    int claimIndex;
    int userPIN;
    String speech;
    String reprompt;
    boolean shouldSessionEnd;
    int nigoIndex;
    String nigoResponse;

    public VoyaResponseImpl(int questionNumber, int userPIN, int claimIndex, int nigoIndex, String nigoResponse,
                            String speech, String reprompt, boolean shouldSessionEnd) {
        this.questionNumber = questionNumber;
        this.claimIndex = claimIndex;
        this.nigoIndex = nigoIndex;
        this.userPIN = userPIN;
        this.speech = speech;
        this.nigoResponse = nigoResponse;
        this.reprompt = reprompt;
        this.shouldSessionEnd = shouldSessionEnd;
    }

    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public int getUserPIN() {
        return this.userPIN;
    }

    @Override
    public int getClaimIndex() {
        return this.claimIndex;
    }

    @Override
    public int getNIGOIndex() {
        return this.nigoIndex;
    }

    @Override
    public String getNIGOResponse() {
        return this.nigoResponse;
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



}
