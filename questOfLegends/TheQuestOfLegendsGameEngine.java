package questOfLegends;

import character.AttackResult;
import character.hero.Hero;
import character.items.spells.Spell;
import character.merchant.Merchant;
import character.monster.Monster;
import tiles.InaccessibleTile;
//import util.ErrorMessage;

import src.util.ErrorMessage;

import java.util.*;
import java.util.stream.Collectors;

import static questOfLegends.QoLDefaults.*;
import static src.util.GameInputs.*;
import static src.util.IOConstants.*;


public class TheQuestOfLegendsGameEngine {
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
    }

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
            this.map.placeMonster(this.monsterTeam.getLocation(i),this.monsterTeam.getMonster(i));
        }
    }
    private boolean checkHeroWin(){
        for (int i=0; i<this.numLane; i++){
            if(0<this.heroTeam.getLocation(i) && this.heroTeam.getLocation(i)<= ((this.numLane * this.laneSize) + this.numLane - 1)){
                return true;
            }
        }
        return false;
    }
    private boolean checkMonsterWin(){
        for (int i=0; i<this.monsterTeam.size(); i++){
            if(((this.laneLength+1)*((this.numLane * this.laneSize) + this.numLane - 1))<this.monsterTeam.getLocation(i) && this.monsterTeam.getLocation(i)<= ((this.laneLength+2)*((this.numLane * this.laneSize) + this.numLane - 1))){
                return true;
            }
        }
        return false;
    }
    private void startQOLgame() {
        int round = 0;
        System.out.println("================== STARTING GAME ===================");
      while (!checkHeroWin()&&!checkMonsterWin()){
            System.out.println("==================== ROUND " + round + " =======================");
              for (int i = 0 ; i<this.numLane; i++){
                  this.map.placeHero(this.heroTeam.getLocation(i),this.heroTeam.getHero(i));
              }
            if(round%MONSTER_SPAWN_RATE == 0){
                spawnMonster();
            }
            this.map.display();
            for(int j = 0 ; j<this.numLane; j++){
                Hero currHero = this.heroTeam.getHero(j);
                int currHeroLocation = this.heroTeam.getLocation(j);
                char opt = '\u0000';
                try{
                    while (opt != ATTACK_INPUT && opt != CAST_INPUT && opt != CHANGE_INPUT && opt != USE_POTION_INPUT && opt != MOVE_INPUT && opt != TELEPORT_INPUT && opt!=INFO && opt!=info) {
                        System.out.println("Would " + currHero.getName() + "like to attack(" + ATTACK_INPUT
                                + ") cast Spell(" + CAST_INPUT
                                + ") change weapon or armor(" + CHANGE_INPUT
                                + ") use potion(" + USE_POTION_INPUT
                                + ") move(" + MOVE_INPUT
                                + ") teleport(" + TELEPORT_INPUT + ") (" + info +"/" + INFO +")");
                        opt = scan.next().charAt(0);
                        System.out.println(DIVIDER);
                    }
                    if(opt==info||opt==INFO) {
                        System.out.print(currHero);
                        try {
                            while (opt != ATTACK_INPUT && opt != CAST_INPUT && opt != CHANGE_INPUT && opt != USE_POTION_INPUT && opt != MOVE_INPUT && opt != TELEPORT_INPUT) {
                                System.out.println("Would " + currHero.getName() + "like to attack(" + ATTACK_INPUT
                                        + ") cast Spell(" + CAST_INPUT
                                        + ") change weapon or armor(" + CHANGE_INPUT
                                        + ") use potion(" + USE_POTION_INPUT
                                        + ") move(" + MOVE_INPUT
                                        + ") teleport(" + TELEPORT_INPUT + ")");
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
                if (opt == ATTACK_INPUT) {
                    this.map.getTile(currHeroLocation);
                    if(this.map.surroundingTilesContainMonster(currHeroLocation).size()==0){
                        System.out.println("There are no enemies within range");
                        opt = '\u0000';
                        try {
                            while (opt != CHANGE_INPUT && opt != USE_POTION_INPUT && opt != MOVE_INPUT && opt != TELEPORT_INPUT) {
                                System.out.println("Would " + currHero.getName() + "like to change weapon or armor(" + CHANGE_INPUT
                                        + ") use potion(" + USE_POTION_INPUT
                                        + ") move(" + MOVE_INPUT
                                        + ") teleport(" + TELEPORT_INPUT + ") instead?");
                                opt = scan.next().charAt(0);
                                System.out.println(DIVIDER);
                            }
                        } catch (Exception e) {
                            ErrorMessage.printErrorInvalidInput();
                        }
                    }
                    else{
                        List<Monster> enemies = new ArrayList<>();
                        for(int tempLocation:this.map.surroundingTilesContainMonster(currHeroLocation)){
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
                                try{
                                    if (enemies.get(monsterId-1).getHealthPower()==0){
                                        monsterId = 0;
                                    }
                                }catch(Exception o) { ErrorMessage.printErrorOutOfRange(); }
                            }
                            AttackResult sres = enemies.get(monsterId - 1).receiveBasicAttack(currHero.doBasicAttack());
                            if (sres == AttackResult.DODGE) {
                                System.out.println(enemies.get(monsterId - 1).getName() + " dodged " + currHero.getName() + "'s attack");
                            }
                            else if (sres == AttackResult.SUCCESS) {
                                System.out.print(" from " + currHero.getName() + "\n");
                                System.out.println(enemies.get(monsterId - 1));
                            }
                            else if (sres == AttackResult.KILL) {
                                System.out.println(currHero.getName() + " killed " + enemies.get(monsterId - 1).getName());
                            }
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
                            while (opt != CHANGE_INPUT && opt != USE_POTION_INPUT && opt != MOVE_INPUT && opt != TELEPORT_INPUT) {
                                System.out.println("Would " + currHero.getName() + "like to change weapon or armor(" + CHANGE_INPUT
                                        + ") use potion(" + USE_POTION_INPUT
                                        + ") move(" + MOVE_INPUT
                                        + ") teleport(" + TELEPORT_INPUT + ") instead?");
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
                }
                if (opt == USE_POTION_INPUT) {
                    if(currHero.getInventory().numPotions()!=0){
                        currHero.usePotion();
                        currHero.battleDisplay();
                    }
                    else{
                        System.out.println(currHero.getName() + " has no potion to consume");
                        System.out.println(currHero.getName() + "'s turn is wasted.");
                    }
                }
                if (opt == MOVE_INPUT) {
                    char move = '\u0000';
                    try{
                        while(!(checkMove(move, currHeroLocation) && validateTile(move, currHeroLocation)) && !(move == quit|| move == QUIT)){
                            System.out.println("Where would you like to move your hero? " +
                                    "up:(" + up + "/" + UP +
                                    ") down:("+ down + "/" + DOWN +
                                    ") left:("+ left + "/" + LEFT +
                                    ") right:("+ right + "/" + RIGHT +
                                    ") teleport:("+ TELEPORT_INPUT + "/" + TELEPORT +
                                    ") quit:(" + quit + "/" + QUIT + ")"
                            );
                            move = scan.next().charAt(0);
                        }
                        if(!(move == quit||move == QUIT)){
                            this.map.removeHero(currHeroLocation, currHero);
                            this.heroTeam.setLocation(j, computeLocation(move, currHeroLocation));
                            System.out.println(currHero.getName()+ "will appear on tile "+ this.heroTeam.getLocation(j) + " on the next round");
                        }
                        else{
                            return;
                        }
                    }
                    catch(Exception e){
                        ErrorMessage.printErrorInvalidInput();
                    }

                }
                //todo
                if (opt == TELEPORT_INPUT) {
                }
            }
            // Monster turn
            for(int k = 0; k<this.monsterTeam.size();k++){
                // check for nearby hero
                // attack if hero is range
            }
            round++;
        }
    }

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

    public static void main(String[] args) {
        TheQuestOfLegendsGameEngine game = new TheQuestOfLegendsGameEngine();
    }
}
