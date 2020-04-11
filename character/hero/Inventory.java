package character.hero;

import character.items.Item;
import character.items.armors.Armor;
import character.items.potions.Potion;
import character.items.spells.Spell;
import character.items.weapons.Weapon;

import java.util.ArrayList;
import java.util.Iterator;

import static src.util.IOConstants.DIVIDER;

public class Inventory {
    //Fields
    private ArrayList<ItemQuantity> inventory;

    //Instance methods
    //Constructor
    public Inventory() {
        this.inventory = new ArrayList<ItemQuantity>();
    }

    public void clearInventory() {
        this.inventory.clear();
    }

    public void addItem(Item item, int pq) {
        ItemQuantity temp = new ItemQuantity(item, pq);
        if (this.inventory.contains(temp)) {
            this.inventory.get(this.inventory.indexOf(temp)).addQ(pq);
        } else {
            this.inventory.add(temp);
        }
    }

    public void removeItem(Item item, int rq) {
        ItemQuantity temp = new ItemQuantity(item, rq);
        if (this.inventory.contains(temp)) {
            this.inventory.get(this.inventory.indexOf(temp)).remQ(rq);
            if (this.inventory.get(this.inventory.indexOf(temp)).getQ() == 0) {
                this.inventory.remove(temp);
            }
        }
    }

    public Item getItem(int index) {
        return this.inventory.get(index).getI();
    }

    public int getQuantity(int index) {
        return this.inventory.get(index).getQ();
    }

    public int numItems() {
        return this.inventory.size();
    }

    public int numWeapons() {
        int n = 0;
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while (itr.hasNext()) {
            ItemQuantity temp = itr.next();
            if (temp.getI() instanceof Weapon) {
                n++;
            }
        }
        return n;
    }
    public int numArmors(){
        int n = 0;
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while (itr.hasNext()) {
            ItemQuantity temp = itr.next();
            if (temp.getI() instanceof Armor) {
                n++;
            }
        }
        return n;
    }
    public int numPotions(){
        int n = 0;
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while (itr.hasNext()) {
            ItemQuantity temp = itr.next();
            if (temp.getI() instanceof Potion) {
                n++;
            }
        }
        return n;
    }
    public int numSpell(){
        int n = 0;
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while (itr.hasNext()) {
            ItemQuantity temp = itr.next();
            if (temp.getI() instanceof Spell) {
                n++;
            }
        }
        return n;
    }
    public void maskInventoryWDisplay() {
        int i = 1;
        System.out.println("=============== WEAPONS IN INVENTORY ===============");
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while (itr.hasNext()) {
            ItemQuantity temp = itr.next();
            if (temp.getI() instanceof Weapon) {
                System.out.print("Item " + i + "\n" + "Quantity: " + temp.getQ() + "\n" + temp.getI().toString());
                System.out.println(DIVIDER);
            }
            i++;
        }
    }
    public void maskInventoryADisplay(){
        int i = 1;
        System.out.println("================ ARMORS IN INVENTORY ===============");
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while(itr.hasNext()){
            ItemQuantity temp = itr.next();
            if (temp.getI() instanceof Armor) {
                System.out.print("Item " + i + "\n" + "Quantity: " + temp.getQ() + "\n" + temp.getI().toString());
                System.out.println(DIVIDER);
            }
            i++;
        }
    }
    public void maskInventoryPDisplay(){
        int i = 1;
        System.out.println("=============== POTIONS IN INVENTORY ===============");
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while(itr.hasNext()){
            ItemQuantity temp = itr.next();
            if(temp.getI() instanceof Potion) {
                System.out.print("Item " + i + "\n" + "Quantity: " + temp.getQ() + "\n" + temp.getI().toString());
                System.out.println(DIVIDER);
            }
            i++;
        }
    }
    public void maskInventorySDisplay(){
        int i = 1;
        System.out.println("================ SPELLS IN INVENTORY ===============");
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while(itr.hasNext()){
            ItemQuantity temp = itr.next();
            if(temp.getI() instanceof Spell) {
                System.out.print("Item " + i + "\n" + "Quantity: " + temp.getQ() + "\n" + temp.getI().toString());
                System.out.println(DIVIDER);
            }
            i++;
        }
    }

    @Override
    public String toString() {
        int i = 1;
        String out = new String();
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while (itr.hasNext()) {
            ItemQuantity temp = itr.next();
            out += "Item " + i + "\n" + temp.toString() + DIVIDER + "\n";
            i++;
        }
        return ("==================== INVENTORY =====================" + "\n" + out);
    }
}
