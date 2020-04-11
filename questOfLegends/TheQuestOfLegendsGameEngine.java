package questOfLegends;

import character.hero.Hero;
import character.merchant.Merchant;
import character.monster.Monster;
import tiles.InaccessibleTile;
//import util.ErrorMessage;

import src.util.ErrorMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static questOfLegends.QoLDefaults.*;
import static src.util.GameInputs.*;
import static src.util.IOConstants.DIVIDER;
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
        this.map = new LaneMap(this.numLane, this.laneSize, this.laneLength, this.merchant, this.probabilityPlain, this.probabilityBush, this.probabilityKoulou, this.probabilityCave);
        System.out.println("========= WELCOME TO THE QUEST OF LEGENDS ==========");
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
            System.out.println("Please select HERO ID for team member " + (j + 1));
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
            this.monsterTeam.addMember(availableMonsters.get(l),l+1, this.map.getMonstersNexus().get(l)); // change this to result from getMonsterNexus
        }
    }
    private void startQOLgame() {
        System.out.println("================== STARTING GAME ===================");
        generateMonster();
        for (int i = 0 ; i<this.numLane; i++){
            this.map.placeHero(this.heroTeam.getLocation(i),this.heroTeam.getHero(i));
            this.map.placeMonster(this.monsterTeam.getLocation(i),this.monsterTeam.getMonster(i));
        }
        this.map.display();
        // rounds
        // heroes turn
        for (int i = 0; i<this.numLane;i++){
            Hero currHero = this.heroTeam.getHero(i);
            int currHeroLocation = this.heroTeam.getLocation(i);
            this.map.surroundingTilesContainMonster(currHeroLocation);
            // even if there is a  monster you dont need to attack
            // Ask user to select option
            move(currHero,currHeroLocation);// ask user for direction, validates user choice, set new location of hero
        }

        this.map.display();
    }

    // a move method to allow the user to select a key to indicate in which direction they would like to move
    private void move(Hero currentHero, int currentHeroLocation){
        char move = '\u0000';
        try{
            while(!(checkMove(move, currentHeroLocation) && validateTile(move, currentHeroLocation)) && !(move == quit|| move == QUIT)){
                System.out.println("Where would you like to move your hero? " +
                        "up:(" + up + "/" + UP +
                        ") down:("+ down + "/" + DOWN +
                        ") left:("+ left + "/" + LEFT +
                        ") right:("+ right + "/" + RIGHT +
                        ") teleport:("+ teleport + "/" + TELEPORT +
                        ") quit:(" + quit + "/" + QUIT + ")"
                );
                move = scan.next().charAt(0);
            }
            if(!(move == quit||move == QUIT)){
                // loop through the team, each hero needs to perform an action
                for (int i = 0; i < this.numLane - 1; i++) {
                    this.heroTeam.setLocation(i, computeLocation(move, currentHeroLocation));
                    this.map.placeHero(this.heroTeam.getLocation(i), currentHero);
                }
            }
            else{
                return;
            }
        }
        catch(Exception e){
            ErrorMessage.printErrorInvalidInput();
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
