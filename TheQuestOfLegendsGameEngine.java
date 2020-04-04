import character.Team;
import character.hero.Hero;
import character.merchant.Merchant;
import character.monster.Monster;
import utils.ErrorMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utils.Defaults.*;

public class TheQuestOfLegendsGameEngine {
    private final int numLane;
//    private final int laneSize;
//    private final Merchant merchant;
//    private finals List<? extends Monster> monsters;
//    private final LaneMap map;
    private final List<? extends Hero> heroes;
    private final LaneTeam heroTeam;
    Scanner scan = new Scanner(System.in);

    public TheQuestOfLegendsGameEngine() {
        this.numLane = DEFAULT_LANE;
//        this.laneSize = DEFAULT_LANE_SIZE;
//        this.merchant = DEFAULT_MERCHANT;
        this.heroes = DEFAULT_HEROES;
//        this.monsters =DEFAULT_MONSTERS;
//        this.map = new LaneMap(numLane, laneSize,  this.merchant, this.monsters);
        this.heroTeam = selectTeam();
//        startGame();
    }

    private LaneTeam selectTeam() {
        List<Hero> team = new ArrayList<Hero>();
        System.out.println("Hero Selection");
        for (int i = 0; i < this.heroes.size(); i++) {
            System.out.println("HERO ID: " + (i + 1));
            System.out.println(this.heroes.get(i));
        }
        for (int j = 0; j < numLane; j++) {
            character.hero.Hero temp = this.heroes.get(0);
            System.out.println("Please select HERO ID for team member " + (j + 1));
            try {
                int index = scan.nextInt();
                scan.nextLine();
                try {
                    temp = this.heroes.get(index - 1);
                } catch (Exception o) {
                    System.out.println("HERO ID entered out of range");
                }
                while (index < 1 || index > this.heroes.size() || team.contains(temp)) {
                    System.out.println("Please select a different HERO ID");
                    index = scan.nextInt();
                    scan.nextLine();
                    try {
                        temp = this.heroes.get(index - 1);
                    } catch (Exception o) {
                        System.out.println("HERO ID entered out of range");
                    }
                }
                team.add(temp);
                System.out.println(temp.getName() + " successfully added to team");
            } catch (Exception e) {
                ErrorMessage.printErrorInvalidInput();
            }
        }
        return new LaneTeam(team);
    }
    public static void main(String[] args) {
        TheQuestOfLegendsGameEngine game = new TheQuestOfLegendsGameEngine();
    }
}
