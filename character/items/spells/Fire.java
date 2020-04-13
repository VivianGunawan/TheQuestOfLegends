package character.items.spells;


import static util.ColouredOutputs.ANSI_RESET;
import static character.CharacterDefaults.FIRE_SPELL_COLOR;

public class Fire extends Spell {
    public Fire(String name, double price, int reqLevel, double damage, double mana){
        super(name, price, reqLevel, damage, mana);
    }

    @Override
    public String toString() {
        return FIRE_SPELL_COLOR + super.toString() +ANSI_RESET;
    }
}
