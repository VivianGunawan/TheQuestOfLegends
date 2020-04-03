package character.items.armors;

import character.items.Item;

import static utils.ColouredOutputs.ANSI_GREEN;
import static utils.ColouredOutputs.ANSI_RESET;

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
            ANSI_GREEN +
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
            return( ANSI_GREEN +
                    "Name: " + this.getName() + "\n" +
                    "Damaged Reduced: " + this.damageReduction + "\n" +
                    ANSI_RESET);
        }
    }
}