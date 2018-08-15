package claimtracker;

import java.util.List;

public interface VoyaUserDataObject {

    List<VoyaClaim> getClaims();

    VoyaClaim getClaim(int index);

    String getFirstName();

    String getLastName();

    List<VoyaNIGOEvent> getNIGOEvents(int claimIndex);

    void respondToCurrentNIGOEvent(int claimIndex, String response);

    void addClaim(VoyaClaim claim);
}