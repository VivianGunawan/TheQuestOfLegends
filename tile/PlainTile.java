package tile;

import static utils.ColouredOutputs.*;

// A class for the plain tile in QoL
public class PlainTile extends Tile {

    private boolean containsMonster;
    // Constructor
    public PlainTile() {
    }

    public void setContainsMonster(boolean monster) {
        containsMonster = monster;
    }

    public boolean containsMonster() {
        return this.containsMonster;
    }

    // temporary plain tile toString
    @Override
    public String toString() {
        if (this.isActive()) {
            if(this.getLocation() < 10) {
                return (ANSI_BLACK + ANSI_BRIGHT_BG_GREEN + this.getLocation() + " *"  + ANSI_RESET);
            } else {
                return (ANSI_BLACK + ANSI_BRIGHT_BG_GREEN + this.getLocation() + "*"  + ANSI_RESET);
            }
        } else {
            if (this.getLocation() < 10) {
                return ANSI_BLACK + ANSI_BRIGHT_BG_GREEN + " " + this.getLocation() + " " + ANSI_RESET;
            } else {
                return ANSI_BLACK + ANSI_BRIGHT_BG_GREEN + this.getLocation() + " " + ANSI_RESET;
            }
        }
    }
}
