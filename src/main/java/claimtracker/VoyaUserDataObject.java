package claimtracker;

import java.util.List;

public interface VoyaUserDataObject {

    List<VoyaClaim> getClaims();

    VoyaClaim getClaim(int index);

    String getFirstName();

    String getLastName();

    VoyaNIGOEvent getNextNIGOEvent(int claimIndex);

    VoyaNIGOEvent peekNextNIGOEvent(int claimIndex);

    List<VoyaNIGOEvent> getNIGOEvents(int claimIndex);

    void respondToCurrentNIGOEvent(int claimIndex, String response);

    void addClaim(VoyaClaim claim);
}