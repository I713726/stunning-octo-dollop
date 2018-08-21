package claimtracker;

/**
 * This interface represents the response sent from the web application to the virtual assistant. How this is constructed
 * into JSON is another class's responsibility
 */
public interface VoyaResponse {

    /**
     * Returns the question number.
     *     0 - PIN
     *     1 - Would you like to hear suggestions to help you retire sooner?
     *     2 - reccomends that you increase savings by 2%, yes/no
     *     3 - option to save 1% more a year from now
     * @return the question number
     */
    int getQuestionNumber();

    /**
     * Returns the user's PIN.
     * @return user PIN number
     */
    int getUserPIN();

    /**
     * return the index of the claim chosen by the user, for use as a session variable.
     * @return
     */
    int getClaimIndex();

    /**
     * return the index of the current NIGO event in the current claim, starts at 0.
     * @return index of current NIGO event in current claim, starts at 0
     */
    int getNIGOIndex();

    /**
     * Returns the speech to be said by the virtual assistant.
     * @return literal words to be said by the virtual assistant, no ssml tags
     */
    String getSpeech();

    /**
     * Returns the text used for a reprompt.
     * @return text used for reprompt (this exists in Amazon Alexa, I don't know if it's a thing in Google Home)
     */
    String getReprompt();

    /**
     * returns the text of a NIGO response, this is stored as a session variable until it is confirmed by the user.
     * @return
     */
    String getNIGOResponse();

    /**
     * Returns the boolean that says whether the session ends. If true, this response ends the session, if false the
     * session continues.
     * @return the boolean that says whether the session ends now.
     */
    boolean getShouldSessionEnd();
}
