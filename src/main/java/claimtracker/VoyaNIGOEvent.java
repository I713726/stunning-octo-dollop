package claimtracker;


public class VoyaNIGOEvent {
    private boolean fixable;
    private NIGO_Fulfillment_Type fulfillmentType;
    private String text;

    public VoyaNIGOEvent(boolean fixable, NIGO_Fulfillment_Type fulfillment_type, String text) {
        this.fixable = fixable;
        this.fulfillmentType = fulfillment_type;
        this.text = text;
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
}
