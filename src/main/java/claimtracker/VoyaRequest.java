package claimtracker;

/**
 * This class represents a request sent to the skill, with set of information about the requeset that can be retrieved
 * with the methods described in this class.
 */

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
     * returns the user's PIN number
     * @return user PIN number
     */
     int getUserPIN();

    /**
     * Once the user has chosen a claim to review, it will return the index of the claim they chose in their list of claims.
     * If the user has not chosen yet it will be zero. One is the first valid claim index.
     * @return the index of the claim, starting at 1 for actual indicies and 0 for uninitialized
     */
    int getClaimIndex();

    /**
     * The NIGO Index keeps track of how far we are into the current claim's list of NIGO events, starts at 0.
     * @return the index into the current claim's NIGO events, starting at 0
     */
    int getNIGOIndex();

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

    /**
     * gives us the user's answer to any NIGO status the user could fulfill.
     * @return a string of text that addresses the NIGO event.
     */
    String getNIGOResponse();
}