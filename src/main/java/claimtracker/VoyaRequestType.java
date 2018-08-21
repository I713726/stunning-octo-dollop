package claimtracker;

/**
 * This interface represents the different types of requests that could be sent to the application. however, this
 * feature is specific to alexa and is not necessary for the dialog to progress, so it may be removed from the program.
 * It may be possible to simply convert requests to VoyaIntentTypes.
 */
public enum VoyaRequestType {
        LAUNCH_REQUEST, SESSION_END_REQUEST, INTENT_REQUEST, HELP_REQUEST, STOP_REQUEST, CANCEL_REQUEST
}
