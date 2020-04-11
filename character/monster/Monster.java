package character.monster;

import character.AttackResult;
import character.MonsterBattle;
import character.items.spells.Fire;
import character.items.spells.Ice;
import character.items.spells.Lightning;
import character.items.spells.Spell;

import static quest.QuestDefaults.SKILL_DETERIORATION;


public abstract class Monster extends character.Character implements MonsterBattle {
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
                if(spell instanceof Ice){
                    this.damage -= (SKILL_DETERIORATION * this.damage);
                }
                else if (spell instanceof Fire){
                    this.defense -= (SKILL_DETERIORATION * this.defense);
                }
                else if(spell instanceof Lightning){
                    this.defense -= (SKILL_DETERIORATION * this.defense);
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
}