package tiles.QoLTiles;

import static tiles.QoLTiles.QoLTilesDefaults.BUSH_TILE_COLOR;
import static util.ColouredOutputs.ANSI_RESET;

public class BushTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public BushTile() {
        super();
    }

    @Override
    public String toString() {
        return BUSH_TILE_COLOR + super.toString() + ANSI_RESET;
    }
}
