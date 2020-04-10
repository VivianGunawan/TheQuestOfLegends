package tile;

import static utils.ColouredOutputs.*;

public class BushTile extends Tile {

    public BushTile(){
    }

    // temporary bush tile toString
    @Override
    public String toString() {
        return (ANSI_BRIGHT_BG_BLACK + ANSI_BG_RED + " B " + ANSI_RESET);
    }
}
