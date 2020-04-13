package character.items.potions;


import static util.ColouredOutputs.ANSI_RESET;
import static character.items.ItemDefaults.HEALTH_POTION_COLOR;

public class Health extends Potion{
    public Health(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel,effect);
    }

    @Override
    public String toString() {
        return (HEALTH_POTION_COLOR +
                super.toString() +
                this.getClass().getSimpleName() + " increased by " + this.getEffect() + "\n" +
                ANSI_RESET);
    }
}
