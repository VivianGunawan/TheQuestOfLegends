package character.items;

public abstract class Item{
    //represents a blueprint for all the possible character.items
    //Fields
    private String name;
    private double price;
    private int reqLevel;
    // Instance Methods
    // Constructor
    public Item(String name, double price, int reqLevel){
        this.name = name;
        this.price = price;
        this.reqLevel = reqLevel;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getReqLevel() {
        return reqLevel;
    }
//    public ItemType getItemType() {
//        return itemType;
//    }

    @Override
    public String toString(){
        return(this.getClass().getSimpleName() + "\n" +
            "Name: " + this.name + "\n" +
            "Price: $" + this.price + "\n" +
            "Minimum level Required: " + this.reqLevel + "\n"    
        );
    }
}