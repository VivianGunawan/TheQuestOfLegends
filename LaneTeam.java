import character.hero.Hero;

import java.util.List;

public class LaneTeam {
    // Represent Team of Heroes or Monsters
    private final List<? extends character.Character> team;

    public LaneTeam(List<? extends character.Character> team) {
        this.team = team;
    }
}
