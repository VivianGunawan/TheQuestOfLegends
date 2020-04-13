package tiles.QoLTiles;

import static tiles.QoLTiles.QoLTilesDefaults.CAVE_TILE_COLOR;
import static util.ColouredOutputs.ANSI_RESET;

public class CaveTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public CaveTile() {
        super();
    }
    @Override
    public String toString() {
        return CAVE_TILE_COLOR + super.toString() + ANSI_RESET;
    }
}