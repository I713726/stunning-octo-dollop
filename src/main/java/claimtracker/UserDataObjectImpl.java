package claimtracker;

import java.util.List;

public class UserDataObjectImpl implements VoyaUserDataObject {

    List<VoyaClaim> claims;

    @Override
    public String listClaims() {
        StringBuilder sb = new StringBuilder();
        for(VoyaClaim s: claims) {
            sb.append(s.getText() + "\n");
        }
        return sb.toString();
    }

    @Override
    public String presentClaim(int index) throws IndexOutOfBoundsException {
        return claims.get(index).getText();
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
    public String getNextNIGOEvent(int claimIndex) {
        //TODO: Still figuring out how to handle this
        return claims.get(claimIndex).getNextNIGOEvent();
    }

    @Override
    public void respondToCurrentNIGOEvent(int claimIndex) {
        //Not sure how this is gonna work yet
    }
}