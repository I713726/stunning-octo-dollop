package claimtracker;

/**
 * Since there could be hundereds of possible NIGO events, I decided to break down the different possible types based
 * on what kind of response they would requrire. The most common fulfillable intents required one of the following
 * types of responses.
 */
public enum NIGO_Fulfillment_Type {
    DATE, NUMBER, NAME, PHONE_NUMBER, ADDRESS
}
