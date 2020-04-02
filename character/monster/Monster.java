package character.monster;

import character.AttackResult;
import character.Battle;
import character.items.spells.Spell;

import static utils.ColouredOutputs.*;
import static utils.ColouredOutputs.ANSI_RESET;
import static utils.Defaults.*;

public abstract class Monster extends character.Character implements Battle {
    //represents a monster in the game could be a dragon, exoskeleton or spirit.
    //Fields
    private double damage;
    private double defense;
    private double dodgeChance;

    //Constructor
    public Monster(String name, int expLevel, double damage, double defense, double dodgeChance) {
        super(name, expLevel);
        this.damage = damage;
        this.defense = defense;
        this.dodgeChance = dodgeChance;
    }
    // Interface methods

    @Override
    public String toString() {
        String out =  this.getClass().getSimpleName()  + "\n" +
                        super.toString() +
                        "Damage: " + this.damage + "\n" +
                        "Defense:" + this.defense + "\n" +
                        "Dodge Chance: " + this.dodgeChance + "\n";
        return out;
    }
    // Battle interface methods
    public AttackResult receiveBasicAttack(double damage) {
        if (this.getHealthPower()==0){
            return AttackResult.DEAD;
        }
        else{
            if( Math.random() < this.dodgeChance){
                return AttackResult.DODGE;
            }
            else{
                double effectiveDamage = 0;
                if(damage - this.defense>0){
                    effectiveDamage = damage - this.defense;
                }
                if(this.getHealthPower()-(effectiveDamage)<=0){
                    this.setHealthPower(0);
                    return  AttackResult.KILL;
                }
                else{
                    this.setHealthPower(this.getHealthPower()-(effectiveDamage));
                    System.out.print(this.getName() + " received " + effectiveDamage + " damage");
                }
                return AttackResult.SUCCESS;
            }
        }
    }
    @Override
    public AttackResult receiveSpell(Spell spell, double spellDamage) {
        if(this.getHealthPower()==0){
            return  AttackResult.DEAD;
        }
        else{
            if( Math.random() < this.dodgeChance){
                return AttackResult.DODGE;
            }
            else {
                switch (spell.getType()){
                    case ICE:
                        // reduce damage
                        this.damage -= (SKILL_DETERIORATION * this.damage);
                        break;
                    case FIRE:
                        // reduce defense
                        this.defense -= (SKILL_DETERIORATION * this.defense);
                        break;
                    case LIGHTNING:
                        // reduce dodgeChance
                        this.dodgeChance -= (SKILL_DETERIORATION * this.dodgeChance);
                        break;
                }
                double effectiveDamage = 0;
                if(spellDamage - this.defense>0){
                    effectiveDamage = spellDamage - this.defense;
                }
                if(this.getHealthPower()-(effectiveDamage)<=0){
                    this.setHealthPower(0);
                    return  AttackResult.KILL;
                }
                else{
                    this.setHealthPower(this.getHealthPower()-(effectiveDamage));
                    System.out.print(this.getName() + " received " + effectiveDamage + " damage");
                }
                return AttackResult.SUCCESS;
            }
        }
    }
    @Override
    public double doBasicAttack() {
        return this.damage;
    }
    // NO OPERATION
    public Spell castSpell() {
        // NO OPERATIONS
        // monsters don't cast spells to heroes
        return null;
    }
    @Override
    public double castSpellDamage(Spell spell) {
        // NO OPERATIONS
        // monsters don't cast spells to heroes
        return 0;
    }
    @Override
    public void regen() {
        // NO OPERATION
        return;
    }
}