package tile;

public abstract class Tile{
    // represent a blueprint for all types of tile
    // Fields
    private int location;
    private TileType type;
    private boolean active;
    //Constructor
    public Tile(TileType type){
        this.type = type;
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
    public TileType getType(){
        return this.type;
    }
    public int getLocation(){
        return this.location;
    }
    public boolean isActive() {
        return active;
    }
    @Override
    public String toString() {
        return(this.type.name());
    }
}