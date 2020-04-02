package character.monster;

import static utils.ColouredOutputs.ANSI_BRIGHT_YELLOW;
import static utils.ColouredOutputs.ANSI_RESET;

public class Exoskeleton extends Monster {
    public Exoskeleton( String name, int expLevel, double damage, double defense, double dodgeChance){
        super(name, expLevel, damage, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return ANSI_BRIGHT_YELLOW + super.toString() + ANSI_RESET;
    }
}
