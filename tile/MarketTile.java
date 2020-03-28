package tile;

import character.merchant.Merchant;
import static utils.ColouredOutputs.*;

public class MarketTile extends Tile {
    //represents a market tile
    // Fields
    private final Merchant merchant;

    // Constructor
    public MarketTile(Merchant merchant) {
        super(TileType.M);
        this.merchant = merchant;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    @Override
    public String toString() {
        if (this.isActive()) {
            return (ANSI_BRIGHT_BG_BLACK + ANSI_BRIGHT_BG_YELLOW + super.toString() + " *" + ANSI_RESET);
        } else {
            return ANSI_BRIGHT_BG_BLACK + ANSI_BRIGHT_BG_YELLOW + " " + super.toString() + " " + ANSI_RESET;
        }
    }
}