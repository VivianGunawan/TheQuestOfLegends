package tiles;

import character.merchant.Merchant;
import static src.util.ColouredOutputs.*;

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
        if (this.isContainsHero()) {
            return (ANSI_BRIGHT_BG_BLACK + ANSI_BRIGHT_BG_YELLOW + "M *" + ANSI_RESET);
        } else {
            return ANSI_BRIGHT_BG_BLACK + ANSI_BRIGHT_BG_YELLOW + " M " + ANSI_RESET;
        }
    }
}