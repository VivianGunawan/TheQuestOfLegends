package utils;

import character.hero.Hero;
import character.hero.Paladin;
import character.hero.Sorcerer;
import character.hero.Warrior;
import character.items.spells.Fire;
import character.items.spells.Ice;
import character.items.spells.Lightning;
import character.merchant.Merchant;
import character.monster.Dragon;
import character.monster.Exoskeleton;
import character.monster.Monster;
import character.items.Item;
import character.items.weapons.Weapon;
import character.items.armors.Armor;
import character.items.spells.Spell;
import character.items.potions.Potion;
import character.monster.Spirit;


import javax.crypto.CipherOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//////////////////////////////////////////////
//              GAME DEFAULTS               //
//////////////////////////////////////////////

public class Defaults {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // QoL DEFAULTS
    public static final int DEFAULT_LANE = 3;
    public static final int DEFAULT_LANE_SIZE = 2;

    // Map defaults for QoL
    public static final double DEFAULT_PROBABILITY_PLAIN = 0.7;
    public static final double DEFAULT_PROBABILITY_BUSH = 0.1;
    public static final double DEFAULT_PROBABILITY_KOULOU = 0.1;
    public static final double DEFAULT_PROBABILITY_CAVE = 0.1;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MAP DEFAULTS
    public static final int DEFAULT_ROW_SIZE = 8;
    public static final int DEFAULT_COL_SIZE = 8;
    public static final double DEFAULT_PROBABILITY_INACCESSIBLE = 0.2;
    public static final double DEFAULT_PROBABILITY_MARKET = 0.3;
    public static final double DEFAULT_PROBABILITY_COMMON = 0.5;
    public static final double DEFAULT_PROBABILITY_ENCOUNTER= 0.75;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // HERO DEFAULTS
    public static final double HP_MULTIPLIER = 100;
    public static final int HEROES_HANDS = 2;
    public static final double EXP_MULTIPLIER = 10;
    public static final double SKILLS_MULTIPLIER = 0.05;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // BATTLE DEFAULTS
    public static final double SKILL_DETERIORATION = 0.1;
    public static final double STRENGTH_MULTIPLIER = 0.05;
    public static final double DEXTERITY_MULTIPLIER = 0.0001;
    public static final double AGILITY_MULTIPLIER = 0.00002;
    public static final double MANA_REGEN = 0.05;
    public static final double HP_REGEN = 0.05;
    public static final double BOUNTY_MULTIPLIER = 100;
    public static final double BOUNTY_EXP = 2;
    public static final double WIN_REVIVE_HP_MULTIPLIER = 0.5;
    public static final double LOSE_REVIVE_HP_MULTIPLIER = 0.25;
    public static final double TAX_MULTIPLIER = 0.5;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GAME DEFAULTS
    public static final List<Weapon> DEFAULT_WEAPONS = new ArrayList<>
            (Arrays.asList(new Weapon("Sword", 500, 1, 800, 1),
                    new Weapon("Bow", 300, 2, 500, 2),
                    new Weapon("Sycthe", 1000, 6, 1100, 2),
                    new Weapon("Axe", 550, 5, 850, 1),
                    new Weapon("TSwords", 1400, 8, 1600, 2),
                    new Weapon("Dagger", 200, 1, 250, 1)));
    public static final List<Armor> DEFAULT_ARMORS = new ArrayList<>
            (Arrays.asList(new Armor("Platinum Shield", 150, 1, 200),
                    new Armor("Breastplate", 350, 3, 600),
                    new Armor("Full Body character.items.Armor", 1000, 8, 1100),
                    new Armor("Wizard Shield", 1200, 10, 1500),
                    new Armor("Speed Boots", 550, 4, 600)));
    public static final List<Spell> DEFAULT_SPELLS = new ArrayList<>
            (Arrays.asList(new Ice("Snow Canon", 500, 2, 650, 250),
                        new Ice("Ice Blade", 250, 1, 450, 100),
                        new Ice("Frost Blizzard", 750, 5, 850, 350),
                        new Ice("Artic Storm", 700, 6, 800, 300),
                        new Fire("Flame Tornado", 700, 4, 850, 300),
                        new Fire("Breath of Fire", 350, 1, 450, 100),
                        new Fire("Heat Wave", 450, 2, 600, 150),
                        new Fire("Lava Commet", 800, 7, 1000, 550),
                        new Lightning("Lightning Dagger", 400, 1, 500, 150),
                        new Lightning("Thunder Blast", 750, 4, 950, 400),
                        new Lightning("Electric Arrows", 550, 5, 650, 200),
                        new Lightning("Spark Needles", 500, 2, 600, 200)));
    public static final List<Potion> DEFAULT_POTIONS =new ArrayList<>
            (Arrays.asList(new Potion("Healing ", 250, 1,"HEALTH", 100),
                    new Potion("Strength", 200, 1, "STRENGTH", 75),
                    new Potion("Magic", 350, 2, "MANA", 100),
                    new Potion("Luck Elixir", 500, 4, "DEXTERITY", 65),
                    new Potion("Mermaid Tears", 850, 5, "AGILITY",100),
                    new Potion("Ambriosa", 1000, 8, "EXPERIENCE",150)));
    public static final List<Item> DEFAULT_ITEMS = Stream.of(DEFAULT_WEAPONS,DEFAULT_ARMORS,DEFAULT_SPELLS,DEFAULT_POTIONS)
                                                .flatMap(Collection::stream)
                                                .collect(Collectors.toList());
    public static final Merchant DEFAULT_MERCHANT = new Merchant(DEFAULT_ITEMS);
    public static final List<? extends Hero> DEFAULT_HEROES = new ArrayList<>
            (Arrays.asList(new Warrior("Gaerdal Ironhand", 7, 100, 700, 500, 600, 1354, HEROES_HANDS),
                   new Warrior("Sehanine Monnbow", 8, 600, 700, 800, 500, 2500, HEROES_HANDS),
                   new Warrior("Muamman Duathall", 6, 300, 900, 500, 750, 2546, HEROES_HANDS),
                   new Warrior("Flandal Steelskin", 7, 200, 750, 650, 700, 2500, HEROES_HANDS),
                   new Sorcerer("Garl Glittergold", 7, 700, 550, 600, 500, 2500, HEROES_HANDS),
                   new Sorcerer("Rillifane Rallathil", 9, 1300, 750, 450, 500, 2500, HEROES_HANDS),
                   new Sorcerer("Segojan Earthcaller", 5, 900, 800, 500, 650, 2500, HEROES_HANDS),
                   new Sorcerer("Skoraeus Stonebones", 6, 800, 850, 600, 450, 2500, HEROES_HANDS),
                    new Paladin("Solonor Thelandira", 7, 300, 750, 650, 700, 2500, HEROES_HANDS)));

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
}
