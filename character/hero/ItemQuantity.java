package character.hero;

import character.items.Item;

public class ItemQuantity{
    // Fields
    private Item i;
    private int q;
    public ItemQuantity(Item i, int q){
        this.i=i;
        this.q=q;
    }
    public Item getI() {
        return i;
    }
    public int getQ() {
        return q;
    }
    public void addQ(int pq) {
        this.q += pq;
    }
    public void remQ(int rq){
        this.q -= rq;
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemQuantity)) {
            return false;
        }
        ItemQuantity other = (ItemQuantity) o;
        return (this.getI().getName().equalsIgnoreCase(other.getI().getName()));
    }

    @Override
    public String toString() {
        return(
                "Quantity: " + this.q + "\n" +
                this.i.toString()
        );
    }
}
