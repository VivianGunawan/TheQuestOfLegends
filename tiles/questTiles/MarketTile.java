package tiles.questTiles;

import character.merchant.Merchant;
import tiles.Tile;

import static tiles.questTiles.questTilesDefaults.MARKET_TILE_COLOR;
import static util.ColouredOutputs.ANSI_RESET;

public class MarketTile extends Tile {
    //represents a market tile
    // Fields
    private final Merchant merchant;

    // Constructor
    public MarketTile(Merchant merchant) {
        this.merchant = merchant;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    @Override
    public String toString() {
        return MARKET_TILE_COLOR + " " + super.toString() + " " + ANSI_RESET;
    }
}