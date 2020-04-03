package character.items.spells;

import character.items.Item;


public abstract class Spell extends Item {
    // represents a spell could be of different spell types, ice, fire, lighting
    // Fields
    private double damage;
    private double mana;
    // Constructor 
    public Spell(String name, double price, int reqLevel, double damage, double mana){
        super(name, price, reqLevel);
        this.damage = damage;
        this.mana = mana;
    }
    public double getDamage() {
        return damage;
    }

    public double getMana() {
        return mana;
    }

    @Override
    public String toString(){
        String out =(
            super.toString() +
            "Damage Range: " + this.damage+ "\n" +
            "Mana Required: " + this.mana + "\n" 
        );
        return out;
    }
}