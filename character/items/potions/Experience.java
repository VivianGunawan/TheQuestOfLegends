package character.items.potions;


import static util.ColouredOutputs.ANSI_RESET;
import static character.CharacterDefaults.EXPERIENCE_POTION_COLOR;

public class Experience extends Potion{
    public Experience(String name, double price, int reqLevel, double effect){
        super(name, price, reqLevel,effect);
    }

    @Override
    public String toString() {
        return (EXPERIENCE_POTION_COLOR +
                super.toString() +
                this.getClass().getSimpleName() + " increased by " + this.getEffect() + "\n" +
                ANSI_RESET);
    }
}
