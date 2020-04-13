package character.hero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.ColouredOutputs.*;

public class HeroDefaults {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // HERO DEFAULTS
    public static final double MANA_MULTIPLIER = 200;
    public static final double EXP_MULTIPLIER = 10;
    public static final double SKILLS_MULTIPLIER = 0.05;
    public static final int HEROES_HANDS = 2;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // HERO BATTLE DEFAULTS
    public static final double STRENGTH_MULTIPLIER = 0.05;
    public static final double DEXTERITY_MULTIPLIER = 0.0001;
    public static final double AGILITY_MULTIPLIER = 0.00002;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // HEROES COLOR
    public static final String PALADIN_COLOR = ANSI_BRIGHT_RED ;
    public static final String SORCERER_COLOR = ANSI_BRIGHT_PURPLE;
    public static final String WARRIOR_COLOR = ANSI_BRIGHT_BLUE;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final List<? extends Hero> DEFAULT_HEROES = new ArrayList<>
        (Arrays.asList(new Warrior("Gaerdal Ironhand", 7,  700, 500, 600, 1354, HEROES_HANDS),
                new Warrior("Sehanine Monnbow", 8,  700, 800, 500, 2500, HEROES_HANDS),
                new Warrior("Muamman Duathall", 6,  900, 500, 750, 2546, HEROES_HANDS),
                new Warrior("Flandal Steelskin", 7,  750, 650, 700, 2500, HEROES_HANDS),
                new Sorcerer("Garl Glittergold", 7,  550, 600, 500, 2500, HEROES_HANDS),
                new Sorcerer("Rillifane Rallathil", 9,  750, 450, 500, 2500, HEROES_HANDS),
                new Sorcerer("Segojan Earthcaller", 5,  800, 500, 650, 2500, HEROES_HANDS),
                new Sorcerer("Skoraeus Stonebones", 6, 850, 600, 450, 2500, HEROES_HANDS),
                new Paladin("Solonor Thelandira", 7,  750, 650, 700, 2500, HEROES_HANDS)));
}
