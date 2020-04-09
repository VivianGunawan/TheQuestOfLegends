import character.merchant.Merchant;
import character.monster.Monster;
import tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class LaneMap {
    // represents the map for The Quest of Legends
    private final int rowsize;
    private final int colsize;
    private final int numLane;
    private final int laneSize;
    private final double probabilityPlain;
    private final double probabilityBush;
    private final double probabilityKoulou;
    private final double probabilityCave;
    private final Tile[][] map;

    public LaneMap(int rowsize, int colsize, int numLane, int laneSize, Merchant merchant, List<? extends Monster> monsters, double probabilityPlain, double probabilityBush, double probabilityKoulou, double probabilityCave) {
        this.rowsize = rowsize;
        this.colsize = colsize;
        this.numLane = numLane;
        this.laneSize = laneSize;
        this.probabilityPlain = probabilityPlain;
        this.probabilityBush = probabilityBush;
        this.probabilityKoulou = probabilityKoulou;
        this.probabilityCave = probabilityCave;
        this.map = new Tile[rowsize][colsize];
        setMap(merchant, monsters);
    }

    // Helper used to set the map
    private void setMap(Merchant merchant, List<? extends Monster> monsters) {

    }

    // Helper method used to generate list of tiles with the appropriate probabilities
    private List<Tile> generateTileOptions(Merchant merchant, List<? extends Monster> monsters) {
        List<Tile> tempTileList = new ArrayList<Tile>();
        int plain = (int) Math.round(this.probabilityPlain * this.rowsize * this.colsize);
        int bush = (int) Math.round(this.probabilityBush * this.rowsize * this.colsize);
        int koulou = (int) Math.round(this.probabilityKoulou * this.rowsize * this.colsize);
        int cave = (int) Math.round(this.probabilityCave * this.rowsize * this.colsize);
        int i = 0;
        while(i < this.rowsize * this.colsize) {
            if (i < plain) {
                tempTileList.add(new )
            }
        }
    }


}
