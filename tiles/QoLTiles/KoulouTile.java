package tiles.QoLTiles;

import static src.util.ColouredOutputs.*;

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
