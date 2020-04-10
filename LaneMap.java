import character.AttackResult;
import character.hero.Hero;
import character.items.spells.Spell;
import character.merchant.Merchant;
import tile.*;
import utils.ErrorMessage;

import java.util.*;
import java.util.stream.IntStream;

import static utils.IOConstants.*;
import static utils.IOConstants.CAST_INPUT;

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
        rowsize = laneLength + 2;
        colsize = (numLane * laneSize) + numLane - 1;
        this.map = new Tile[rowsize][colsize];

        setMap(merchant);
    }

    // Helper used to set the map
    private void setMap(Merchant merchant) {
        List<Tile> tileOptions = generateTileOptions();
        int colCounter = 0;
        for (int i = 0; i < rowsize; i++) {
            // first and last row, nexus cells
            if (i == 0 || i == rowsize - 1) {
                for (int j = 0; j < colsize; j++) {
                    if (colCounter < laneSize) {
                        this.map[i][j] = new NexusTile(merchant);
                        this.map[i][j].setLocation((i * rowsize) + j + 1);
                        colCounter++;
                    } else {
                        this.map[i][j] = new InaccessibleTile();
                        this.map[i][j].setLocation((i * rowsize) + j + 1);
                        colCounter = 0;
                    }
                }
                colCounter = 0;
            } else {
                // the rows in between the first and the last
                for (int j = 0; j < colsize; j++) {
                    if (colCounter < laneSize) {
                        this.map[i][j] = tileOptions.get((i * rowsize) + j);
                        this.map[i][j].setLocation((i * rowsize) + j + 1);
                        colCounter++;
                    } else {
                        this.map[i][j] = new InaccessibleTile();
                        this.map[i][j].setLocation((i * rowsize) + j + 1);
                        colCounter = 0;
                    }
                }
                colCounter = 0;
            }
        }
    }

    // Displays Map
    public void displayMap(){
        String border = "---+";
        System.out.println("+" + border.repeat(colsize));
        for (int i = 0; i < rowsize; i++) {
            for (int j = 0; j < colsize; j++) {
                System.out.print("|" + map[i][j].toString());
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

    /**
     * A place method for the QoL game
     */
    public void place(int location, Character character) {
        Tile desiredTile = this.map[(location-1)/this.rowsize][(location-1)%this.rowsize];
        desiredTile.setActive(true);
        // the place method for the heroes
        if (character.getClass().isInstance(Hero.class)) {
            if (surroundingTilesContainMonster(location).size()>0){


                if (desiredTile instanceof  NexusTile) {
                    // in a Nexus tile
                }
                else {

                    // ch
                }
            } else {
                // no battle, but you can do everything else
            }
        } else {
            // Place method for the monsters
        }
    }

    public Tile getTile(int location){
        return  this.map[(location-1)/this.rowsize][(location-1)%this.rowsize];
    }

    public List<Integer> surroundingTilesContainMonster(int location) {
        List<Integer> monsterLocations = new ArrayList<>();
        int tileRow = (location-1)/this.rowsize;
        int tileCol = (location-1)%this.rowsize;
        Tile desiredTile = this.map[tileRow][tileCol];
        List<Tile> tilesToCompare = new ArrayList<>();

        // condition for being on the left
        if (tileCol == 0) {
            // condition for being on the top
            if (tileRow == 0) {
                if (checkTile(location + 1) != 0) {
                    monsterLocations.add(location + 1);
                }
                if(checkTile(location + this.rowsize) != 0) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize + 1) != 0) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
            }
            // condition for being on the bottom
            else if(tileRow == this.rowsize + 1) {
                // condition
                if (checkTile(location + 1) != 0) {
                    monsterLocations.add(location + 1);
                }
                if(checkTile(location - this.rowsize) != 0) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize + 1) != 0) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
            } // condition for being on the middle
            else {
                if (checkTile(location + 1) != 0) {
                    monsterLocations.add(location + 1);
                }
                if(checkTile(location + this.rowsize) != 0) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize + 1) != 0) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
                if(checkTile(location - this.rowsize) != 0) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize +1) != 0) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
            }
        }
        // condition for being on the right
        else if (tileCol == colsize - 1) {
            // condition for being on the top
            if (tileRow == 0) {
                if (checkTile(location - 1) != 0) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location + this.rowsize) != 0) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize - 1) != 0) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
            } // condition for being on the bottom
            else if(tileRow == this.rowsize - 1) {
                if (checkTile(location - 1) != 0) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location - this.rowsize) != 0) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize - 1) != 0) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            } // condition for being in the middle
            else {
                if (checkTile(location - 1) != 0) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location + this.rowsize) != 0) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize - 1) != 0) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
                if(checkTile(location - this.rowsize) != 0) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize - 1) != 0) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            }
        }
        // condition for being in the middle
        else {
            // condition for being on the top
            if (tileRow == 0) {
                if (checkTile(location + 1) != 0) {
                    monsterLocations.add(location + 1);
                }
                if (checkTile(location - 1) != 0) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location + this.rowsize) != 0) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize + 1) != 0) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
                if (checkTile(location + this.rowsize - 1) != 0) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
            } // condition for being on the bottom
            else if(tileRow == this.rowsize - 1) {
                if (checkTile(location + 1) != 0) {
                    monsterLocations.add(location + 1);
                }
                if (checkTile(location - 1) != 0) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location - this.rowsize) != 0) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize + 1) != 0) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
                if (checkTile(location - this.rowsize - 1) != 0) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            } // condition for being on the middle
            else {
                if (checkTile(location + 1) != 0) {
                    monsterLocations.add(location + 1);
                }
                if (checkTile(location - 1) != 0) {
                    monsterLocations.add(location - 1);
                }
                if(checkTile(location + this.rowsize) != 0) {
                    monsterLocations.add(location + this.rowsize);
                }
                if (checkTile(location + this.rowsize + 1) != 0) {
                    monsterLocations.add(location + this.rowsize + 1);
                }
                if (checkTile(location + this.rowsize - 1) != 0) {
                    monsterLocations.add(location + this.rowsize - 1);
                }
                if(checkTile(location - this.rowsize) != 0) {
                    monsterLocations.add(location - this.rowsize);
                }
                if (checkTile(location - this.rowsize +1) != 0) {
                    monsterLocations.add(location - this.rowsize + 1);
                }
                if (checkTile(location - this.rowsize - 1) != 0) {
                    monsterLocations.add(location - this.rowsize - 1);
                }
            }
        }
        return  monsterLocations;
    }
    private int checkTile(int location){
        Tile tempTile = getTile(location);
        if (tempTile instanceof NexusTile){
            //check for monster
            NexusTile currentNexus = (NexusTile) tempTile;
            if (currentNexus.containsMonster()) {
                return location;
            }
        } else if (tempTile instanceof CaveTile) {
            //check for monster
            CaveTile currentCave = (CaveTile) tempTile;
            if (currentCave.containsMonster()) {
                return location;
            }
        } else if (tempTile instanceof KoulouTile) {
            //check for monster
            KoulouTile currentKoulou = (KoulouTile) tempTile;
            if (currentKoulou.containsMonster()) {
                return location;
            }
        } else if (tempTile instanceof BushTile) {
            //check for monster
            BushTile currentBush = (BushTile) tempTile;
            if (currentBush.containsMonster()) {
                return location;
            }
        } else if (tempTile instanceof PlainTile) {
            //check for monster
            PlainTile currentPlain = (PlainTile) tempTile;
            if (currentPlain.containsMonster()) {
                return location;
            }
        } else {
            return 0;
        }
        return 0;
    }
}
