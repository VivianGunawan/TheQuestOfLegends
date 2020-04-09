package tile;

public abstract class Tile{
    // represent a blueprint for all types of tile
    // Fields
    private int location;
    private boolean active;
    //Constructor
    public Tile(){
        this.active = false;
    }
    //Mutator
    public void setLocation(int location){
        this.location = location;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    //Accessor
    public int getLocation(){
        return this.location;
    }
    public boolean isActive() {
        return active;
    }
}