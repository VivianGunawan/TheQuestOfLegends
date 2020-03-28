package character;

import static utils.Defaults.*;

public abstract class Character {
    // represents a blueprint for all possible characters
    // Fields
    private final String name;
    private int level;
    private double healthPower;


    // Constructor
    public Character(String name, int level) {
        this.name = name;
        this.level = level;
        this.healthPower = this.level * HP_MULTIPLIER;
    }

    // Accessor
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public double getHealthPower() {
        return healthPower;
    }

    public void setHealthPower(double healthPower) {
        this.healthPower = healthPower;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return (
                "Name: " + this.name + "\n" +
                "Level:" + this.level + "\n" +
                "Health Power: " + this.healthPower + "\n"
        );
    }
}