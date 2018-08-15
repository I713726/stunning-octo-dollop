package claimtracker;

public class VoyaRequestImpl implements VoyaRequest {

    int questionNo;
    int userPIN;
    int claimIndex;
    int nigoIndex;
    VoyaRequestType requestType;
    String locale;
    VoyaIntentType intentType;
    String nigoResponse;

    public VoyaRequestImpl(int questionNo, int userPIN, int claimIndex, int nigoIndex, String locale,
                           VoyaRequestType requestType, VoyaIntentType intentType, String nigoResponse){
        this.questionNo = questionNo;
        this.requestType = requestType;
        this.userPIN = userPIN;
        this.claimIndex = claimIndex;
        this.nigoIndex = nigoIndex;
        this.locale = locale;
        this.intentType = intentType;
        this.nigoResponse = nigoResponse;
    }

    @Override
    public int getQuestionNo() {
        return questionNo;
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
    public VoyaRequestType getRequestType() {
        return requestType;
    }

    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public VoyaIntentType getIntent() {
        return intentType;
    }

    public String getNIGOResponse() {
        return nigoResponse;
    }
}
