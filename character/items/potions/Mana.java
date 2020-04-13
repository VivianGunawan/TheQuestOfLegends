package character.items.potions;

import static util.ColouredOutputs.ANSI_RESET;
import static character.CharacterDefaults.MANA_POTION_COLOR;

public class Mana extends Potion{
    public Mana(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel,effect);
    }

    @Override
    public String toString() {
        return (MANA_POTION_COLOR +
                super.toString() +
                this.getClass().getSimpleName() + " increased by " + this.getEffect() + "\n" +
                ANSI_RESET);
    }
}

