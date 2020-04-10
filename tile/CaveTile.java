package tile;

import static utils.ColouredOutputs.*;

public class CaveTile extends Tile {

    private boolean containsMonster;

    public CaveTile(){
    }

    public void setContainsMonster(boolean containsMonster) {
        this.containsMonster = containsMonster;
    }

    public boolean containsMonster() {
        return containsMonster;
    }

    // temporary cave tile toString
    @Override
    public String toString() {
        if (this.isActive()) {
            if(this.getLocation() < 10) {
                return (ANSI_BLACK + ANSI_BRIGHT_BG_BLUE + this.getLocation() + " *"  + ANSI_RESET);
            } else {
                return (ANSI_BLACK + ANSI_BRIGHT_BG_BLUE + this.getLocation() + "*"  + ANSI_RESET);
            }
        } else {
            if (this.getLocation() < 10) {
                return ANSI_BLACK + ANSI_BRIGHT_BG_BLUE + " " + this.getLocation() + " " + ANSI_RESET;
            } else {
                return ANSI_BLACK + ANSI_BRIGHT_BG_BLUE + this.getLocation() + " " + ANSI_RESET;
            }
        }
    }
}
