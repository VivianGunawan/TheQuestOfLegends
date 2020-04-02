package character.hero;

import static utils.ColouredOutputs.ANSI_BRIGHT_BLUE;
import static utils.ColouredOutputs.ANSI_RESET;
import static utils.Defaults.SKILLS_MULTIPLIER;

public class Warrior extends Hero {
    // Constructor
    public Warrior(String name, int experience, double mana, double strength, double agility, double dexterity, double money, int hands){
        super(name, experience, mana, strength, agility, dexterity, money,hands);
    }

    @Override
    public String toString() {
        return ANSI_BRIGHT_BLUE + super.toString() + ANSI_RESET;
    }

    @Override
    public String battleDisplay() {
        return ANSI_BRIGHT_BLUE + super.battleDisplay() + ANSI_RESET;
    }

    @Override
    protected void levelUp() {
        super.levelUp();
        this.setStrength(this.getStrength() + (this.getStrength() * SKILLS_MULTIPLIER));
        this.setAgility(this.getAgility() + (this.getAgility() * SKILLS_MULTIPLIER));
    }
}