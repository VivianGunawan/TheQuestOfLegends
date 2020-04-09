package character.items.potions;

import static utils.ColouredOutputs.ANSI_BRIGHT_BLUE;
import static utils.ColouredOutputs.ANSI_RESET;

public class Mana extends Potion{
    public Mana(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel,effect);
    }

    @Override
    public String toString() {
        return (ANSI_BRIGHT_BLUE +
                super.toString() +
                this.getClass().getSimpleName() + " increased by " + this.getEffect() + "\n" +
                ANSI_RESET);
    }
}

