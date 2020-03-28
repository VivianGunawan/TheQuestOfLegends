package character.hero;

import character.items.Item;
import character.items.ItemType;

import java.util.ArrayList;
import java.util.Iterator;

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
            if (temp.getI().getItemType()==ItemType.WEAPON) {
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
            if (temp.getI().getItemType()==ItemType.ARMOR) {
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
            if (temp.getI().getItemType()==ItemType.POTION) {
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
            if (temp.getI().getItemType()== ItemType.SPELL) {
                n++;
            }
        }
        return n;
    }
    public void maskInventoryWDisplay() {
        int i = 1;
        System.out.print("\033[0;1m" + "WEAPONS IN INVENTORY\n" + "\u001B[0m" + "---------------------------\n");
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while (itr.hasNext()) {
            ItemQuantity temp = itr.next();
            if (temp.getI().getItemType()==ItemType.WEAPON) {
                System.out.print("Item " + i + "\n" + "Quantity: " + temp.getQ() + "\n" + temp.getI().toString() + "\n---------------------------\n");
            }
            i++;
        }
    }
    public void maskInventoryADisplay(){
        int i = 1;
        System.out.print("\033[0;1m"+ "ARMORS IN INVENTORY\n"+ "\u001B[0m" +"---------------------------\n");
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while(itr.hasNext()){
            ItemQuantity temp = itr.next();
            if(temp.getI().getItemType()==ItemType.ARMOR) {
                System.out.print("Item " + i + "\n" + "Quantity: " + temp.getQ() + "\n"+ temp.getI().toString() +"\n---------------------------\n");
            }
            i++;
        }
    }
    public void maskInventoryPDisplay(){
        int i = 1;
        System.out.print("\033[0;1m"+ "POTIONS IN INVENTORY\n"+ "\u001B[0m" +"---------------------------\n");
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while(itr.hasNext()){
            ItemQuantity temp = itr.next();
            if(temp.getI().getItemType()==ItemType.POTION) {
                System.out.print("Item " + i + "\n" + "Quantity: " + temp.getQ() + "\n"+ temp.getI().toString() +"\n---------------------------\n");
            }
            i++;
        }
    }
    public void maskInventorySDisplay(){
        int i = 1;
        System.out.print("\033[0;1m"+ "SPELLS IN INVENTORY\n"+ "\u001B[0m" +"---------------------------\n");
        Iterator<ItemQuantity> itr = this.inventory.iterator();
        while(itr.hasNext()){
            ItemQuantity temp = itr.next();
            if(temp.getI().getItemType()==ItemType.SPELL) {
                System.out.print("Item " + i + "\n" + "Quantity: " + temp.getQ() + "\n"+ temp.getI().toString() +"\n---------------------------\n");
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
            out += "Item " + i + "\n" + temp.toString() + "\n---------------------------\n";
            i++;
        }
        return "\033[0;1m" + "INVENTORY\n" + "\u001B[0m" + "---------------------------\n" + out;
    }
}
