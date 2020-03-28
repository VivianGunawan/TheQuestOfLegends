package character.items.spells;

import character.items.Item;
import character.items.ItemType;
import static utils.ColouredOutputs.ANSI_RESET;
import static utils.ColouredOutputs.ANSI_BLUE;
import static utils.ColouredOutputs.ANSI_RED;
import static utils.ColouredOutputs.ANSI_CYAN;


public class Spell extends Item {
    // represents a spell could be of different spell types, ice, fire, lighting
    // Fields
    private SpellType type;
    private double damage;
    private double mana;
    // Constructor 
    public Spell(String type, String name, double price, int reqLevel, double damage, double mana){
        super(ItemType.SPELL,name, price, reqLevel);
        this.type  = SpellType.valueOf(type);
        this.damage = damage;
        this.mana = mana;
    }

    public SpellType getType() {
        return type;
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
            this.type.name() + " " +
            super.toString() +
            "Damage Range: " + this.damage+ "\n" +
            "Mana Required: " + this.mana + "\n" 
        );
        switch (this.type){
            case ICE:
                out = ANSI_CYAN + out + ANSI_RESET;
                break;
            case LIGHTNING:
                out = ANSI_BLUE + out + ANSI_RESET;
                break;
            case FIRE:
                out = ANSI_RED + out +ANSI_RESET;
        }
        return out;
    }
}