package character.items.potions;

import static util.ColouredOutputs.ANSI_RESET;
import static character.CharacterDefaults.AGILITY_POTION_COLOR;

public class Agility extends Potion{
    public Agility(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel,effect);
    }

    @Override
    public String toString() {
        return (AGILITY_POTION_COLOR +
                super.toString() +
                this.getClass().getSimpleName() + " increased by " + this.getEffect() + "\n" +
                ANSI_RESET);
    }
}

