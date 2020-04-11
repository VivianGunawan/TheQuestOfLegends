package quest;

import character.merchant.Merchant;
import character.hero.Hero;
import character.monster.Monster;
import tiles.InaccessibleTile;

import src.util.ErrorMessage;
import static src.util.GameInputs.*;
import static src.util.IOConstants.*;
import static quest.QuestDefaults.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;



public class TheQuestGameEngine {
    // This class serves as the game engine for the quest
    // Fields
    private final int rowsize;
    private final int colsize;
    private final double probabilityInaccessible;
    private final double probabilityMarket;
    private final double probabilityCommon;
    private final double probabilityEncounter;
    private final Merchant merchant;
    private final List<? extends Hero> heroes;
    private final List<? extends Monster> monsters;
    private final Map map;
    private final Team team;
    private int location;
    Scanner scan = new Scanner(System.in);

    // Constructor
    // Default NoArg Constructor -> Used for starting new games from specifications in default
    public TheQuestGameEngine() {
        this.rowsize = DEFAULT_ROW_SIZE;
        this.colsize = DEFAULT_COL_SIZE;
        this.probabilityInaccessible = DEFAULT_PROBABILITY_INACCESSIBLE;
        this.probabilityMarket = DEFAULT_PROBABILITY_MARKET;
        this.probabilityCommon = DEFAULT_PROBABILITY_COMMON;
        this.probabilityEncounter = DEFAULT_PROBABILITY_ENCOUNTER;
        this.merchant = DEFAULT_MERCHANT;
        this.heroes = DEFAULT_HEROES;
        this.monsters =DEFAULT_MONSTERS;
        this.map = new Map(this.rowsize, this.colsize, this.probabilityInaccessible, this.probabilityMarket, this.probabilityCommon, this.merchant, this.monsters, this.probabilityEncounter);
        System.out.println("=============== WELCOME TO THE QUEST ===============");
        this.map.display();
        System.out.println("================== TEAM SELECTION ==================");
        this.team = selectTeam();
        startGame();
    }
    // Methods
    // Prompts user for number of team members
    private int numTeam(){
        int nC = 0;
        System.out.println("How many heroes do you want in your team ? (1-3)");
        try {
            nC = scan.nextInt();
            System.out.println(DIVIDER);
            scan.nextLine();
            while (nC < 1 || nC > 3) {
                ErrorMessage.printErrorOutOfRange();
                System.out.println("Please enter valid amount of team members (1-3)");
                nC = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
            }
        } catch (InputMismatchException e) {
            ErrorMessage.printErrorInvalidInput();
        }
        return nC;
    }
    // Prompts user to select a team of heroes
    private Team selectTeam() {
        int nC = numTeam();
        List<Hero> team = new ArrayList<Hero>();
        System.out.println("================== HERO SELECTION ==================");
        for (int i = 0; i < this.heroes.size(); i++) {
            System.out.println("HERO ID: " + (i + 1));
            System.out.print(this.heroes.get(i));
            System.out.println(DIVIDER);
        }
        for (int j = 0; j < nC; j++) {
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
                while (index < 1 || index > this.heroes.size() || team.contains(temp)) {
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
                team.add(temp);
                System.out.println(temp.getName() + " successfully added to team");
                System.out.println(DIVIDER);
            } catch (Exception e) {
                ErrorMessage.printErrorInvalidInput();
            }
        }
        return new Team(team);
    }
    /* Starts game
    places team of hero on top left market tile
    facilitates map traversal as long as user does not quit the game
    */
    private void startGame() {
        System.out.println("================== STARTING GAME ===================");
        this.location = this.map.getSafeStart();
        this.map.place(this.location, this.team);
        move();
    }
    private void move(){
        char m = '\u0000';
        try{
            while(!(checkMove(m)&&validateTile(m))&&!(m==quit||m==QUIT)){
                System.out.println("Where would you like to move your hero? ");
                System.out.println("up:(" + up + "/" + UP +
                                    ") down:("+ down + "/" + DOWN +
                                    ") left:("+ left + "/" + LEFT +
                                    ") right:("+ right + "/" + RIGHT +
                                    ") quit:(" + quit + "/" + QUIT + ")"
                                    );
                m = scan.next().charAt(0);
                System.out.println(DIVIDER);
            }
            if(!(m==quit||m==QUIT)){
                this.location = computeLocation(m);
                this.map.place(this.location, this.team);
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
    private boolean checkMove(char m){
        if(m==up||m==UP||m==down||m==DOWN||m==left||m==LEFT||m==right||m==RIGHT) {
            if (m == up || m == UP) {
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
        TheQuestGameEngine game = new TheQuestGameEngine();
    }
}

