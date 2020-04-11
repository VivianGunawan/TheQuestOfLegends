package tile.QoLTiles;

import tile.Tile;

import static utils.ColouredOutputs.*;
import static utils.ColouredOutputs.ANSI_RESET;

// Represents a battle tile in the QoL game
// In every battle tile
    // - no one
    // - one hero
    // - one monster
    // - one hero and one monster
    // - NEVER two heroes and two monsters
public abstract class BattleTile extends Tile {
    // Fields
    private boolean containsMonster;
    // Constructor
    public BattleTile(){
        super();
        this.containsMonster = false;
    }
    //Mutator
    public void setContainsMonster(boolean containsMonster) {
        this.containsMonster = containsMonster;
    }
    //Accessor
    public boolean isContainsMonster() {
        return containsMonster;
    }

    @Override
    public String toString() {
        if (this.isContainsMonster()) {
            if(this.getLocation() < 10) {
                return (this.getLocation() + " " + super.toString() +  "+");
            } else {
                return (this.getLocation() + super.toString() +  "+");
            }
        } else {
            if (this.getLocation() < 10) {
                return (this.getLocation() + " " + super.toString() + " ");
            } else {
                return (this.getLocation() + super.toString() + " ");
            }
        }
    }
}
