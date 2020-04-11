package character.items.spells;

import static src.util.ColouredOutputs.ANSI_RED;
import static src.util.ColouredOutputs.ANSI_RESET;

public class Fire extends Spell {
    public Fire(String name, double price, int reqLevel, double damage, double mana){
        super(name, price, reqLevel, damage, mana);
    }

    @Override
    public String toString() {
        return ANSI_RED + super.toString() +ANSI_RESET;
    }
}
