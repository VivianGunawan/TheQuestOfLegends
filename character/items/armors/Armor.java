package character.items.armors;

import character.items.Item;

import static util.ColouredOutputs.ANSI_RESET;
import static character.items.ItemDefaults.ARMOR_COLOR;

public class Armor extends Item {
    // when worn by hero, reduces incoming damage from enemy's attack
    // Field
    private double damageReduction;

    //Constructor
    public Armor(String name, double price, int reqLevel, double damageReduction){
        super(name, price, reqLevel);
        this.damageReduction = damageReduction;
    }

    public double getDamageReduction() {
        return damageReduction;
    }

    // Default display in market
    @Override
    public String toString() {
        return (
            ARMOR_COLOR +
            super.toString() + 
            "Damage Reduced: " + this.damageReduction + "\n" +
            ANSI_RESET
        );
    }
    public String battleDisplay(){
        if(this.getName()==null){
            return "none";
        }
        else{
            return( ARMOR_COLOR +
                    "Name: " + this.getName() + "\n" +
                    "Damaged Reduced: " + this.damageReduction + "\n" +
                    ANSI_RESET);
        }
    }
}