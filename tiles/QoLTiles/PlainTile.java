package tiles.QoLTiles;

import static questOfLegends.QoLDefaults.PLAIN_TILE_COLOR;
import static src.util.ColouredOutputs.*;

// A class for the plain tile in QoL
public class PlainTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public PlainTile() {
        super();
    }
    @Override
    public String toString() {
        return  PLAIN_TILE_COLOR + super.toString() +ANSI_RESET;
    }
}
