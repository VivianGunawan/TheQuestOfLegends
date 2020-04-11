package tiles.QoLTiles;

import static src.util.ColouredOutputs.*;

public class BushTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public BushTile() {
        super();
    }

    @Override
    public String toString() {
        return ANSI_BLACK + ANSI_BRIGHT_BG_GREEN + super.toString() +ANSI_RESET;
    }
}
