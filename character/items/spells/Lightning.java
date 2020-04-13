package character.items.spells;

import static util.ColouredOutputs.ANSI_RESET;
import static character.items.ItemDefaults.LIGHTNING_SPELL_COLOR;

public class Lightning extends Spell {
    public Lightning(String name, double price, int reqLevel, double damage, double mana){
        super(name, price, reqLevel, damage, mana);
    }

    @Override
    public String toString() {
        return  LIGHTNING_SPELL_COLOR + super.toString() + ANSI_RESET;
    }
}
