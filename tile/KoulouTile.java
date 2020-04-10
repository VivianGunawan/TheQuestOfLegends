package tile;

import static utils.ColouredOutputs.*;

public class KoulouTile extends Tile {

    public KoulouTile() {
    }

    // temporary kpulou tile toString
    @Override
    public String toString() {
        return (ANSI_BRIGHT_BG_BLACK + ANSI_BG_RED + " K " + ANSI_RESET);
    }
}
