package claimtracker;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import org.json.JSONException;
import org.json.JSONObject;

public class AlexaRequestAndResponseBuilder implements VoyaRequestAndResponseBuilder{


    @Override
    public VoyaRequest buildRequest(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        int questionNo;
        int voyaPIN;
        VoyaIntentType intentType;
        try{
            questionNo = jsonObject.getJSONObject("session").getJSONObject("attributes").getInt("questionNo");
        }
        catch(JSONException e) {
            questionNo = 0;
        }

        try{
            intentType = this.getIntentType(jsonObject.getJSONObject("request").getJSONObject("intent").getString("name"));
        }
        catch(JSONException e) {
            intentType = null;
        }

        VoyaRequestType requestType = this.getRequestType(jsonObject.getJSONObject("request").getString("type"));
        String locale = jsonObject.getJSONObject("request").getString("locale");

        return new VoyaRequestImpl(questionNo, 0, requestType, locale, intentType);
    }

    @Override
    public String buildResponse(VoyaResponse response) {
        JSONObject outJson = new JSONObject();
        JsonStringEncoder encoder = new JsonStringEncoder();
        outJson.put("version", 1.0);
        outJson.put("response", new JSONObject().put("outputSpeech", new JSONObject().put("type", "SSML")
                .put("ssml", "<speak>" + response.getSpeech() + "</speak>")));
        outJson.getJSONObject("response").put("reprompt", new JSONObject().put("outputSpeech",
                new JSONObject().put("type", "SSML").put("ssml", ("<speak>" + response.getReprompt() + "</speak>"))));
        /*
        outJson.getJSONObject("response").put("directives", new JSONArray().put(
                new JSONObject().put("type", "VideoApp.Launch").put("videoItem", new JSONObject().put("source", "https://youtu.be/XFJRJPY7Z_A")
                .put("metadata", new JSONObject().put("title", "retirement day video").put("subtitle", "subtitle")))));
                */
        outJson.getJSONObject("response").put("shouldEndSession", response.getShouldSessionEnd());
        outJson.put("sessionAttributes", new JSONObject().put("questionNo", response.getQuestionNumber())
                .put("voyaPin", response.getUserPIN()));
        return outJson.toString();
    }

    private VoyaRequestType getRequestType(String requestType) {
        switch(requestType) {
            case "LaunchRequest":
                return VoyaRequestType.LAUNCH_REQUEST;
            case "IntentRequest":
                return VoyaRequestType.INTENT_REQUEST;
            case "HelpRequest":
                return VoyaRequestType.HELP_REQUEST;
            case "SessionEndedRequest":
                return VoyaRequestType.SESSION_END_REQUEST;
            case "StopRequest":
                return VoyaRequestType.STOP_REQUEST;
            default:
                return VoyaRequestType.CANCEL_REQUEST;
        }
    }

    private VoyaIntentType getIntentType(String intentType) {
        switch(intentType) {
            case "VoyaClaimNumberIntent":
                return VoyaIntentType.CLAIM_NUMBER;
            case "VoyaSSNIntent":
                return VoyaIntentType.SSN;
            case "VoyaDateOfBirthIntent":
                return VoyaIntentType.BIRTH_MONTH_DAY;
            default:
                throw new IllegalArgumentException("Unrecognized intent type");
        }
    }
}
