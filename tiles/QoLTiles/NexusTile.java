package tiles.QoLTiles;

import character.merchant.Merchant;

import static tiles.QoLTiles.QoLTilesDefaults.NEXUS_TILE_COLOR;
import static util.ColouredOutputs.ANSI_RESET;

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
        return  NEXUS_TILE_COLOR + super.toString() + ANSI_RESET;
    }
}
