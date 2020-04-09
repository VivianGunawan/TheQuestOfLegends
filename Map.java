import character.Team;
import character.merchant.Merchant;
import character.monster.Monster;
import tile.*;
import utils.ErrorMessage;

import java.util.*;

import static utils.IOConstants.*;

public class Map{
    // represent the map of the game play
    // Fields
    private final int rowsize;
    private final int colsize;
    private final Tile[][] map;
    private final double probabilityInaccessible;
    private final double probabilityMarket;
    private final double probabilityCommon;
    Scanner scan = new Scanner(System.in);
    // Constructors
    public Map(int rowsize, int colsize, double probabilityInaccessible, double probabilityMarket, double probabilityCommon, Merchant merchant, List<? extends Monster> monsters, double probabilityEncounter){
        this.rowsize = rowsize;
        this.colsize = colsize;
        this.map = new Tile[rowsize][colsize];
        this.probabilityInaccessible = probabilityInaccessible;
        this.probabilityMarket = probabilityMarket;
        this.probabilityCommon = probabilityCommon;
        setMap(merchant,monsters,probabilityEncounter);
    }
    // Methods
    // Set tiles of the map with the appropriate probabilities through using genOptions

    public Tile getTile(int location){
        return  this.map[(location-1)/this.rowsize][(location-1)%this.rowsize];
    }
    // Method to return the top left first market occurrence
    public int getSafeStart(){
        for(int r=0; r<this.rowsize; r++){
            for(int c = 0; c<this.colsize; c++){
                if(this.map[r][c] instanceof MarketTile){
                    return this.map[r][c].getLocation();
                }
            }
        }
        return 0;
    }
    // Displays Map
    public void display(){
        String border = "---+";
        System.out.println("+"+border.repeat(this.colsize));
        for (int i = 0; i < this.rowsize; i++) {
            for (int j = 0; j < this.colsize; j++) {
                System.out.print("|"+map[i][j].toString());
            }
            System.out.print("|");
            System.out.println();
            System.out.println("+"+border.repeat(this.colsize));;
        }
    }
    public void place(int location, Team heroes){
        Tile t = this.map[(location-1)/this.rowsize][(location-1)%this.rowsize];
        t.setActive(true);
        if(t instanceof MarketTile){
            MarketTile currentMarket = (MarketTile) t;
            System.out.println("You have entered a market tile");
            char yn = '\u0000';
            display();
            try{
                while(yn != YES_INPUT && yn != NO_INPUT){
                    System.out.println("Would you like to leave in current tile? (" + YES_INPUT + "/" +NO_INPUT + ")");
                    yn = scan.next().charAt(0);
                }
                while(yn == NO_INPUT){
                    char opt = '\u0000';
                    try{
                        while (opt!= TRANSACTION_INPUT && opt!=EXPLORE_INPUT && opt!=NONE){
                            System.out.println("Would you like to perform a transaction (" + TRANSACTION_INPUT +
                                    ") or explore inventory (" + EXPLORE_INPUT + ") or none ("+ NONE +")");
                            opt = scan.next().charAt(0);
                        }
                    }catch (Exception e){
                        ErrorMessage.printErrorInvalidInput();
                    }
                    while(opt!=NONE){
                        if(opt==TRANSACTION_INPUT){
                            heroes.transaction(currentMarket.getMerchant());
                            break;
                        }
                        if(opt==EXPLORE_INPUT){
                            heroes.exploreInventory();
                            break;
                        }
                        try{
                            while (opt!= TRANSACTION_INPUT && opt!=SELL_INPUT && opt!=NONE){
                                System.out.print("Would you like to perform a transaction (" + TRANSACTION_INPUT +
                                        ") or explore inventory (" + EXPLORE_INPUT + ") or none ("+ NONE +")");
                                opt = scan.next().charAt(0);
                            }
                        }catch (Exception e) {
                            ErrorMessage.printErrorInvalidInput();
                        }
                    }
                    display();
                    System.out.println("Would you like to leave current tile? (" + YES_INPUT + "/" +NO_INPUT + ")");
                    try {
                        yn = scan.next().charAt(0);
                        while( yn != YES_INPUT && yn != NO_INPUT){
                            yn = scan.next().charAt(0);
                        }
                    } catch (InputMismatchException e) {
                        ErrorMessage.printErrorInvalidInput();
                    }
                    if(yn == YES_INPUT){
                        break;
                    }
                }
                t.setActive(false);
            }catch (InputMismatchException e){
                ErrorMessage.printErrorInvalidInput();
            }
        }
        else if(t instanceof CommonTile){
            CommonTile currentCommon = (CommonTile) t;
            System.out.println("You have entered a common tile");
            if(currentCommon.monsterAwake()){
                System.out.println("You have awaken the monsters that dwell within this tile");
                List<Monster> enemies = currentCommon.getMonsters(heroes.getnC(),heroes.getMaxLevel());
                heroes.battle(enemies);
            }
            char ync = '\u0000';
            display();
            try {
                while (ync != YES_INPUT && ync != NO_INPUT) {
                    System.out.println("Would you like to leave current tile? (" + YES_INPUT + "/" + NO_INPUT + ")");
                    ync = scan.next().charAt(0);
                }
                while (ync == NO_INPUT) {
                    heroes.exploreInventory();
                    display();
                    System.out.println("Would you like to leave current tile? (" + YES_INPUT + "/" + NO_INPUT + ")");
                    try {
                        ync = scan.next().charAt(0);
                        while (ync != YES_INPUT && ync != NO_INPUT) {
                            ync = scan.next().charAt(0);
                        }
                    } catch (InputMismatchException e) {
                        ErrorMessage.printErrorInvalidInput();
                    }
                    if (ync == YES_INPUT) {
                        break;
                    }
                }
                t.setActive(false);
            }catch (InputMismatchException e){
                ErrorMessage.printErrorInvalidInput();
            }
        }
    }
    //Helper
    private void setMap(Merchant merchant, List<? extends Monster> monsters, double probabilityEncounter){
        List<Tile> options = genOptions(merchant, monsters, probabilityEncounter);
        for (int i = 0; i < this.rowsize; i++) {
            for (int j = 0; j < this.colsize; j++) {
                this.map[i][j] = options.get((i*this.rowsize)+j);
                this.map[i][j].setLocation((i*this.rowsize)+j+1);
            }
        }
    }
    // Generate list of tiles with the appropriate probabilities
    private List<Tile> genOptions(Merchant merchant, List<? extends Monster> monsters, double probabilityEncounter){
        List<Tile> temp = new ArrayList<Tile>();
        int inaccessible = (int) Math.round(this.probabilityInaccessible * this.rowsize * this.colsize);
        int market = (int) Math.round(this.probabilityMarket * this.rowsize * this.colsize);
        int common = (int) Math.round(this.probabilityCommon* this.rowsize * this.colsize);
        int i = 0;
        while(i<this.rowsize*this.colsize){
            if(i<inaccessible){
                temp.add(new InaccessibleTile());
            }
            else if (i>=inaccessible && i<inaccessible+market){
                temp.add(new MarketTile(merchant));
            }
            else if(i>=inaccessible+market && i<inaccessible+market+common){
                temp.add(new CommonTile(monsters, probabilityEncounter));
            }
            i++;
        }
        Collections.shuffle(temp);
        return temp;
    }
}