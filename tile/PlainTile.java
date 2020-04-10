package tile;

import static utils.ColouredOutputs.*;

// A class for the plain tile in QoL
public class PlainTile extends Tile {

    // Constructor
    public PlainTile() {
    }

    // temporary plain tile toString
    @Override
    public String toString() {
        return (ANSI_BRIGHT_BG_BLACK + ANSI_BG_RED + " P " + ANSI_RESET);
    }
}
