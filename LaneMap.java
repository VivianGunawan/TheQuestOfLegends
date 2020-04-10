import character.merchant.Merchant;
import character.monster.Monster;
import tile.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public LaneMap(int numLane, int laneSize, int laneLength, Merchant merchant, List<? extends Monster> monsters, double probabilityPlain, double probabilityBush, double probabilityKoulou, double probabilityCave) {
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

        setMap(merchant, monsters);
    }

    // Helper used to set the map
    private void setMap(Merchant merchant, List<? extends Monster> monsters) {
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
}
