package claimtracker;

import java.util.List;

/**
 * This interface represents the data stored for each user, such as the name (first and last), claims associated with
 * the user, and the ability to add claims.
 */
public interface VoyaUserDataObject {

    List<VoyaClaim> getClaims();

    VoyaClaim getClaim(int index);

    String getFirstName();

    String getLastName();

    List<VoyaNIGOEvent> getNIGOEvents(int claimIndex);

    void respondToCurrentNIGOEvent(int claimIndex, String response);

    void addClaim(VoyaClaim claim);
}