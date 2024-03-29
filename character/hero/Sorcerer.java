package character.hero;

import static character.hero.HeroDefaults.SKILLS_MULTIPLIER;

import static util.ColouredOutputs.ANSI_RESET;
import static character.hero.HeroDefaults.SORCERER_COLOR;


public class Sorcerer extends Hero{
    // Constructor
    public Sorcerer(String name, int experience, double strength, double agility, double dexterity, double money, int hands){
        super(name, experience, strength, agility, dexterity, money,hands);
    }

    @Override
    public String toString() {
        return SORCERER_COLOR + super.toString() + ANSI_RESET;
    }

    @Override
    public String battleDisplay() {
        return SORCERER_COLOR+ super.battleDisplay() + ANSI_RESET;
    }
    @Override
    protected void levelUp() {
        super.levelUp();
        this.setDexterity(this.getDexterity() + (this.getDexterity() * SKILLS_MULTIPLIER));
        this.setAgility(this.getAgility() + (this.getAgility() * SKILLS_MULTIPLIER));
    }
}
