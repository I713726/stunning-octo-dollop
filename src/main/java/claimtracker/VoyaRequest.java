package claimtracker;

public interface VoyaRequest {
    /**
     * returns the question number, used for tracking where the user is in the flow of interaction.
     * Question Numbers:
     * 0 - next digit/letter of claim number
     * 1 - last 4 digits of SSN
     * 2 - date of birth (month, day)
     * @return the question number
     */
    int getQuestionNo();

    /**
     * returns the user's claim number as a String (because claim numbers have letters in them too)
     * @return user claim number
     */
     String getClaimNumber();

    /**
     * return the last 4 of the SSN used for confirmation
     * @return last 4 of SSN
     */
    int getSSN();

    /**
     * return birth month and day used for confirmation
     * @return birth month/day
     */
    String getDOB();

    /**
     * Returns the type of request
     * @return type of request in the response, from enum VoyaRequestType
     */
    VoyaRequestType getRequestType();

    /**
     * Returns a string symbolically representing the language and country, e.g. en-US, es-ES
     * @return locale code from <a href="https://developer.amazon.com/docs/custom-skills/develop-skills-in-multiple-languages.html">here</a>
     */
    String getLocale();

    /**
     * Returns the intent type, if the request is an intent request. Otherwise, returns null.
     * @return intent type from enum VoyaIntentType
     */
    VoyaIntentType getIntent();
}