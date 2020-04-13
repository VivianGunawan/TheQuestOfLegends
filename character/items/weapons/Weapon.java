package character.items.weapons;

import character.items.Item;

import static util.ColouredOutputs.ANSI_RESET;
import static character.CharacterDefaults.WEAPON_COLOR;

public class Weapon extends Item {
    // Used by hero to attack a monster
    // Fields
    double damage;
    int reqHands;
    // Constructor
    public Weapon(String name, double price, int reqLevel, double damage, int reqHands){
        super(name, price, reqLevel);
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
            WEAPON_COLOR +
            super.toString() + 
            "Damage Dealt: " + this.damage + "\n" +
            "# of Hands Required: " + this.reqHands + "\n" +
            ANSI_RESET
            );
    }
    public String battleDisplay(){
        return(WEAPON_COLOR +
                "Name: " + this.getName() + "\n" +
                "Damage Dealt: " + this.damage + "\n" +
                "# of Hands Required: " + this.reqHands + "\n" +
                ANSI_RESET
        );
    }
}