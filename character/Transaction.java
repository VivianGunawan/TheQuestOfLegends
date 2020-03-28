package character;

import character.items.Item;

import java.util.List;

public interface Transaction {
    void buy(List<Item> items); // No Operations in Merchant
    List<Item> sell();
}
