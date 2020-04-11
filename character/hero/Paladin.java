package character.hero;

import static quest.QuestDefaults.SKILLS_MULTIPLIER;
import static src.util.ColouredOutputs.ANSI_BRIGHT_RED;
import static src.util.ColouredOutputs.ANSI_RESET;


public class Paladin extends Hero {
    // Constructor
    public Paladin(String name, int experience, double mana, double strength, double agility, double dexterity, double money, int hands){
        super(name, experience, mana, strength, agility, dexterity, money,hands);
    }
    @Override
    public String toString() {
        return ANSI_BRIGHT_RED + super.toString() + ANSI_RESET;
    }

    @Override
    public String battleDisplay() {
        return ANSI_BRIGHT_RED + super.battleDisplay() + ANSI_RESET;
    }

    @Override
    protected void levelUp() {
        super.levelUp();
        this.setStrength(this.getStrength() + (this.getStrength() * SKILLS_MULTIPLIER));
        this.setDexterity(this.getDexterity() + (this.getDexterity() * SKILLS_MULTIPLIER));
    }
}
