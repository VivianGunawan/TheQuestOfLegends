package character.items.potions;

import character.items.Item;
import character.items.ItemType;
import static utils.ColouredOutputs.ANSI_BRIGHT_PURPLE;
import static utils.ColouredOutputs.ANSI_RESET;


public class Potion extends Item {
    // Used by a hero in order to increase one of their statistics by some amount
    // Fields
    PotionType type;
    double effect;
    // Constructor
    public Potion(String name, double price, int reqLevel, String type, double effect){
        super(ItemType.POTION,name, price, reqLevel);
        this.type = PotionType.valueOf(type);
        this.effect = effect;
    }

    public PotionType getType() {
        return type;
    }

    public double getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return(
            ANSI_BRIGHT_PURPLE +
            super.toString() + 
            this.type + " increased by " + this.effect + "\n" +
            ANSI_RESET
            );
    }
}