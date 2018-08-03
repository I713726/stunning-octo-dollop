package claimtracker;

public class VoyaRequestImpl implements VoyaRequest {

    int questionNo;
    String claimNumber;
    VoyaRequestType requestType;
    String locale;
    int ssn;
    String dateOfBirth;
    VoyaIntentType intentType;

    public VoyaRequestImpl(int questionNo, String claimNumber, String dateOfBirth, int ssn, String locale,
                           VoyaRequestType requestType, VoyaIntentType intentType){
        this.questionNo = questionNo;
        this.requestType = requestType;
        this.dateOfBirth = dateOfBirth;
        this.ssn = ssn;
        this.claimNumber = claimNumber;
        this.locale = locale;
        this.intentType = intentType;
    }

    @Override
    public int getQuestionNo() {
        return questionNo;
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
    public String getDOB() {
        return dateOfBirth;
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
