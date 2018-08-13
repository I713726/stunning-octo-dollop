package claimtracker;

public interface VoyaUserDataObject {

    String listClaims();

    String presentClaim(int index);

    String getFirstName();

    String getLastName();

    String getNextNIGOEvent(int claimIndex);

    void respondToCurrentNIGOEvent(int claimIndex);
}