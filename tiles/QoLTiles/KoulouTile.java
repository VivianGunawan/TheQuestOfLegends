package tiles.QoLTiles;

import static tiles.QoLTiles.QoLTilesDefaults.KOULOU_TILE_COLOR;
import static util.ColouredOutputs.ANSI_RESET;

public class KoulouTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public KoulouTile() {
        super();
    }
    @Override
    public String toString() {
        return  KOULOU_TILE_COLOR + super.toString() + ANSI_RESET;
    }
}
