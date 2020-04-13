package character.monster;

import static util.ColouredOutputs.ANSI_RESET;
import static character.CharacterDefaults.DRAGON_COLOR;

public class Dragon extends Monster {
    public Dragon( String name, int expLevel, double damage, double defense, double dodgeChance){
        super(name, expLevel, damage, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return DRAGON_COLOR + super.toString() + ANSI_RESET;
    }
}
