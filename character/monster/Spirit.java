package character.monster;

import static util.ColouredOutputs.ANSI_RESET;
import static character.CharacterDefaults.SPIRIT_COLOR;

public class Spirit extends Monster {
    public Spirit( String name, int expLevel, double damage, double defense, double dodgeChance){
        super(name, expLevel, damage, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return SPIRIT_COLOR + super.toString() + ANSI_RESET;
    }
}
