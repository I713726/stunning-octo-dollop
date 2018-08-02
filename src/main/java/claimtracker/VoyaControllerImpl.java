package claimtracker;

public class VoyaControllerImpl implements VoyaController {
    @Override
    public VoyaResponseImpl getResponse(VoyaRequest sessionData) {
        if(sessionData.getIntent() == VoyaIntentType.CLAIM_NUMBER) {
            return new VoyaResponseImpl(0, 0, "claim number recieved", "", false);
        }
        return new VoyaResponseImpl(0, 0, "hello", "hello", false);
    }
}
