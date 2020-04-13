package character.monster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.ColouredOutputs.*;

public class MonsterDefaults {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MONSTER BATTLE DEFAULTS
    public static final double SKILL_DETERIORATION = 0.1;
    public static final List<? extends Monster> DEFAULT_MONSTERS =  new ArrayList<>
            (Arrays.asList(new Dragon("Natsunomeryu", 1, 100, 20, 0.1),
                    new Dragon("Chrysophylax", 2, 200, 50, 0.2),
                    new Dragon("Desghidorrah", 3, 300, 40, 0.35),
                    new Dragon("BunsenBurner", 4, 400, 50, 0.45),
                    new Dragon("Kas-Ethelinh", 5, 600, 50, 0.60),
                    new Dragon("Phaarthurnax", 6, 600, 70, 0.60),
                    new Dragon("TheScaleless", 7, 700, 60, 0.75),
                    new Dragon("TheWeatherbe", 8, 800, 90, 0.80),
                    new Dragon("D-Maleficent", 9, 900, 95, 0.85),
                    new Dragon("Alexstraszan", 10, 1000, 90, 0.55),
                    new Exoskeleton("BigBad-Wolf", 1, 150, 25, 0.15),
                    new Exoskeleton("WickedWitch", 2, 250, 35, 0.25),
                    new Exoskeleton("Brandobaris", 3, 350, 45, 0.30),
                    new Exoskeleton("Aasterinian", 4, 400, 50, 0.45),
                    new Exoskeleton("St-Shargaas", 5, 550, 65, 0.55),
                    new Exoskeleton("Chronepsish", 6, 650, 75, 0.60),
                    new Exoskeleton("Cyrrollalee", 7, 700, 80, 0.75),
                    new Exoskeleton("Kiaransalee", 8, 850, 95, 0.85),
                    new Exoskeleton("St-Yeenoghu", 9, 950, 85, 0.90),
                    new Exoskeleton("Merrshaullk", 10, 1000, 90, 0.55),
                    new Spirit("Aim-Haborym", 1, 450, 35, 0.35),
                    new Spirit("Andrealphus", 2, 600, 50, 0.40),
                    new Spirit("Andromalius", 3, 550, 45, 0.25),
                    new Spirit("Chiang-shih", 4, 700, 60, 0.40),
                    new Spirit("FallenAngel", 5, 800, 70, 0.50),
                    new Spirit("Ereshkigall", 6, 950, 45, 0.35),
                    new Spirit("Melchiresas", 7, 350, 15, 0.75),
                    new Spirit("Jormunngand", 8, 600, 90, 0.20),
                    new Spirit("Rakkshasass", 9, 550, 60, 0.35),
                    new Spirit("Taltecuhtli", 10, 300, 20, 0.50)));
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MONSTER COLOR
    public static final String DRAGON_COLOR =  ANSI_BRIGHT_GREEN;
    public static final String EXOSKELETON_COLOR = ANSI_BRIGHT_YELLOW ;
    public static final String SPIRIT_COLOR = ANSI_BRIGHT_CYAN;
}
