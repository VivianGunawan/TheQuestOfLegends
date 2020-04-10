package tile;

import character.merchant.Merchant;

import static utils.ColouredOutputs.*;
import static utils.ColouredOutputs.ANSI_RESET;

public class NexusTile extends Tile {

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
        if (this.isActive()) {
            return (ANSI_BLACK + ANSI_BRIGHT_BG_YELLOW +  "N *" + ANSI_RESET);
        } else {
            return ANSI_BLACK + ANSI_BRIGHT_BG_YELLOW + " N " + ANSI_RESET;
        }
    }
}
