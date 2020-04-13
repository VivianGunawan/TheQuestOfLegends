package character.hero;

import static character.CharacterDefaults.SKILLS_MULTIPLIER;
import static character.CharacterDefaults.PALADIN_COLOR;
import static util.ColouredOutputs.ANSI_RESET;


public class Paladin extends Hero {
    // Constructor
    public Paladin(String name, int experience, double mana, double strength, double agility, double dexterity, double money, int hands){
        super(name, experience, mana, strength, agility, dexterity, money,hands);
    }
    @Override
    public String toString() {
        return PALADIN_COLOR + super.toString() + ANSI_RESET;
    }

    @Override
    public String battleDisplay() {
        return PALADIN_COLOR+ super.battleDisplay() + ANSI_RESET;
    }

    @Override
    protected void levelUp() {
        super.levelUp();
        this.setStrength(this.getStrength() + (this.getStrength() * SKILLS_MULTIPLIER));
        this.setDexterity(this.getDexterity() + (this.getDexterity() * SKILLS_MULTIPLIER));
    }
}
