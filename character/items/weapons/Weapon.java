package character.items.weapons;

import character.items.Item;
import character.items.ItemType;
import static utils.ColouredOutputs.ANSI_BRIGHT_WHITE;
import static utils.ColouredOutputs.ANSI_RESET;

public class Weapon extends Item {
    // Used by hero to attack a monster
    // Fields
    double damage;
    int reqHands;
    // Constructor
    public Weapon(String name, double price, int reqLevel, double damage, int reqHands){
        super(ItemType.WEAPON,name, price, reqLevel);
        this.damage = damage;
        this.reqHands = reqHands;
    }

    public double getDamage() {
        return damage;
    }

    public int getReqHands() {
        return reqHands;
    }

    @Override
    public String toString() {
        return(
            ANSI_BRIGHT_WHITE +
            super.toString() + 
            "Damage Dealt: " + this.damage + "\n" +
            "# of Hands Required: " + this.reqHands + "\n" +
            ANSI_RESET
            );
    }
    public String battleDisplay(){
        return(ANSI_BRIGHT_WHITE +
                "Name: " + this.getName() + "\n" +
                "Damage Dealt: " + this.damage + "\n" +
                "# of Hands Required: " + this.reqHands + "\n" +
                ANSI_RESET
        );
    }
}