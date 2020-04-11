package character.items.potions;

import static src.util.ColouredOutputs.ANSI_BRIGHT_PURPLE;
import static src.util.ColouredOutputs.ANSI_RESET;

public class Dexterity extends Potion{
    public Dexterity(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel,effect);
    }

    @Override
    public String toString() {
        return (ANSI_BRIGHT_PURPLE +
                super.toString() +
                this.getClass().getSimpleName() + " increased by " + this.getEffect() + "\n" +
                ANSI_RESET);
    }
}
