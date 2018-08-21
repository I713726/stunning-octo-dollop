package claimtracker;

/**
 * This is a pretty straightforaward interface, it has one method that takes a VoyaRequest and produces the appropriate
 * VoyaResponse. Implementations of this class should handle all of the dialog.
 */
public interface VoyaController {
    /**
     * Return the proper response for the given VoyaReqest. The response should be plain text, which is exactly what
     * will be spoken by the virtual assitant (No assistant-specific formats should be used, e.g. NO SSML)
     * @param sessionData The request (created based on posted JSON)
     * @return Appropriate response based on request
     */
    public VoyaResponse getResponse(VoyaRequest sessionData);
}
