package tiles.QoLTiles;

import static questOfLegends.QoLDefaults.BUSH_TILE_COLOR;
import static src.util.ColouredOutputs.*;

public class BushTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public BushTile() {
        super();
    }

    @Override
    public String toString() {
        return BUSH_TILE_COLOR + super.toString() +ANSI_RESET;
    }
}
