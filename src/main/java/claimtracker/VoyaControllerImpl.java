package claimtracker;

public class VoyaControllerImpl implements VoyaController {
    @Override
    public VoyaResponse getResponse(VoyaRequest sessionData) {
        return new VoyaResponseImpl(0, 0, "hello", "hello", false);
    }
}
