package tiles.QoLTiles;

import static src.util.ColouredOutputs.*;

public class CaveTile extends BattleTile{

    private boolean containsMonster;
    // Constructor
    public CaveTile() {
        super();
    }
    @Override
    public String toString() {
        return ANSI_BLACK + ANSI_BRIGHT_BG_PURPLE + super.toString() +ANSI_RESET;
    }
}