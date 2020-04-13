package character.items.potions;


import static util.ColouredOutputs.ANSI_RESET;
import static character.items.ItemDefaults.DEXTERITY_POTION_COLOR;

public class Dexterity extends Potion{
    public Dexterity(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel,effect);
    }

    @Override
    public String toString() {
        return (DEXTERITY_POTION_COLOR +
                super.toString() +
                this.getClass().getSimpleName() + " increased by " + this.getEffect() + "\n" +
                ANSI_RESET);
    }
}
