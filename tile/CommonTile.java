package tile;

import character.monster.Monster;
import java.util.*;
import java.util.stream.Collectors;
import static utils.ColouredOutputs.*;

public class CommonTile extends Tile {
    //represents a commmon tile
    // Fields
    // Monsters dwell in common tile
    private final List<? extends Monster> monsters;
    private final double probabilityEncounter;
    public List<Monster> availableMonsters;
    Scanner scan = new Scanner(System.in);

    //Constructor
    public CommonTile(List<? extends Monster> monsters, double probabilityEncounter) {
        super(TileType.C);
        this.monsters = monsters;
        this.probabilityEncounter = probabilityEncounter;
        this.availableMonsters = new ArrayList<Monster>();
    }
    // used for checking if a battle should occur
    public boolean monsterAwake(){
        return Math.random() < probabilityEncounter;
    }
    // returns nC monsters(each monster level is equal to maxLvl)
    public List<Monster> getMonsters(int nC, int maxLvl) {
        filterMonsters(maxLvl);
        return this.availableMonsters.subList(0,nC);
    }
    // filters monsters to get monsters with level equal to maxLvl then shuffles the list.
    private void filterMonsters(int maxLvl) {
        List<Monster> temp = this.monsters.stream()
                .filter(monster -> monster.getLevel() == maxLvl)
                .collect(Collectors.toList());
        this.availableMonsters.addAll(temp);
        Collections.shuffle(this.availableMonsters);
    }

    @Override
    public String toString() {
        if (this.isActive()) {
            return ANSI_BRIGHT_BLACK +ANSI_BRIGHT_BG_GREEN + super.toString() + " *" + ANSI_RESET;
        } else {
            return ANSI_BRIGHT_BLACK +ANSI_BRIGHT_BG_GREEN + " " + super.toString() + " " + ANSI_RESET;
        }
    }
}