package tile.QoLTiles;

import tile.Tile;

import static utils.ColouredOutputs.*;

// A class for the plain tile in QoL
public class PlainTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public PlainTile() {
        super();
    }
    @Override
    public String toString() {
        return ANSI_BLACK + ANSI_BRIGHT_BG_YELLOW + super.toString() +ANSI_RESET;
    }
}
