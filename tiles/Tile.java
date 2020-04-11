package tiles;

public abstract class Tile{
    // represent a blueprint for all types of tile
    // Fields
    private int location;
    private boolean containsHero;
    //Constructor
    public Tile(){
        this.containsHero = false;
    }
    //Mutator
    public void setLocation(int location){
        this.location = location;
    }
    public void setContainsHero(boolean containsHero) {
        this.containsHero = containsHero;
    }
    //Accessor
    public int getLocation(){
        return this.location;
    }
    public boolean isContainsHero() {
        return containsHero;
    }

    @Override
    public String toString() {
        if(this.containsHero){
            return "*";
        }
        else{
            return " ";
        }
    }
}