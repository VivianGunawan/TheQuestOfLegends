package questOfLegends;

import character.hero.Hero;
import character.merchant.Merchant;
import character.monster.Monster;
import tiles.*;
import tiles.QoLTiles.*;

import java.util.*;

import static questOfLegends.QoLDefaults.*;
import static src.util.ColouredOutputs.ANSI_RESET;
import static src.util.IOConstants.*;

public class LaneMap {
    // represents the map for The Quest of Legends
    private final int numLane;// 3
    private final int laneSize; // 2
    private final int laneLength; // number of rows, 6
    private final double probabilityPlain;
    private final double probabilityBush;
    private final double probabilityKoulou;
    private final double probabilityCave;
    Scanner scan = new Scanner(System.in);
    // The row size and column size will be calculated based on the number of lanes, lane size, and lane length of the game to make it dynamic
    private final int rowsize;
    private final int colsize;
    private final Tile[][] map;

    public LaneMap(int numLane, int laneSize, int laneLength, Merchant merchant, double probabilityPlain, double probabilityBush, double probabilityKoulou, double probabilityCave) {
        this.numLane = numLane;
        this.laneSize = laneSize;
        this.laneLength = laneLength;
        this.probabilityPlain = probabilityPlain;
        this.probabilityBush = probabilityBush;
        this.probabilityKoulou = probabilityKoulou;
        this.probabilityCave = probabilityCave;
        this.rowsize = laneLength + 2;
        this.colsize = (numLane * laneSize) + numLane - 1;
        this.map = new Tile[rowsize][colsize];
        setMap(merchant);
        System.out.println(NEXUS_TILE_COLOR + "    " + ANSI_RESET + " represents a Nexus Tile");
        System.out.println(PLAIN_TILE_COLOR + "    " + ANSI_RESET + " represents a Plain Tile");
        System.out.println(BUSH_TILE_COLOR + "    " + ANSI_RESET + " represents a Bush Tile");
        System.out.println(KOULOU_TILE_COLOR + "    " + ANSI_RESET + " represents a Koulou Tile");
        System.out.println(CAVE_TILE_COLOR + "    " + ANSI_RESET + " represents a Nexus Tile");
    }

    public int getRowSize() {
        return this.rowsize;
    }

    public int getColSize() {
        return this.colsize;
    }

    public Tile getTile(int location){
        return  this.map[(location-1)/this.colsize][(location-1)%this.colsize];
    }

    public int getNumLane() {
        return this.numLane;
    }

    // Helper used to set the map
    private void setMap(Merchant merchant) {
        List<Tile> tileOptions = generateTileOptions();
        Iterator<Tile> tileOptionsIterator = tileOptions.iterator();
        for(int r = 0 ; r<this.rowsize ; r++) {
            for( int c = 0; c<this.colsize; c++){
                if(c==laneSize || (c>this.laneSize && (c-this.laneSize)%(this.laneSize+1)==0)){
                    this.map[r][c]= new InaccessibleTile();
                }
                else{
                    if(r==0||r==this.rowsize-1){
                        this.map[r][c]= new NexusTile(merchant);
                    }
                    else{
                        if(tileOptionsIterator.hasNext()) {
                            this.map[r][c] = tileOptionsIterator.next();
                        }
                    }
                }
                this.map[r][c].setLocation((r*this.colsize)+c+1);
            }
        }
    }

    // Displays Map
    public void display(){
        System.out.println("======================= MAP ========================");
        String border = "----+";
        System.out.println("+" + border.repeat(colsize));
        for (int i = 0; i < rowsize; i++) {
            for (int j = 0; j < colsize; j++) {
                if (map[i][j].toString().length()==3){
                    System.out.print("| " + map[i][j].toString());
                }
                else{
                    System.out.print("|" + map[i][j].toString());
                }
            }
            System.out.print("|");
            System.out.println();
            System.out.println("+" + border.repeat(colsize));;
        }
    }

    // Helper method used to generate list of tiles with the appropriate probabilities
    private List<Tile> generateTileOptions() {
        List<Tile> tempTileList = new ArrayList<Tile>();
        int plain = (int) Math.round(this.probabilityPlain * rowsize * colsize);
        int bush = (int) Math.round(this.probabilityBush * rowsize * colsize);
        int koulou = (int) Math.round(this.probabilityKoulou * rowsize * colsize);
        int cave = (int) Math.round(this.probabilityCave * rowsize * colsize);
        int i = 0;
        while(i < rowsize * colsize) {
            if (i < plain) {
                tempTileList.add(new PlainTile());
            }
            else if (i >= plain && i < plain+bush) {
                tempTileList.add(new BushTile());
            }
            else if (i >= plain+bush && i < plain+bush+koulou) {
                tempTileList.add(new KoulouTile());
            }
            else if (i >= plain+bush+koulou && i < plain+bush+koulou+cave) {
                tempTileList.add(new CaveTile());
            }
            i++;
        }
        Collections.shuffle(tempTileList);
        return tempTileList;
    }

    // Retrieve locations of nexus
    public List<Integer> getHeroesNexus(){
        List<Integer> heroesNexus = new ArrayList<>();
        for(int i = this.colsize*(this.rowsize-1)+1; i< this.colsize*this.rowsize; i+= this.laneSize+1){
            heroesNexus.add(i);
        }
        return heroesNexus;
    }
    public List<Integer> getMonstersNexus(){
        List<Integer> monstersNexus = new ArrayList<>();
        for(int i = 1; i< this.colsize; i+= this.laneSize+1){
            monstersNexus.add(i);
        }
        return monstersNexus;
    }

    // Place hero on a tile
    public void placeHero(int location, Hero hero) {
        Tile currTile  = getTile(location);
        currTile.setContainsHero(true);
        display();
        if (currTile instanceof  NexusTile){
            System.out.println(hero.getName()+" has entered a Nexus Tile");
            System.out.println(DIVIDER);
            NexusTile currNexus = (NexusTile) currTile;
            Merchant merchant = currNexus.getMerchant();
            char opt = '\u0000';
            try{
                while (opt!= TRANSACTION_INPUT && opt!=EXPLORE_INPUT && opt!=NONE){
                    System.out.println("Would " + hero.getName() + " like to perform a transaction (" + TRANSACTION_INPUT +
                            ") or explore inventory (" + EXPLORE_INPUT + ") or none ("+ NONE +")");
                    opt = scan.next().charAt(0);
                    System.out.println(DIVIDER);
                }
            }catch (Exception e){
                src.util.ErrorMessage.printErrorInvalidInput();
            }
            while(opt!=NONE){
                if(opt==TRANSACTION_INPUT){
                    talkToMerchant(hero,merchant);
                    char syn = '\u0000';
                    try {
                        while(syn!=YES_INPUT && syn!=NO_INPUT){
                            System.out.println("Would " + hero.getName() + "like to interact with " + merchant.getName() + "again? (" + YES_INPUT + "/" + NO_INPUT + ")");
                            syn = scan.next().charAt(0);
                            System.out.println(DIVIDER);
                        }
                        if(syn==YES_INPUT){
                            talkToMerchant(hero,merchant);
                        }
                    } catch (InputMismatchException e) {
                        src.util.ErrorMessage.printErrorInvalidInput();
                    }
                    break;
                }
                if(opt==EXPLORE_INPUT){
                    hero.exploreInventory();
                    break;
                }
                try{
                    while (opt!= TRANSACTION_INPUT && opt!=SELL_INPUT && opt!=NONE){
                        System.out.print("Would you like to perform a transaction (" + TRANSACTION_INPUT +
                                ") or explore inventory (" + EXPLORE_INPUT + ") or none ("+ NONE +")");
                        opt = scan.next().charAt(0);
                        System.out.println(DIVIDER);
                    }
                }catch (Exception e) {
                    src.util.ErrorMessage.printErrorInvalidInput();
                }
            }
            return;
        }
        else if (currTile instanceof KoulouTile){
            hero.setStrength(hero.getStrength()+(hero.getStrength()*KOULOU_STRENGTH_MULTIPLIER));
        }
        else if (currTile instanceof BushTile){
            hero.setDexterity(hero.getDexterity()+(hero.getDexterity()*BUSH_DEXTERITY_MULTIPLIER));
        }
        else if(currTile instanceof CaveTile){
            hero.setAgility(hero.getAgility()+(hero.getAgility()*CAVE_AGILITY_MULTIPLIER));
        }
    }
    // Remove hero from a tile
    public void removeHero(int location, Hero hero) {
        Tile currTile  = getTile(location);
        currTile.setContainsHero(false);
        if (currTile instanceof KoulouTile){
            hero.setStrength(hero.getStrength()-(hero.getStrength()*KOULOU_STRENGTH_MULTIPLIER));
        }
        else if (currTile instanceof BushTile){
            hero.setDexterity(hero.getDexterity()-(hero.getDexterity()*BUSH_DEXTERITY_MULTIPLIER));
        }
        else if(currTile instanceof CaveTile){
            hero.setAgility(hero.getAgility()-(hero.getAgility()*CAVE_AGILITY_MULTIPLIER));
        }
    }
    // Place monster on a tile
    public void placeMonster(int location){
        BattleTile currTile  = (BattleTile) getTile(location);
        currTile.setContainsMonster(true);
    }
    // Remove monster from a tile
    public void removeMonster(int location){
        BattleTile currTile  = (BattleTile) getTile(location);
        currTile.setContainsMonster(false);
    }

    // returns nearby monster locations
    public List<Integer> surroundingTilesContainMonster(int location) {
        List<Integer> monsterLocations = new ArrayList<>();

        // condition for being on the left
        if ((location-1)%this.rowsize==0) {
            // condition for being on the top
            if ((location-1)/this.rowsize == 0) {
                if (checkTileMonster(location + 1) ) {
                    monsterLocations.add(location + 1);
                }
                if(checkTileMonster(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTileMonster(location + this.rowsize + 1) ) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
            }
            // condition for being on the bottom
            else if((location-1)/this.rowsize == this.rowsize - 1) {
                // condition
                if (checkTileMonster(location + 1) ) {
                    monsterLocations.add(location + 1);
                }
                if(checkTileMonster(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTileMonster(location - this.rowsize + 1) ) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
            } // condition for being on the middle
            else {
                if (checkTileMonster(location + 1) ) {
                    monsterLocations.add(location + 1);
                }
                if(checkTileMonster(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTileMonster(location + this.rowsize + 1) ) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
                if(checkTileMonster(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTileMonster(location - this.rowsize +1) ) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
            }
        }
        // condition for being on the right
        else if ((location-1)%this.rowsize == colsize - 1) {
            // condition for being on the top
            if ((location-1)/this.rowsize == 0) {
                if (checkTileMonster(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTileMonster(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTileMonster(location + this.rowsize - 1) ) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
            } // condition for being on the bottom
            else if((location-1)/this.rowsize == this.rowsize - 1) {
                if (checkTileMonster(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTileMonster(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTileMonster(location - this.rowsize - 1) ) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            } // condition for being in the middle
            else {
                if (checkTileMonster(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTileMonster(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTileMonster(location + this.rowsize - 1) ) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
                if(checkTileMonster(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTileMonster(location - this.rowsize - 1) ) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            }
        }
        // condition for being in the middle
        else {
            // condition for being on the top
            if ((location-1)/this.rowsize == 0) {
                if (checkTileMonster(location + 1) ) {
                    monsterLocations.add(location + 1);
                }
                if (checkTileMonster(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTileMonster(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTileMonster(location + this.rowsize + 1) ) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
                if (checkTileMonster(location + this.rowsize - 1) ) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
            } // condition for being on the bottom
            else if((location-1)/this.rowsize == this.rowsize - 1) {
                if (checkTileMonster(location + 1)) {
                    monsterLocations.add(location + 1);
                }
                if (checkTileMonster(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTileMonster(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTileMonster(location - this.rowsize + 1) ) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
                if (checkTileMonster(location - this.rowsize - 1) ) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            } // condition for being on the middle
            else {
                if (checkTileMonster(location + 1) ) {
                    monsterLocations.add(location + 1);
                }
                if (checkTileMonster(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTileMonster(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTileMonster(location + this.rowsize + 1) ) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
                if (checkTileMonster(location + this.rowsize - 1) ) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
                if(checkTileMonster(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTileMonster(location - this.rowsize +1) ) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
                if (checkTileMonster(location - this.rowsize - 1) ) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            }
        }
        return  monsterLocations;
    }
    private boolean checkTileMonster(int location){
        Tile tempTile = getTile(location);
        if (tempTile instanceof NexusTile){
            //check for monster
            NexusTile currentNexus = (NexusTile) tempTile;
            if (currentNexus.isContainsMonster()) {
                return true;
            }
        } else if (tempTile instanceof CaveTile) {
            //check for monster
            CaveTile currentCave = (CaveTile) tempTile;
            if (currentCave.isContainsMonster()) {
                return true;
            }
        } else if (tempTile instanceof KoulouTile) {
            //check for monster
            KoulouTile currentKoulou = (KoulouTile) tempTile;
            if (currentKoulou.isContainsMonster()) {
                return true;
            }
        } else if (tempTile instanceof BushTile) {
            //check for monster
            BushTile currentBush = (BushTile) tempTile;
            if (currentBush.isContainsMonster()) {
                return true;
            }
        } else if (tempTile instanceof PlainTile) {
            //check for monster
            PlainTile currentPlain = (PlainTile) tempTile;
            if (currentPlain.isContainsMonster()) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    // returns nearby hero locations
    public List<Integer> surroundingTilesContainHero(int location) {
        List<Integer> heroLocations = new ArrayList<>();

        // condition for being on the left
        if ((location-1)%this.rowsize==0) {
            // condition for being on the top
            if ((location-1)/this.rowsize == 0) {
                if (checkTileHero(location + 1) ) {
                    heroLocations.add(location + 1);
                }
                if(checkTileHero(location + this.rowsize) ) {
                    heroLocations.add(location + this.rowsize);
                }
                if (checkTileHero(location + this.rowsize + 1) ) {
                    heroLocations.add(location + this.rowsize + 1);
                }
            }
            // condition for being on the bottom
            else if((location-1)/this.rowsize == this.rowsize - 1) {
                // condition
                if (checkTileHero(location + 1) ) {
                    heroLocations.add(location + 1);
                }
                if(checkTileHero(location - this.rowsize) ) {
                    heroLocations.add(location - this.rowsize);
                }
                if (checkTileHero(location - this.rowsize + 1) ) {
                    heroLocations.add(location - this.rowsize + 1);
                }
            } // condition for being on the middle
            else {
                if (checkTileHero(location + 1) ) {
                    heroLocations.add(location + 1);
                }
                if(checkTileHero(location + this.rowsize) ) {
                    heroLocations.add(location + this.rowsize);
                }
                if (checkTileHero(location + this.rowsize + 1) ) {
                    heroLocations.add(location + this.rowsize + 1);
                }
                if(checkTileHero(location - this.rowsize) ) {
                    heroLocations.add(location - this.rowsize);
                }
                if (checkTileHero(location - this.rowsize +1) ) {
                    heroLocations.add(location - this.rowsize + 1);
                }
            }
        }
        // condition for being on the right
        else if ((location-1)%this.rowsize == colsize - 1) {
            // condition for being on the top
            if ((location-1)/this.rowsize == 0) {
                if (checkTileHero(location - 1) ) {
                    heroLocations.add(location - 1);
                }
                if(checkTileHero(location + this.rowsize) ) {
                    heroLocations.add(location + this.rowsize);
                }
                if (checkTileHero(location + this.rowsize - 1) ) {
                    heroLocations.add(location + this.rowsize - 1);
                }
            } // condition for being on the bottom
            else if((location-1)/this.rowsize == this.rowsize - 1) {
                if (checkTileHero(location - 1) ) {
                    heroLocations.add(location - 1);
                }
                if(checkTileHero(location - this.rowsize) ) {
                    heroLocations.add(location - this.rowsize);
                }
                if (checkTileHero(location - this.rowsize - 1) ) {
                    heroLocations.add(location - this.rowsize - 1);
                }
            } // condition for being in the middle
            else {
                if (checkTileHero(location - 1) ) {
                    heroLocations.add(location - 1);
                }
                if(checkTileHero(location + this.rowsize) ) {
                    heroLocations.add(location + this.rowsize);
                }
                if (checkTileHero(location + this.rowsize - 1) ) {
                    heroLocations.add(location + this.rowsize - 1);
                }
                if(checkTileHero(location - this.rowsize) ) {
                    heroLocations.add(location - this.rowsize);
                }
                if (checkTileMonster(location - this.rowsize - 1) ) {
                    heroLocations.add(location - this.rowsize - 1);
                }
            }
        }
        // condition for being in the middle
        else {
            // condition for being on the top
            if ((location-1)/this.rowsize == 0) {
                if (checkTileHero(location + 1) ) {
                    heroLocations.add(location + 1);
                }
                if (checkTileHero(location - 1) ) {
                    heroLocations.add(location - 1);
                }
                if(checkTileHero(location + this.rowsize) ) {
                    heroLocations.add(location + this.rowsize);
                }
                if (checkTileHero(location + this.rowsize + 1) ) {
                    heroLocations.add(location + this.rowsize + 1);
                }
                if (checkTileHero(location + this.rowsize - 1) ) {
                    heroLocations.add(location + this.rowsize - 1);
                }
            } // condition for being on the bottom
            else if((location-1)/this.rowsize == this.rowsize - 1) {
                if (checkTileHero(location + 1)) {
                    heroLocations.add(location + 1);
                }
                if (checkTileHero(location - 1) ) {
                    heroLocations.add(location - 1);
                }
                if(checkTileHero(location - this.rowsize) ) {
                    heroLocations.add(location - this.rowsize);
                }
                if (checkTileHero(location - this.rowsize + 1) ) {
                    heroLocations.add(location - this.rowsize + 1);
                }
                if (checkTileHero(location - this.rowsize - 1) ) {
                    heroLocations.add(location - this.rowsize - 1);
                }
            } // condition for being on the middle
            else {
                if (checkTileHero(location + 1) ) {
                    heroLocations.add(location + 1);
                }
                if (checkTileHero(location - 1) ) {
                    heroLocations.add(location - 1);
                }
                if(checkTileHero(location + this.rowsize) ) {
                    heroLocations.add(location + this.rowsize);
                }
                if (checkTileHero(location + this.rowsize + 1) ) {
                    heroLocations.add(location + this.rowsize + 1);
                }
                if (checkTileHero(location + this.rowsize - 1) ) {
                    heroLocations.add(location + this.rowsize - 1);
                }
                if(checkTileHero(location - this.rowsize) ) {
                    heroLocations.add(location - this.rowsize);
                }
                if (checkTileHero(location - this.rowsize +1) ) {
                    heroLocations.add(location - this.rowsize + 1);
                }
                if (checkTileHero(location - this.rowsize - 1) ) {
                    heroLocations.add(location - this.rowsize - 1);
                }
            }
        }
        return  heroLocations;
    }
    private boolean checkTileHero(int location){
        Tile tempTile = getTile(location);
        if (tempTile instanceof NexusTile){
            //check for monster
            NexusTile currentNexus = (NexusTile) tempTile;
            if (currentNexus.isContainsHero()) {
                return true;
            }
        } else if (tempTile instanceof CaveTile) {
            //check for monster
            CaveTile currentCave = (CaveTile) tempTile;
            if (currentCave.isContainsHero()) {
                return true;
            }
        } else if (tempTile instanceof KoulouTile) {
            //check for monster
            KoulouTile currentKoulou = (KoulouTile) tempTile;
            if (currentKoulou.isContainsHero()) {
                return true;
            }
        } else if (tempTile instanceof BushTile) {
            //check for monster
            BushTile currentBush = (BushTile) tempTile;
            if (currentBush.isContainsHero()) {
                return true;
            }
        } else if (tempTile instanceof PlainTile) {
            //check for monster
            PlainTile currentPlain = (PlainTile) tempTile;
            if (currentPlain.isContainsHero()) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    private void talkToMerchant (Hero hero, Merchant merchant) {
        merchant.maskItems(hero.getLevel());
        System.out.println("Would " + hero.getName() + " like to buy or sell an item? (" + BUY_INPUT + "/" + SELL_INPUT + ")");
        char bs = '\u0000';
        try {
            bs = scan.next().charAt(0);
            System.out.println(DIVIDER);
            while (bs != BUY_INPUT && bs != SELL_INPUT) {
                System.out.println("Would " + hero.getName() + " like to buy or sell an item? (" + BUY_INPUT + "/" + SELL_INPUT + ")");
                bs = scan.next().charAt(0);
                System.out.println(DIVIDER);
            }
            if (bs == BUY_INPUT) {
                hero.buy(merchant.sell());
            }
            else if(bs == SELL_INPUT){
                hero.sell();
            }
            char yn = '\u0000';
            try {
                while (yn != YES_INPUT && yn != NO_INPUT) {
                    System.out.println("Is " + hero.getName() + " done talking to merchant? (y/n)");
                    yn = scan.next().charAt(0);
                    System.out.println(DIVIDER);
                }
                if (yn == YES_INPUT) {
                    return;
                }
                else {
                    talkToMerchant(hero, merchant);
                }
            } catch (InputMismatchException e) {
                src.util.ErrorMessage.printErrorInvalidInput();
            }
        } catch (InputMismatchException e) {
            src.util.ErrorMessage.printErrorInvalidInput();
        }
    }
}
