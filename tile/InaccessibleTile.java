package tile;

import static utils.ColouredOutputs.*;

public class InaccessibleTile extends Tile {
    // represents an inaccessible tile
    // Constructor
    public InaccessibleTile() {
    }

    @Override
    public String toString() {
        return "   ";
    }
}