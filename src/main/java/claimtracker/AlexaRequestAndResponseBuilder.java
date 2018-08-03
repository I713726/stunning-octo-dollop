package claimtracker;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import org.json.JSONException;
import org.json.JSONObject;

public class AlexaRequestAndResponseBuilder implements VoyaRequestAndResponseBuilder{


    @Override
    public VoyaRequest buildRequest(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        int questionNo = 0;
        String claimNumber = "";
        int ssn = 0;
        String dateOfBirth = "";
        VoyaIntentType intentType;

        String locale = jsonObject.getJSONObject("request").getString("locale");
        VoyaRequestType requestType = this.getRequestType(jsonObject.getJSONObject("request").getString("type"));

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
        try {
            claimNumber = jsonObject.getJSONObject("session").getJSONObject("attributes").getString("claimNumber");
        }
        catch(JSONException e) {
            claimNumber = "";
        }
        if(requestType == VoyaRequestType.INTENT_REQUEST) {
            if(intentType == VoyaIntentType.LETTER) {
                claimNumber += jsonObject.getJSONObject("request").getJSONObject("intent").getJSONObject("slots").getJSONObject("letter").getString("value");
            }
            else if(intentType == VoyaIntentType.NUMBER) {
                claimNumber += jsonObject.getJSONObject("request").getJSONObject("intent").getJSONObject("slots").getJSONObject("number").getString("value");
            }
            else if(intentType == VoyaIntentType.BIRTH_MONTH_DAY) {
                dateOfBirth = jsonObject.getJSONObject("request").getJSONObject("intent").getJSONObject("slots").getJSONObject("dateOfBirth").getString("value");
            }
            else if(intentType == VoyaIntentType.SSN) {
                ssn = jsonObject.getJSONObject("request").getJSONObject("intent").getJSONObject("slots").getJSONObject("ssn").getInt("value");
            }
        }


        return new VoyaRequestImpl(questionNo, claimNumber, dateOfBirth, ssn, locale, requestType, intentType);
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
                .put("claimNumber", response.getClaimNumber()).put("dateOfBirth", response.getDOB()).put("ssn", response.getSSN()));
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
            case "VoyaClaimNumberLetter":
                return VoyaIntentType.LETTER;
            case "VoyaClaimNumberNumber":
                return VoyaIntentType.NUMBER;
            case "VoyaSSN":
                return VoyaIntentType.SSN;
            case "VoyaDateOfBirth":
                return VoyaIntentType.BIRTH_MONTH_DAY;
            default:
                throw new IllegalArgumentException("Unrecognized intent type");
        }
    }

}
