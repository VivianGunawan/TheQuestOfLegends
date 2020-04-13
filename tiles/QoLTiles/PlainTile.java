package tiles.QoLTiles;

import static tiles.QoLTiles.QoLTilesDefaults.PLAIN_TILE_COLOR;
import static util.ColouredOutputs.ANSI_RESET;

// A class for the plain tile in QoL
public class PlainTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public PlainTile() {
        super();
    }
    @Override
    public String toString() {
        return  PLAIN_TILE_COLOR + super.toString() + ANSI_RESET;
    }
}
