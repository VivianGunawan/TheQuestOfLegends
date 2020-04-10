package tile;

import static utils.ColouredOutputs.*;

public class CaveTile extends Tile {

    public CaveTile(){
    }

    // temporary cave tile toString
    @Override
    public String toString() {
        return (ANSI_BRIGHT_BG_BLACK + ANSI_BG_RED + " C " + ANSI_RESET);
    }
}
