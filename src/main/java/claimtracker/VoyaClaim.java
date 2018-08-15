package claimtracker;

import java.util.ArrayList;
import java.util.List;

public class VoyaClaim {
    private String text;
    private List<VoyaNIGOEvent> nigoEvents;
    private int nigoCounter;
    private VoyaClaimType claimType;
    private VoyaClaimState state;

    public VoyaClaim(String text, VoyaClaimType type, VoyaClaimState state) {
        this.text = text;
        this.nigoEvents = new ArrayList<VoyaNIGOEvent>();
        nigoCounter = 0;
        this.claimType = type;
        this.state = state;
    }
    public void addNIGOEvent(VoyaNIGOEvent e) {
        this.nigoEvents.add(e);
    }

    public List<VoyaNIGOEvent> getNigoEvents() {
        return new ArrayList<>(this.nigoEvents);
    }

    public VoyaNIGOEvent getNIGOEvent(int index) throws IndexOutOfBoundsException {
        return this.nigoEvents.get(index);
    }

    public int getNumNIGOEvents() {
        return this.nigoEvents.size();
    }

    public String getText() {
        return this.text;
    }

    public VoyaClaimType getClaimType() {
        return claimType;
    }

    public VoyaClaimState getState() {
        return this.state;
    }

    public int getNumFixableNIGOEvents() {
        int count = 0;
        for(VoyaNIGOEvent e : this.nigoEvents) {
            if(e.getFixable()) {
                count ++;
            }
        }
        return count;
    }
}
