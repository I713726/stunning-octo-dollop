package claimtracker;

/**
 * This class represents a Not in Good Order (NIGO) event that needs addressing
 */

public class VoyaNIGOEvent {
    private boolean fixable;
    private NIGO_Fulfillment_Type fulfillmentType;
    private String text;
    private String nextSteps;

    public VoyaNIGOEvent(boolean fixable, NIGO_Fulfillment_Type fulfillment_type, String text, String nextSteps) {
        this.fixable = fixable;
        this.fulfillmentType = fulfillment_type;
        this.text = text;
        this.nextSteps = nextSteps;
    }

    public String getText() {
        return this.text;
    }

    public NIGO_Fulfillment_Type getFulfillmentType() {
        return fulfillmentType;
    }

    public boolean getFixable(){
        return this.fixable;
    }

    //Return the steps that need to be taken to fix the NIGO event.
    public String getSteps() {
        return nextSteps;
    }
}
