package character.monster;

import static src.util.ColouredOutputs.ANSI_BRIGHT_GREEN;
import static src.util.ColouredOutputs.ANSI_RESET;

public class Dragon extends Monster {
    public Dragon( String name, int expLevel, double damage, double defense, double dodgeChance){
        super(name, expLevel, damage, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return ANSI_BRIGHT_GREEN + super.toString() + ANSI_RESET;
    }
}
