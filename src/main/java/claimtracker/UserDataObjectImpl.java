package claimtracker;

import java.util.ArrayList;
import java.util.List;

public class UserDataObjectImpl implements VoyaUserDataObject {

    private List<VoyaClaim> claims;

    public UserDataObjectImpl() {
        this.claims = new ArrayList<VoyaClaim>();
    }

    @Override
    public List<VoyaClaim> getClaims() {
        return new ArrayList<>(this.claims);
    }

    @Override
    public VoyaClaim getClaim(int index) throws IndexOutOfBoundsException {
        return claims.get(index);
    }

    @Override
    public String getFirstName() {
        return "John";
    }

    @Override
    public String getLastName() {
        return "Doe";
    }

    @Override
    public VoyaNIGOEvent getNextNIGOEvent(int claimIndex) {
        //TODO: Still figuring out how to handle this
        return claims.get(claimIndex).getNextNIGOEvent();
    }

    @Override
    public VoyaNIGOEvent peekNextNIGOEvent(int claimIndex) {
        return claims.get(claimIndex).peekNextNIGOEvent();
    }

    @Override
    public List<VoyaNIGOEvent> getNIGOEvents(int claimIndex) {
        return claims.get(claimIndex).getNigoEvents();
    }

    @Override
    public void respondToCurrentNIGOEvent(int claimIndex, String response) {
        //Not sure how this is gonna work yet
    }

    public void addClaim(VoyaClaim claim) {
        this.claims.add(claim);
    }
}