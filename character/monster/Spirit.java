package character.monster;

import static utils.ColouredOutputs.ANSI_BRIGHT_CYAN;
import static utils.ColouredOutputs.ANSI_RESET;

public class Spirit extends Monster {
    public Spirit( String name, int expLevel, double damage, double defense, double dodgeChance){
        super(name, expLevel, damage, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return ANSI_BRIGHT_CYAN + super.toString() + ANSI_RESET;
    }
}
