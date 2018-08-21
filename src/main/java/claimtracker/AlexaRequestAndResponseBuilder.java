package claimtracker;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is an implementation of the VoyaRequestAndResponseBuilder that processes and creates JSON in the format
 * supported by Amazon Alexa.
 */
public class AlexaRequestAndResponseBuilder implements VoyaRequestAndResponseBuilder{


    @Override
    public VoyaRequest buildRequest(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        int questionNo = 0;
        int userPIN = 0;
        int claimIndex = 0;
        int nigoIndex = 0;
        VoyaIntentType intentType;
        String nigoResponse = "";

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
            userPIN = jsonObject.getJSONObject("session").getJSONObject("attributes").getInt("voyaPIN");
        }
        catch(JSONException e) {
            try {
                userPIN = jsonObject.getJSONObject("request").getJSONObject("intent").getJSONObject("slots")
                        .getJSONObject("VoyaPIN").getInt("value");
            } catch(JSONException e1) {
                userPIN = 0;
            }
        }
        if(intentType == VoyaIntentType.CHOOSE_CLAIM){
              try {
                  claimIndex = jsonObject.getJSONObject("request").getJSONObject("intent").getJSONObject("slots")
                          .getJSONObject("claimIndex").getJSONObject("resolutions").getJSONArray("resolutionsPerAuthority")
                          .getJSONObject(0).getJSONArray("values").getJSONObject(0).getJSONObject("value").getInt("name");
              } catch (JSONException e1) {
                  throw new IllegalArgumentException("Couldn't find the claim index in a choose claim request");
              }
        }
        else {
            try{
               claimIndex = jsonObject.getJSONObject("session").getJSONObject("attributes").getInt("claimIndex");
            }
            catch(JSONException e) {
                claimIndex = 0;
            }
        }
        if(intentType == VoyaIntentType.PIN) {
            userPIN = jsonObject.getJSONObject("request").getJSONObject("intent").getJSONObject("slots").getJSONObject("voyaPIN").getInt("value");
        }
        try {
            nigoIndex = jsonObject.getJSONObject("session").getJSONObject("attributes").getInt("nigoIndex");
        }
        catch(JSONException e) {
            nigoIndex = 0;
        }
        if(intentType == VoyaIntentType.NIGO_RESPONSE) {
            JSONObject fullfillmentSlot = jsonObject.getJSONObject("request").getJSONObject("intent").getJSONObject("slots");
            if(fullfillmentSlot.getJSONObject("date").has("value")) {
                nigoResponse = fullfillmentSlot.getJSONObject("date").getString("value");
            }
            else if(fullfillmentSlot.getJSONObject("phone_number").has("value")) {
                nigoResponse = fullfillmentSlot.getJSONObject("phone_number").getString("value");
            }
            else if(fullfillmentSlot.getJSONObject("address").has("value")) {
                nigoResponse = fullfillmentSlot.getJSONObject("address").getString("value");
            }
            else if(fullfillmentSlot.getJSONObject("number").has("value")) {
                nigoResponse = fullfillmentSlot.getJSONObject("number").getString("value");
            }
            else {
                throw new IllegalArgumentException("Unknown type of value for NIGO response.");
            }
        }
        else {
            try {
                nigoResponse = jsonObject.getJSONObject("session").getJSONObject("attributes").getString("nigoResponse");
            }catch (JSONException e) {
                nigoResponse = "";
            }
        }
        return new VoyaRequestImpl(questionNo, userPIN, claimIndex, nigoIndex, locale, requestType, intentType, nigoResponse);
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
                .put("voyaPIN", response.getUserPIN()).put("claimIndex", response.getClaimIndex())
                .put("nigoIndex", response.getNIGOIndex()).put("nigoResponse", response.getNIGOResponse()));
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
            default:
                return VoyaRequestType.STOP_REQUEST;
        }
    }


    private VoyaIntentType getIntentType(String intentType) {
        switch(intentType) {
            case "VoyaChooseClaimIntent":
                return VoyaIntentType.CHOOSE_CLAIM;
            case "VoyaYesIntent":
                return VoyaIntentType.YES;
            case "VoyaNoIntent":
                return VoyaIntentType.NO;
            case "VoyaNIGOFullfillmentIntent":
                return VoyaIntentType.NIGO_RESPONSE;
            case "AMAZON.FallbackIntent":
                return VoyaIntentType.FALLBACK;
            case "VoyaPINIntent":
                return VoyaIntentType.PIN;
            case "AMAZON.CancelIntent":
                return VoyaIntentType.CANCEL;
            case "AMAZON.HelpIntent":
                return VoyaIntentType.HELP;
            case "AMAZON.StopIntent":
                return VoyaIntentType.CANCEL;
            default:
                throw new IllegalArgumentException("Unrecognized intent type");
        }
    }

}
