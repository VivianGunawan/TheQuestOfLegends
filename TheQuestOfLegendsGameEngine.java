import character.hero.Hero;
import character.merchant.Merchant;
import character.monster.Monster;
import tile.InaccessibleTile;
import utils.ErrorMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static utils.Defaults.*;
import static utils.IOConstants.*;
import static utils.IOConstants.QUIT;

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
        this.map.displayMap();
        this.heroTeam = selectTeam();
        this.monsterTeam = new LaneTeam();
        startQOLgame();
    }

    private LaneTeam selectTeam() {
        List<Hero> tempTeam = new ArrayList<Hero>();
        System.out.println("Hero Selection");
        for (int i = 0; i < this.heroes.size(); i++) {
            System.out.println("HERO ID: " + (i + 1));
            System.out.println(this.heroes.get(i));
        }
        for (int j = 0; j < this.numLane; j++) {
            character.hero.Hero temp = this.heroes.get(0);
            System.out.println("Please select HERO ID for lane " + (j + 1));
            try {
                int index = scan.nextInt();
                scan.nextLine();
                try {
                    temp = this.heroes.get(index - 1);
                } catch (Exception o) {
                    System.out.println("HERO ID entered out of range");
                }
                while (index < 1 || index > this.heroes.size() || tempTeam.contains(temp)) {
                    System.out.println("Please select a different HERO ID");
                    index = scan.nextInt();
                    scan.nextLine();
                    try {
                        temp = this.heroes.get(index - 1);
                    } catch (Exception o) {
                        System.out.println("HERO ID entered out of range");
                    }
                }
                tempTeam.add(temp);
                System.out.println(temp.getName() + " successfully added to team");
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
        generateMonster();
        for (int i = 0 ; i<this.numLane; i++){
            this.map.placeHero(this.heroTeam.getLocation(i),this.heroTeam.getHero(i));
            this.map.placeMonster(this.monsterTeam.getLocation(i),this.monsterTeam.getMonster(i));
        }
        this.map.displayMap();
        // rounds
        // heroes turn
        for (int i = 0; i<this.numLane;i++){
            Hero currHero = this.heroTeam.getHero(i);
            int currHeroLocation = this.heroTeam.getLocation(i);
            this.map.surroundingTilesContainMonster();
            // even if there is a  monster you dont need to attack
            // Ask user to select option
            move(currHero,currHeroLocation);// ask user for direction, validates user choice, set new location of hero
        }
    }

    // a move method to allow the user to select a key to indicate in which direction they would like to move
    private void move(int location){
        char move = '\u0000';
        try{
            while(!(checkMove(move, location) && validateTile(move)) && !(move == quit|| move == QUIT)){
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
                for (int i = 0; i < this.numLane; i++) {
                    this.heroTeam.setLocation(i, computeLocation(move));
                    //this.location = computeLocation(move);
                    this.map.placeHero(this.heroTeam.getLocation(i), this.heroTeam.getHero(i));
                    //this.map.place(this.location, this.team);
                }
                move();
            }
            else{
                return;
            }
        }
        catch(Exception e){
            ErrorMessage.printErrorInvalidInput();
        }
    }

    private boolean checkMove(char m, int location){
        if( m == up || m == UP || m == down || m == DOWN || m == left ||m == LEFT|| m == right || m == RIGHT) {
            if (m == up || m == UP) {
                return this.heroTeam.getLocation()
                return this.location > this.rowsize;
            }
            else if (m == down || m == DOWN) {
                return this.location <= (this.colsize - 1) * colsize || this.location > this.colsize * this.rowsize;
            }
            else if (m == left || m == LEFT) {
                return this.location % this.rowsize != 1;
            }
            else {
                return this.location % this.rowsize != 0;
            }
        }
        return false;
    }
    private int computeLocation(char m){
        if(m==up||m==UP||m==down||m==DOWN||m==left||m==LEFT||m==right||m==RIGHT) {
            if (m == up || m == UP) {
                if (!(this.location <= this.rowsize)) {
                    return this.location-this.rowsize;
                }
            }
            else if (m == down || m == DOWN) {
                if (!(this.location > (this.colsize - 1) * colsize && this.location <= this.colsize * this.rowsize)) {
                    return this.location+this.rowsize;
                }
            }
            else if (m == left || m == LEFT) {
                if (!(this.location % this.rowsize == 1)){
                    return this.location-1;
                }
            }
            else {
                if (!(this.location % this.rowsize == 0)) {
                    return this.location+1;
                }
            }
        }
        return 0;
    }
    private boolean validateTile(char m){
        if (this.map.getTile(computeLocation(m)) instanceof InaccessibleTile){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        TheQuestOfLegendsGameEngine game = new TheQuestOfLegendsGameEngine();
    }
}
