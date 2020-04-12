package questOfLegends;

import character.AttackResult;
import character.hero.Hero;
import character.items.spells.Spell;
import character.merchant.Merchant;
import character.monster.Monster;
import tiles.InaccessibleTile;
//import util.ErrorMessage;

import src.util.ErrorMessage;
import tiles.Tile;

import java.util.*;
import java.util.stream.Collectors;

import static questOfLegends.QoLDefaults.*;
import static src.util.GameInputs.*;
import static src.util.IOConstants.*;


public class TheQuestOfLegendsGameEngine {
    // Fields
    private final int numLane;
    private final int laneSize;
    private final int laneLength;
    private final Merchant merchant;
    private final List<? extends Hero> heroes;
    private final List<? extends Monster> monsters;
    private final double probabilityPlain;
    private final double probabilityBush;
    private final double probabilityKoulou;
    private final double probabilityCave;
    private final LaneMap map;
    private final LaneTeam heroTeam;
    private final LaneTeam monsterTeam;
    Scanner scan = new Scanner(System.in);

    // Constructor
    public TheQuestOfLegendsGameEngine() {
        this.numLane = DEFAULT_LANE;
        this.laneSize = DEFAULT_LANE_SIZE;
        this.laneLength = DEFAULT_LANE_LENGTH;
        this.merchant = DEFAULT_MERCHANT;
        this.heroes = DEFAULT_HEROES;
        this.monsters = DEFAULT_MONSTERS;
        this.probabilityPlain = DEFAULT_PROBABILITY_PLAIN;
        this.probabilityCave = DEFAULT_PROBABILITY_CAVE;
        this.probabilityKoulou = DEFAULT_PROBABILITY_KOULOU;
        this.probabilityBush = DEFAULT_PROBABILITY_BUSH;
        System.out.println("========= WELCOME TO THE QUEST OF LEGENDS ==========");
        this.map = new LaneMap(this.numLane, this.laneSize, this.laneLength, this.merchant, this.probabilityPlain, this.probabilityBush, this.probabilityKoulou, this.probabilityCave);
        this.map.display();
        System.out.println("================== TEAM SELECTION ==================");
        this.heroTeam = selectTeam();
        this.monsterTeam = new LaneTeam();
        startQOLgame();
        endQOLgame();
    }

    // Methods
    // Select hero team members
    private LaneTeam selectTeam() {
        List<Hero> tempTeam = new ArrayList<Hero>();
        System.out.println("================== HERO SELECTION ==================");
        for (int i = 0; i < this.heroes.size(); i++) {
            System.out.println("HERO ID: " + (i + 1));
            System.out.print(this.heroes.get(i));
            System.out.println(DIVIDER);
        }
        for (int j = 0; j < this.numLane; j++) {
            Hero temp = this.heroes.get(0);
            System.out.println("Please select HERO ID for lane " + (j + 1));
            try {
                int index = scan.nextInt();
                scan.nextLine();
                System.out.println(DIVIDER);
                try {
                    temp = this.heroes.get(index - 1);
                } catch (Exception o) {
                    System.out.println("HERO ID entered out of range");
                }
                while (index < 1 || index > this.heroes.size() || tempTeam.contains(temp)) {
                    System.out.println("Please select a different HERO ID");
                    index = scan.nextInt();
                    System.out.println(DIVIDER);
                    scan.nextLine();
                    try {
                        temp = this.heroes.get(index - 1);
                    } catch (Exception o) {
                        System.out.println("HERO ID entered out of range");
                    }
                }
                tempTeam.add(temp);
                System.out.println(temp.getName() + " successfully added to team");
                System.out.println(DIVIDER);
            } catch (Exception e) {
                ErrorMessage.printErrorInvalidInput();
            }
        }
        LaneTeam heroes = new LaneTeam();
        for (int k = 0; k< this.numLane; k++){
            heroes.addMember(tempTeam.get(k),k+1,this.map.getHeroesNexus().get(k)); // change this to result from getHeroesNexus
        }
        return heroes;
    }

    // generate monster and set location to nexus
    private void generateMonster(){
        List<Monster> availableMonsters = new ArrayList();
        List<Monster> temp = this.monsters.stream()
                .filter(monster -> monster.getLevel() == this.heroTeam.getMaxLevel())
                .collect(Collectors.toList());
        availableMonsters.addAll(temp);
        // only 3 types of monster
        if(this.numLane>3){
            for(int k = 0; k < this.numLane/3; k++){
                availableMonsters.addAll(temp);
            }
        }
        Collections.shuffle(availableMonsters);
        for (int l = 0; l< this.numLane; l++){
            this.monsterTeam.addMember(availableMonsters.get(l),l+1, this.map.getMonstersNexus().get(l));
        }
    }
    // calls generate monster and place on nexus
    private void spawnMonster(){
        generateMonster();
        for (int i = 0 ; i<this.numLane; i++){
            this.map.placeMonster(this.monsterTeam.getLocation(i));
        }
    }

    // check if there is a hero that reached a monster nexus
    private boolean checkHeroWin(){
        for (int i=0; i<this.numLane; i++){
            if(0<this.heroTeam.getLocation(i) && this.heroTeam.getLocation(i)<= ((this.numLane * this.laneSize) + this.numLane - 1)){
                return true;
            }
        }
        return false;
    }
    // check if there is a monster that reached a hero nexus
    private boolean checkMonsterWin(){
        for (int i=0; i<this.monsterTeam.size(); i++){
            if(((this.laneLength+1)*((this.numLane * this.laneSize) + this.numLane - 1))<this.monsterTeam.getLocation(i) && this.monsterTeam.getLocation(i)<= ((this.laneLength+2)*((this.numLane * this.laneSize) + this.numLane - 1))){
                return true;
            }
        }
        return false;
    }

    // implement rounds
    private void startQOLgame() {
        int round = 0;
        System.out.println("================== STARTING GAME ===================");
        while (!checkHeroWin()&&!checkMonsterWin()){
            System.out.println("==================== ROUND " + round + " =======================");
            if(round%MONSTER_SPAWN_RATE == 0){
                spawnMonster();
            }
            // place all monsters in map
            for(int i=0; i<this.monsterTeam.size(); i++){
                this.map.placeMonster(this.monsterTeam.getLocation(i));
            }
            // Hero's turn
            for(int j = 0 ; j<this.numLane; j++){
                Hero currHero = this.heroTeam.getHero(j);
                int currHeroLocation = this.heroTeam.getLocation(j);
                // non actions
                this.map.placeHero(currHeroLocation,currHero);
                // actions
                char opt = '\u0000';
                try{
                    while (opt != ATTACK_INPUT && opt != CAST_INPUT && opt != CHANGE_INPUT && opt != USE_POTION_INPUT && opt != MOVE_INPUT && opt != TELEPORT_INPUT && opt!=INFO && opt!=info && opt!=quit && opt!=QUIT) {
                        System.out.println("Would " + currHero.getName() + "like to attack(" + ATTACK_INPUT
                                + ") cast Spell(" + CAST_INPUT
                                + ") change weapon or armor(" + CHANGE_INPUT
                                + ") use potion(" + USE_POTION_INPUT
                                + ") move(" + MOVE_INPUT
                                + ") teleport(" + TELEPORT_INPUT
                                + ") (" + info +"/" + INFO
                                +") (" +quit + "/" + QUIT + ")");
                        opt = scan.next().charAt(0);
                        System.out.println(DIVIDER);
                    }
                    if(opt==info||opt==INFO) {
                        System.out.print(currHero);
                        try {
                            while (opt != ATTACK_INPUT && opt != CAST_INPUT && opt != CHANGE_INPUT && opt != USE_POTION_INPUT && opt != MOVE_INPUT && opt != TELEPORT_INPUT && opt!=quit && opt!=QUIT) {
                                System.out.println("Would " + currHero.getName() + "like to attack(" + ATTACK_INPUT
                                        + ") cast Spell(" + CAST_INPUT
                                        + ") change weapon or armor(" + CHANGE_INPUT
                                        + ") use potion(" + USE_POTION_INPUT
                                        + ") move(" + MOVE_INPUT
                                        + ") teleport(" + TELEPORT_INPUT
                                        + ") (" + info +"/" + INFO
                                        +") (" +quit + "/" + QUIT + ")");
                                opt = scan.next().charAt(0);
                                System.out.println(DIVIDER);
                            }
                        } catch (Exception e) {
                            ErrorMessage.printErrorInvalidInput();
                        }
                    }
                } catch (Exception e){
                        ErrorMessage.printErrorInvalidInput();
                    }
                while(opt!= quit || opt!= QUIT) {
                    if (opt == ATTACK_INPUT) {
                        if (this.map.surroundingTilesContainMonster(currHeroLocation).size() == 0) {
                            System.out.println("There are no enemies within range");
                            opt = '\u0000';
                            try {
                                while (opt != CHANGE_INPUT && opt != USE_POTION_INPUT && opt != MOVE_INPUT && opt != TELEPORT_INPUT && opt!=quit && opt!=QUIT) {
                                    System.out.println("Would " + currHero.getName() + "like to change weapon or armor(" + CHANGE_INPUT
                                            + ") use potion(" + USE_POTION_INPUT
                                            + ") move(" + MOVE_INPUT
                                            + ") teleport(" + TELEPORT_INPUT
                                            + ") instead ? (" + quit + "/" + QUIT + ")");
                                    opt = scan.next().charAt(0);
                                    System.out.println(DIVIDER);
                                }
                            } catch (Exception e) {
                                ErrorMessage.printErrorInvalidInput();
                            }
                        }
                        else {
                            List<Monster> enemies = new ArrayList<>();
                            for (int tempLocation : this.map.surroundingTilesContainMonster(currHeroLocation)) {
                                enemies.add(this.monsterTeam.getMonsterByLocation(tempLocation));
                            }
                            int monsterId = 0;
                            // TODO: displayEnemies(enemies);
                            try {
                                while (monsterId < 1 || monsterId > enemies.size()) {
                                    System.out.println("which other monster would " +
                                            currHero.getName() + "like to attack (monster #)");
                                    monsterId = scan.nextInt();
                                    scan.nextLine();
                                    try {
                                        if (enemies.get(monsterId - 1).getHealthPower() == 0) {
                                            monsterId = 0;
                                        }
                                    } catch (Exception o) {
                                        ErrorMessage.printErrorOutOfRange();
                                    }
                                }
                                AttackResult sres = enemies.get(monsterId - 1).receiveBasicAttack(currHero.doBasicAttack());
                                if (sres == AttackResult.DODGE) {
                                    System.out.println(enemies.get(monsterId - 1).getName() + " dodged " + currHero.getName() + "'s attack");
                                } else if (sres == AttackResult.SUCCESS) {
                                    System.out.print(" from " + currHero.getName() + "\n");
                                    System.out.println(enemies.get(monsterId - 1));
                                } else if (sres == AttackResult.KILL) {
                                    System.out.println(currHero.getName() + " killed " + enemies.get(monsterId - 1).getName());
                                }
                                break;
                            } catch (InputMismatchException e) {
                                ErrorMessage.printErrorInvalidInput();
                            }
                        }
                    }
                    if (opt == CAST_INPUT) {
                        if (this.map.surroundingTilesContainMonster(currHeroLocation).size() == 0) {
                            System.out.println("There are no enemies within range");
                            opt = '\u0000';
                            try {
                                while (opt != CHANGE_INPUT && opt != USE_POTION_INPUT && opt != MOVE_INPUT && opt != TELEPORT_INPUT && opt!=quit && opt!=QUIT) {
                                    System.out.println("Would " + currHero.getName() + "like to change weapon or armor(" + CHANGE_INPUT
                                            + ") use potion(" + USE_POTION_INPUT
                                            + ") move(" + MOVE_INPUT
                                            + ") teleport(" + TELEPORT_INPUT
                                            + ") instead ? (" + quit + "/" + QUIT + ")");
                                    opt = scan.next().charAt(0);
                                    System.out.println(DIVIDER);
                                }
                            } catch (Exception e) {
                                ErrorMessage.printErrorInvalidInput();
                            }
                        } else {
                            List<Monster> enemies = new ArrayList<>();
                            for (int tempLocation : this.map.surroundingTilesContainMonster(currHeroLocation)) {
                                enemies.add(this.monsterTeam.getMonsterByLocation(tempLocation));
                            }
                            if (currHero.getInventory().numSpell() != 0) {
                                int monsterId = 0;
                                // TODO: displayEnemies(enemies);
                                try {
                                    while (monsterId < 1 || monsterId > enemies.size()) {
                                        System.out.println(" which other monster would " + currHero.getName() + "like to cast spell on? (monster #)");
                                        monsterId = scan.nextInt();
                                        scan.nextLine();
                                        try {
                                            if (enemies.get(monsterId - 1).getHealthPower() == 0) {
                                                monsterId = 0;
                                            }
                                        } catch (Exception o) {
                                            ErrorMessage.printErrorOutOfRange();
                                        }
                                    }
                                    Spell casted = currHero.castSpell();
                                    AttackResult sres = enemies.get(monsterId - 1).receiveSpell(casted, currHero.castSpellDamage(casted));
                                    if (sres == AttackResult.DODGE) {
                                        System.out.println(enemies.get(monsterId - 1).getName() + " dodged " + currHero.getName() + "'s spell");
                                        System.out.println(enemies.get(monsterId - 1));
                                    } else if (sres == AttackResult.SUCCESS) {
                                        System.out.print(" from " + currHero.getName() + "\n");
                                        System.out.println(enemies.get(monsterId - 1));
                                    } else if (sres == AttackResult.KILL) {
                                        System.out.println(currHero.getName() + " killed " + enemies.get(monsterId - 1).getName());
                                    }
                                    break;
                                } catch (InputMismatchException e) {
                                    ErrorMessage.printErrorInvalidInput();
                                }
                            } else {
                                System.out.println(currHero.getName() + "has no spell to cast");
                                System.out.println(currHero.getName() + "'s turn is wasted.");
                            }
                        }
                    }
                    if (opt == CHANGE_INPUT) {
                        currHero.change();
                        currHero.battleDisplay();
                        break;
                    }
                    if (opt == USE_POTION_INPUT) {
                        if (currHero.getInventory().numPotions() != 0) {
                            currHero.usePotion();
                            currHero.battleDisplay();
                        } else {
                            System.out.println(currHero.getName() + " has no potion to consume");
                            System.out.println(currHero.getName() + "'s turn is wasted.");
                        }
                        break;
                    }
                    if (opt == MOVE_INPUT) {
                        char move = '\u0000';
                        try {
                            while (!(checkMove(move, currHeroLocation) && validateTile(move, currHeroLocation))) {
                                System.out.println("Where would you like to move your hero? " +
                                        "up:(" + up + "/" + UP +
                                        ") down:(" + down + "/" + DOWN +
                                        ") left:(" + left + "/" + LEFT +
                                        ") right:(" + right + "/" + RIGHT + ")");
                                move = scan.next().charAt(0);
                            }
                            this.map.removeHero(currHeroLocation, currHero);
                            this.heroTeam.setLocation(j, computeLocation(move, currHeroLocation));
                            System.out.println(currHero.getName() + "will appear on tile " + this.heroTeam.getLocation(j) + " on the next turn");
                            break;
                        } catch (Exception e) {
                            ErrorMessage.printErrorInvalidInput();
                        }
                  }
                  if (opt == TELEPORT_INPUT) {
                    int teleportLocation = 0;
                    Tile desiredTile;
                    System.out.println("Which tile would you like to teleport " + currHero.getName() + " to? ");
                    try {
                        teleportLocation = scan.nextInt();
                        System.out.println(DIVIDER);
                        scan.nextLine();
                        while (teleportLocation < 1 || teleportLocation > this.map.getColSize() * this.map.getRowSize() || tileIsInaccessible(teleportLocation) ||
                                passMonsterDuringTeleport(j, teleportLocation) || teleportInSameLane(j, teleportLocation)) {
                            ErrorMessage.printTeleportError();
                            System.out.println("Please select another tile location");
                            teleportLocation = scan.nextInt();
                            System.out.println(DIVIDER);
                            scan.nextLine();
                        }
                    } catch (Exception o) {
                        ErrorMessage.printErrorInvalidInput();
                    }
                    this.map.removeHero(currHeroLocation, currHero);
                    this.heroTeam.setLocation(j, teleportLocation);
                    System.out.println(currHero.getName()+ "will appear on tile "+ this.heroTeam.getLocation(j) + " on the next turn");
                }
            }
      }
            // Monster turn
            for(int k = 0; k<this.monsterTeam.size();k++){
                Monster currMonster = this.monsterTeam.getMonster(k);
                int currMonsterLocation = this.monsterTeam.getLocation(k);
                // actions
                // no heroes in proximity
                if(this.map.surroundingTilesContainHero(currMonsterLocation).size()==0){
                    this.map.removeMonster(currMonsterLocation);
                    this.monsterTeam.setLocation(k,currMonsterLocation+ this.map.getColSize());
                }
                // heroes in proximity
                else{
                    // select first hero as enemy by default
                    int enemyLocation = this.map.surroundingTilesContainHero(currMonsterLocation).get(0);
                    int enemyIndex = this.heroTeam.getHeroIndexByLocation(enemyLocation);
                    // attack
                    AttackResult sres = this.heroTeam.getHero(enemyIndex).receiveBasicAttack(currMonster.doBasicAttack());
                    if (sres == AttackResult.DODGE) {
                        System.out.println(this.heroTeam.getHero(enemyIndex).getName() + " dodged " + currMonster.getName() + "'s attack");
                        this.heroTeam.getHero(enemyIndex).battleDisplay();
                    }
                    else if (sres == AttackResult.SUCCESS) {
                        System.out.print(" from " + currMonster.getName() + "\n");
                        this.heroTeam.getHero(enemyIndex).battleDisplay();
                    }
                    else if (sres == AttackResult.KILL) {
                        System.out.println(currMonster.getName() + " killed " + this.heroTeam.getHero(enemyIndex).getName());
                    }
                }
            }
            round++;
    }

    private boolean tileIsInaccessible(Integer teleportLocation) {
        return (this.map.getTile(teleportLocation) instanceof InaccessibleTile);
    }

    private boolean passMonsterDuringTeleport(Integer index, Integer teleportLocation) {
        int colIndex = (teleportLocation - 1) % this.map.getColSize();
        int lane = (colIndex / this.map.getNumLane()) + 1;
        return !(teleportLocation > this.monsterTeam.getLocation(index));
    }

    private boolean teleportInSameLane(Integer heroIndex, Integer teleportLocation) {
        int colIndex = (teleportLocation - 1) % this.map.getColSize();
        int lane = (colIndex / this.map.getNumLane()) + 1;
        return (this.heroTeam.getLane(heroIndex) == lane);

    }
    // when user chooses to quit
    private void endQOLgame(){
        System.out.println("==================== END GAME ======================");
    }

    // Hero move validations
    private boolean checkMove(char m, int currentHeroLocation){
        if( m == up || m == UP || m == down || m == DOWN || m == left ||m == LEFT|| m == right || m == RIGHT) {
            if (m == up || m == UP) {
                return (currentHeroLocation > this.map.getRowSize());
            }
            else if (m == down || m == DOWN) {
                return currentHeroLocation <= (this.map.getColSize() - 1) * this.map.getColSize() || currentHeroLocation > this.map.getColSize() * this.map.getRowSize();
            }
            else if (m == left || m == LEFT) {
                return (currentHeroLocation % this.map.getRowSize() != 1 && !(this.map.getTile(currentHeroLocation - 1) instanceof InaccessibleTile));
            }
            else {
                return (currentHeroLocation % this.map.getRowSize() != 0 && !(this.map.getTile(currentHeroLocation + 1) instanceof InaccessibleTile));
            }
        }
        return false;
    }
    private int computeLocation(char m, int currentHeroLocation){
        if(m==up||m==UP||m==down||m==DOWN||m==left||m==LEFT||m==right||m==RIGHT) {
            if (m == up || m == UP) {
                if (!(currentHeroLocation <= this.map.getRowSize())) {
                    return (currentHeroLocation - this.map.getRowSize());
                }
            }
            else if (m == down || m == DOWN) {
                if (!(currentHeroLocation > (this.map.getColSize() - 1) * this.map.getColSize() &&
                        currentHeroLocation <= this.map.getColSize() * this.map.getRowSize())) {
                    return currentHeroLocation + this.map.getRowSize();
                }
            }
            else if (m == left || m == LEFT) {
                // make sure that the tile to the left of the hero is not an inaccessible tile
                if ((currentHeroLocation % this.map.getRowSize() != 1) || !(this.map.getTile(currentHeroLocation - 1) instanceof InaccessibleTile)) {
                    return currentHeroLocation - 1;
                }
            }
            else {
                // make sure that the tile to the right of the hero is not an inaccessible tile
                if((currentHeroLocation % this.map.getRowSize() != 0) || !(this.map.getTile(currentHeroLocation + 1) instanceof InaccessibleTile)) {
                    return currentHeroLocation + 1;
                }
            }
        }
        return 0;
    }
    private boolean validateTile(char m, int currentHeroLocation){
        if (this.map.getTile(computeLocation(m, currentHeroLocation)) instanceof InaccessibleTile){
            return false;
        }
        return true;
        
    }

    // Monster move validations

    public static void main(String[] args) {
        TheQuestOfLegendsGameEngine game = new TheQuestOfLegendsGameEngine();
    }
}
