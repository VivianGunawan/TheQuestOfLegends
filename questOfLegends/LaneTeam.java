package questOfLegends;

import character.Character;
import character.hero.Hero;
import character.monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class LaneTeam {
    // Represent Team of Heroes or Monsters
    private final List<Character> team;
    private final List<Integer> lane ;
    private final List<Integer> location;
    public LaneTeam(){
        this.team = new ArrayList<>();
        this.lane = new ArrayList<>();
        this.location = new ArrayList<>();
    }
    public void addMember(Character character, int lane, int location){
        this.team.add(character);
        this.lane.add(lane);
        this.location.add(location);
        assert(this.team.size()==this.lane.size()||this.lane.size()==this.location.size());
    }
    public void removeMember(int index){
        this.team.remove(index);
        this.lane.remove(index);
        this.location.remove(index);
    }
    public int size(){
        return this.team.size();
    }
    public int getLane(int index){
        return this.lane.get(index);
    }
    public int getLocation(int index){
        return this.location.get(index);
    }

    public void setLocation(int index, int location) {
        this.location.set(index, location);
    }
    public void setLane(int index, int lane) {
        this.lane.set(index, lane);
    }

    public int getMaxLevel() {
        int maxLevel = 0;
        for(int i=0; i<this.team.size();i++){
            if(this.team.get(i).getLevel()>maxLevel){
                maxLevel = this.team.get(i).getLevel();
            }
        }
        return maxLevel;
    }
    public Hero getHero(int index){
        return (Hero) this.team.get(index);
    }
    public int getHeroIndexByLocation(int location){
            return this.location.indexOf(location);
    }

    public Monster getMonster(int index){
        return (Monster) this.team.get(index);
    }
    public int getMonsterIndexByLocation(int location){
        return this.location.indexOf(location);
    }
}
