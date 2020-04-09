package character.items.potions;

import character.items.Item;

import static utils.ColouredOutputs.ANSI_BRIGHT_PURPLE;
import static utils.ColouredOutputs.ANSI_RESET;


public abstract class Potion extends Item {
    // Used by a hero in order to increase one of their statistics by some amount
    // Fields
    double effect;
    // Constructor
    public Potion(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel);
        this.effect = effect;
    }

    public double getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}