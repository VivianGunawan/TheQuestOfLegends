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
    public Monster getMonster(int index){
        return (Monster) this.team.get(index);
    }
    public int getLane(int index){
        return this.lane.get(index);
    }
    public int getLocation(int index){
        return this.location.get(index);
    }
}
