package character.items.spells;

import static utils.ColouredOutputs.ANSI_BLUE;
import static utils.ColouredOutputs.ANSI_RESET;

public class Lightning extends Spell {
    public Lightning(String name, double price, int reqLevel, double damage, double mana){
        super(name, price, reqLevel, damage, mana);
    }

    @Override
    public String toString() {
        return  ANSI_BLUE + super.toString() + ANSI_RESET;
    }
}
