package character.items.potions;

import static src.util.ColouredOutputs.ANSI_BRIGHT_YELLOW;
import static src.util.ColouredOutputs.ANSI_RESET;

public class Agility extends Potion{
    public Agility(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel,effect);
    }

    @Override
    public String toString() {
        return (ANSI_BRIGHT_YELLOW +
                super.toString() +
                this.getClass().getSimpleName() + " increased by " + this.getEffect() + "\n" +
                ANSI_RESET);
    }
}

