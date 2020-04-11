package tiles.QoLTiles;

import character.merchant.Merchant;

import static src.util.ColouredOutputs.*;
import static src.util.ColouredOutputs.ANSI_RESET;

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
