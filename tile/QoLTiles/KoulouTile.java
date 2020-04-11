package tile.QoLTiles;

import tile.Tile;

import static utils.ColouredOutputs.*;

public class KoulouTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public KoulouTile() {
        super();
    }
    @Override
    public String toString() {
        return ANSI_BLACK + ANSI_BRIGHT_BG_RED+ super.toString() +ANSI_RESET;
    }
}
