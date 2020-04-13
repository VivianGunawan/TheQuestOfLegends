package character.items.spells;


import static util.ColouredOutputs.ANSI_RESET;
import static character.CharacterDefaults.ICE_SPELL_COLOR;

public class Ice extends Spell {
    public Ice(String name, double price, int reqLevel, double damage, double mana){
        super(name, price, reqLevel, damage, mana);
    }

    @Override
    public String toString() {
        return ICE_SPELL_COLOR + super.toString() + ANSI_RESET;
    }
}
