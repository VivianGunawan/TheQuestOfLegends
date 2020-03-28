package tile;

import static utils.ColouredOutputs.*;

public class InaccessibleTile extends Tile {
    // represents an inaccesible tile
    // Constructor
    public InaccessibleTile() {
        super(TileType.I);
    }

    @Override
    public String toString() {
        return ANSI_BRIGHT_BLACK + ANSI_BRIGHT_BG_BLACK + "   " + ANSI_RESET;
    }
}