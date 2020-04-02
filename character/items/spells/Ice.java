package character.items.spells;

import static utils.ColouredOutputs.ANSI_CYAN;
import static utils.ColouredOutputs.ANSI_RESET;

public class Ice extends Spell {
    public Ice(String name, double price, int reqLevel, double damage, double mana){
        super(name, price, reqLevel, damage, mana);
    }

    @Override
    public String toString() {
        return ANSI_CYAN + super.toString() + ANSI_RESET;
    }
}
