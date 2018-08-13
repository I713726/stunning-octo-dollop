package claimtracker;

public class VoyaRequestImpl implements VoyaRequest {

    int questionNo;
    int userPIN;
    int claimIndex;
    VoyaRequestType requestType;
    String locale;
    VoyaIntentType intentType;

    public VoyaRequestImpl(int questionNo, int userPIN, int claimIndex, String locale,
                           VoyaRequestType requestType, VoyaIntentType intentType){
        this.questionNo = questionNo;
        this.requestType = requestType;
        this.userPIN = userPIN;
        this.claimIndex = claimIndex;
        this.locale = locale;
        this.intentType = intentType;
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
        return this.getClaimIndex();
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


}
