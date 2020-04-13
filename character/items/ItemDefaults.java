package character.items;

import character.items.armors.Armor;
import character.items.potions.*;
import character.items.spells.Fire;
import character.items.spells.Ice;
import character.items.spells.Lightning;
import character.items.spells.Spell;
import character.items.weapons.Weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.ColouredOutputs.*;

public class ItemDefaults {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ITEM COLORS
    public static final String WEAPON_COLOR = ANSI_WHITE;
    public static final String ARMOR_COLOR = ANSI_GREEN;
    public static final String ICE_SPELL_COLOR = ANSI_CYAN;
    public static final String FIRE_SPELL_COLOR = ANSI_RED;
    public static final String LIGHTNING_SPELL_COLOR = ANSI_BLUE;
    public static final String AGILITY_POTION_COLOR = ANSI_YELLOW;
    public static final String DEXTERITY_POTION_COLOR = ANSI_PURPLE;
    public static final String EXPERIENCE_POTION_COLOR = ANSI_BRIGHT_WHITE  ;
    public static final String HEALTH_POTION_COLOR = ANSI_BRIGHT_GREEN;
    public static final String MANA_POTION_COLOR = ANSI_BRIGHT_BLUE;
    public static final String STRENGTH_POTION_COLOR = ANSI_BRIGHT_RED;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    public static final List<? extends Potion> DEFAULT_POTIONS =new ArrayList<>
            (Arrays.asList(new Health("Healing Potion", 250, 1, 100),
                    new Strength("Strength Potion", 200, 1, 75),
                    new Mana("Magic Potion", 350, 2,  100),
                    new Dexterity("Luck Elixir", 500, 4,65),
                    new Agility("Mermaid Tears", 850, 5,100),
                    new Experience("Ambriosa", 1000, 8, 150)));
    public static final List<Item> DEFAULT_ITEMS = Stream.of(DEFAULT_WEAPONS,DEFAULT_ARMORS,DEFAULT_SPELLS,DEFAULT_POTIONS)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
}
