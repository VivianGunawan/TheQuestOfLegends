package character.merchant;

import character.Seller;
import character.items.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static util.IOConstants.DIVIDER;

public class Merchant extends character.Character implements Seller {
    // This represent the merchant that lives in a market tile
    // Fields
    private List<Item> items;
    private List<Item> availableItems;

    // Constructor
    public Merchant(List<Item>items){
        super("Merchant", 0); // MERCHANT NAME IS JUST MERCHANT AND IT IS LEVEL O-> HP REMAINS 0 AND DOESN'T LEVEL UP
        this.items= items;
        this.availableItems = new ArrayList<>();
    }

    // Only display character.items that are purchasable by heroes
    public void maskItems(int maxlvl) {
        this.availableItems = this.items.stream()
                .filter(item -> item.getReqLevel() <= maxlvl)
                .collect(Collectors.toList());

        System.out.println("========== LEVEL BASED PURCHASABLE ITEMS ===========");
        int i = 1;
        Iterator<Item> itr = this.availableItems.iterator();
        while (itr.hasNext()) {
            System.out.print("Item " + i + "\n" + itr.next().toString());
            System.out.println(DIVIDER);
            i++;
        }
    }
    @Override
    public List<Item> sell() {
        return this.availableItems;
    }
}
