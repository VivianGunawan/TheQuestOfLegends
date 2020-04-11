package tile.QoLTiles;

import character.merchant.Merchant;
import tile.MarketTile;
import tile.Tile;

import static utils.ColouredOutputs.*;
import static utils.ColouredOutputs.ANSI_RESET;

public class NexusTile extends BattleTile {
    //represents a market tile
    // Fields
    private final Merchant merchant;

    // Constructor
    public NexusTile(Merchant merchant) {
        this.merchant = merchant;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    @Override
    public String toString() {
        return ANSI_BLACK + ANSI_BRIGHT_BG_BLUE + super.toString() +ANSI_RESET;
    }
}
