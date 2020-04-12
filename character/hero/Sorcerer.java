package character.hero;

import static character.CharacterDefaults.SKILLS_MULTIPLIER;
import static src.util.ColouredOutputs.ANSI_BRIGHT_PURPLE;
import static src.util.ColouredOutputs.ANSI_RESET;


public class Sorcerer extends Hero{
    // Constructor
    public Sorcerer(String name, int experience, double mana, double strength, double agility, double dexterity, double money, int hands){
        super(name, experience, mana, strength, agility, dexterity, money,hands);
    }

    @Override
    public String toString() {
        return ANSI_BRIGHT_PURPLE + super.toString() + ANSI_RESET;
    }

    @Override
    public String battleDisplay() {
        return ANSI_BRIGHT_PURPLE + super.battleDisplay() + ANSI_RESET;
    }
    @Override
    protected void levelUp() {
        super.levelUp();
        this.setDexterity(this.getDexterity() + (this.getDexterity() * SKILLS_MULTIPLIER));
        this.setAgility(this.getAgility() + (this.getAgility() * SKILLS_MULTIPLIER));
    }
}
