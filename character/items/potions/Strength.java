package character.items.potions;


import static util.ColouredOutputs.ANSI_RESET;
import static character.items.ItemDefaults.STRENGTH_POTION_COLOR;

public class Strength extends Potion{
    public Strength(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel,effect);
    }

    @Override
    public String toString() {
        return (STRENGTH_POTION_COLOR +
                super.toString() +
                this.getClass().getSimpleName() + " increased by " + this.getEffect() + "\n" +
                ANSI_RESET);
    }
}

