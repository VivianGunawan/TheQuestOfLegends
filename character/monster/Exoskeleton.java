package character.monster;

import static util.ColouredOutputs.ANSI_RESET;
import static character.monster.MonsterDefaults.EXOSKELETON_COLOR;

public class Exoskeleton extends Monster {
    public Exoskeleton( String name, int expLevel, double damage, double defense, double dodgeChance){
        super(name, expLevel, damage, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return EXOSKELETON_COLOR + super.toString() + ANSI_RESET;
    }
}
