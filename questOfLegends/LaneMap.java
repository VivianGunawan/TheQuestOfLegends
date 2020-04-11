package questOfLegends;

import character.hero.Hero;
import character.merchant.Merchant;
import character.monster.Monster;
import tiles.*;
import tiles.QoLTiles.*;

import java.util.*;

public class LaneMap {
    // represents the map for The Quest of Legends
    private final int numLane;// 3
    private final int laneSize; // 2
    private final int laneLength; // number of rows, 6
    private final double probabilityPlain;
    private final double probabilityBush;
    private final double probabilityKoulou;
    private final double probabilityCave;
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
    }

    public Integer getRowSize() {
        return this.rowsize;
    }

    public Integer getColSize() {
        return this.getColSize();
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

    // Place hero on the map
    public void placeHero(int location, Hero hero) {
        Tile currTile  = this.map[(location-1)/this.colsize][(location-1)%this.colsize];
        currTile.setContainsHero(true);
    }
    // Place monster on the
    public void placeMonster(int location, Monster monster){
        BattleTile currTile  = (BattleTile) this.map[(location-1)/this.colsize][(location-1)%this.colsize];
        currTile.setContainsMonster(true);
    }

    public Tile getTile(int location){
        return  this.map[(location-1)/this.rowsize][(location-1)%this.rowsize];
    }

    public List<Integer> surroundingTilesContainMonster(int location) {
        List<Integer> monsterLocations = new ArrayList<>();

        // condition for being on the left
        if ((location-1)%this.rowsize==0) {
            // condition for being on the top
            if ((location-1)/this.rowsize == 0) {
                if (checkTile(location + 1) ) {
                    monsterLocations.add(location + 1);
                }
                if(checkTile(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize + 1) ) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
            }
            // condition for being on the bottom
            else if((location-1)/this.rowsize == this.rowsize + 1) {
                // condition
                if (checkTile(location + 1) ) {
                    monsterLocations.add(location + 1);
                }
                if(checkTile(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize + 1) ) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
            } // condition for being on the middle
            else {
                if (checkTile(location + 1) ) {
                    monsterLocations.add(location + 1);
                }
                if(checkTile(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize + 1) ) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
                if(checkTile(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize +1) ) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
            }
        }
        // condition for being on the right
        else if ((location-1)%this.rowsize == colsize - 1) {
            // condition for being on the top
            if ((location-1)/this.rowsize == 0) {
                if (checkTile(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize - 1) ) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
            } // condition for being on the bottom
            else if((location-1)/this.rowsize == this.rowsize - 1) {
                if (checkTile(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize - 1) ) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            } // condition for being in the middle
            else {
                if (checkTile(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize - 1) ) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
                if(checkTile(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize - 1) ) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            }
        }
        // condition for being in the middle
        else {
            // condition for being on the top
            if ((location-1)/this.rowsize == 0) {
                if (checkTile(location + 1) ) {
                    monsterLocations.add(location + 1);
                }
                if (checkTile(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize + 1) ) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
                if (checkTile(location + this.rowsize - 1) ) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
            } // condition for being on the bottom
            else if((location-1)/this.rowsize == this.rowsize - 1) {
                if (checkTile(location + 1)) {
                    monsterLocations.add(location + 1);
                }
                if (checkTile(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize + 1) ) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
                if (checkTile(location - this.rowsize - 1) ) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            } // condition for being on the middle
            else {
                if (checkTile(location + 1) ) {
                    monsterLocations.add(location + 1);
                }
                if (checkTile(location - 1) ) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location + this.rowsize) ) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize + 1) ) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
                if (checkTile(location + this.rowsize - 1) ) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
                if(checkTile(location - this.rowsize) ) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize +1) ) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
                if (checkTile(location - this.rowsize - 1) ) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            }
        }
        return  monsterLocations;
    }
    private boolean checkTile(int location){
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
}
