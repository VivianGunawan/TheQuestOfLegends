package tiles.QoLTiles;

import static questOfLegends.QoLDefaults.KOULOU_TILE_COLOR;
import static src.util.ColouredOutputs.*;

public class KoulouTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public KoulouTile() {
        super();
    }
    @Override
    public String toString() {
        return  KOULOU_TILE_COLOR + super.toString() +ANSI_RESET;
    }
}
