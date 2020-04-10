package tile;

import static utils.ColouredOutputs.*;

public class CaveTile extends Tile {

    public CaveTile(){
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
