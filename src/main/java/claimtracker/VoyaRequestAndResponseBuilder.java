package claimtracker;

/**
 * This interface is the layer of translation between the JSON recieved and sent to the virtual assistant and the
 * VoyaRequest and VoyaResponse classes used to process the dialog interally.
 */

public interface VoyaRequestAndResponseBuilder {

    /**
     * This method parses JSON data from a virtual assistant and uses it to create a VoyaRequest object
     * @param jsonData input JSON from the virtual assistant (e.g. Alexa)
     * @return VoyaRequest to be procesed by the VoyaController
     */
    VoyaRequest buildRequest(String jsonData);

    /**
     * This method takes a VoyaResponse object and uses it to create a JSON string to be sent to the virtual assistant.
     * @param response Response created by the VoyaController
     * @return JSON response for the virtual assistant (Alexa or Google Home)
     */
    String buildResponse(VoyaResponse response);
}
