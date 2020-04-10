package tile;

import static utils.ColouredOutputs.*;

public class BushTile extends Tile {
    private boolean containsMonster;

    public BushTile(){
    }

    public void setContainsMonster(boolean containsMonster) {
        this.containsMonster = containsMonster;
    }

    public boolean containsMonster() {
        return containsMonster;
    }

    // temporary bush tile toString
    @Override
    public String toString() {
        if (this.isActive()) {
            if(this.getLocation() < 10) {
                return (ANSI_BLACK + ANSI_BRIGHT_BG_PURPLE + this.getLocation() + " *"  + ANSI_RESET);
            } else {
                return (ANSI_BLACK + ANSI_BRIGHT_BG_PURPLE + this.getLocation() + "*"  + ANSI_RESET);
            }
        } else {
            if (this.getLocation() < 10) {
                return ANSI_BLACK + ANSI_BRIGHT_BG_PURPLE + " " + this.getLocation() + " " + ANSI_RESET;
            } else {
                return ANSI_BLACK + ANSI_BRIGHT_BG_PURPLE + this.getLocation() + " " + ANSI_RESET;
            }
        }
    }
}
