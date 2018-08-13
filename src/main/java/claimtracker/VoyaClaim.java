package claimtracker;

import java.util.ArrayList;
import java.util.List;

public class VoyaClaim {
    String text;
    List<VoyaNIGOEvent> nigoEvents;
    int nigoCounter;

    public VoyaClaim(String text) {
        this.text = text;
        this.nigoEvents = new ArrayList<VoyaNIGOEvent>();
        nigoCounter = 0;
    }
    public void addNIGOEvent(VoyaNIGOEvent e) {
        this.nigoEvents.add(e);
    }

    public String listNIGOEvents() {
        StringBuilder sb = new StringBuilder();
        for(VoyaNIGOEvent n: this.nigoEvents) {
            sb.append(n.getText() + ",\n");
        }
        return sb.toString();
    }

    public VoyaNIGOEvent getNIGOEvent(int index) throws IndexOutOfBoundsException {
        return this.nigoEvents.get(index);
    }

    public int getNumNIGOEvents() {
        return this.nigoEvents.size();
    }

    public String getNextNIGOEvent() {
        try {
            String out = this.getNIGOEvent(nigoCounter).getText();
            nigoCounter ++;
            return out;
        }
        catch(IndexOutOfBoundsException e) {
            return "There are no more NIGO events";
        }
    }

    public String getText() {
        return this.text;
    }
}
