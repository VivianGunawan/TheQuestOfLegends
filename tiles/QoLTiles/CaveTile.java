package tiles.QoLTiles;

import static questOfLegends.QoLDefaults.CAVE_TILE_COLOR;
import static src.util.ColouredOutputs.*;

public class CaveTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public CaveTile() {
        super();
    }
    @Override
    public String toString() {
        return CAVE_TILE_COLOR + super.toString() +ANSI_RESET;
    }
}