package character.items.potions;

import static utils.ColouredOutputs.ANSI_BRIGHT_RED;
import static utils.ColouredOutputs.ANSI_RESET;

public class Strength extends Potion{
    public Strength(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel,effect);
    }

    @Override
    public String toString() {
        return (ANSI_BRIGHT_RED +
                super.toString() +
                this.getClass().getSimpleName() + " increased by " + this.getEffect() + "\n" +
                ANSI_RESET);
    }
}

