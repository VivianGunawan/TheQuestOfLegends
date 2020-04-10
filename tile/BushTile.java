package tile;

import static utils.ColouredOutputs.*;

public class BushTile extends Tile {

    public BushTile(){
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
