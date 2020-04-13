package quest;


import character.hero.Hero;
import character.merchant.Merchant;
import character.monster.Monster;
import character.AttackResult;
import character.items.spells.Spell;

import java.util.*;
import java.util.stream.IntStream;

import util.ErrorMessage;
import static quest.QuestDefaults.*;
import static util.IOConstants.*;
import static util.ColouredOutputs.*;

public class Team{
    // Represents team of heroes
    // Fields
    private final List<Hero> team;
    Scanner scan = new Scanner(System.in);
    // Constructor
    public Team(List<Hero> team){
        this.team = team;
    }

    // Accessors
    // return number of team members
    public int getnC(){
        return this.team.size();
    }
    // return level of highest leveled  hero in the team
    public int getMaxLevel(){
        int maxLvl = 0;
        for(int i=0; i<this.team.size();i++){
            if (this.team.get(i).getLevel()>maxLvl){
                maxLvl = this.team.get(i).getLevel();
            }
        }
        return maxLvl;
    }

    // Display
    private void display(){
        System.out.println("================== HEROES IN TEAM ==================");
        for (int i =0; i<this.team.size(); i++){
            System.out.println("HERO ID: " + (i+1));
            System.out.print(this.team.get(i));
            System.out.println(DIVIDER);
        }
    }

    // Explore Inventory Menu
    public void exploreInventory(){
        this.display();
        System.out.println("Which hero's inventory in your team would you like to check? (ID #)");
        int heroId = 0;
        try{
            heroId = scan.nextInt();
            scan.nextLine();
            while(heroId<1||heroId>this.team.size()){
                ErrorMessage.printErrorOutOfRange();
                System.out.println("Please enter valid ID");
                heroId = scan.nextInt();
                scan.nextLine();
            }
        }
        catch(InputMismatchException e){
            ErrorMessage.printErrorInvalidInput();
        }
        Hero temp = (Hero) this.team.get(heroId-1);
        temp.exploreInventory();
        char yn ='\u0000';
        try {
            while(yn!=YES_INPUT&&yn!=NO_INPUT){
                System.out.println("Would you like to check another hero's inventory? (" + YES_INPUT + "/" + NO_INPUT + ")");
                yn = scan.next().charAt(0);
                System.out.println(DIVIDER);
            }
            if(yn==YES_INPUT){
                exploreInventory();
            }
            else if(yn ==NO_INPUT){
                return;
            }
        } catch (InputMismatchException e) {
            ErrorMessage.printErrorInvalidInput();
        }
    }
    // Interact Menu
    public void transaction(Merchant merchant){
            this.display();
            System.out.println("Which hero's would like to talk to merchant? (HERO ID #)");
            int heroId = 0;
            try{
                heroId = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
                while(heroId<1||heroId>this.team.size()){
                    ErrorMessage.printErrorOutOfRange();
                    System.out.println("Please enter valid ID");
                    heroId = scan.nextInt();
                    System.out.println(DIVIDER);
                    scan.nextLine();
                }
            }
            catch(InputMismatchException e){
                ErrorMessage.printErrorInvalidInput();
            }
            Hero temp = this.team.get(heroId-1);
            talkToMerchant(temp,merchant);
            char syn = '\u0000';
            try {
                while(syn!=YES_INPUT && syn!=NO_INPUT){
                    System.out.println("Would you like to interact with " + merchant.getName() + "(" + YES_INPUT + "/" + NO_INPUT + ")");
                    syn = scan.next().charAt(0);
                    System.out.println(DIVIDER);
                }
                if(syn==YES_INPUT){
                    transaction(merchant);
                }
                else if(syn==NO_INPUT){
                    return;
                }
            } catch (InputMismatchException e) {
                ErrorMessage.printErrorInvalidInput();
            }
    }
    // In Battle
    public void battle(List<Monster> enemies) {
        displayEnemies(enemies);
        System.out.println("================== BATTLE BEGINS ===================");
        int round = 1;
        // per round
        while (!defeatedMonsters(enemies) && !everyoneFainted()) {
            System.out.println("ROUND: " + round);
            for (int i = 0; i < this.team.size(); i++) {
                Hero currHero = this.team.get(i);
                // Hero's turn
                if (currHero.getHealthPower()>0){
                    System.out.println(currHero.getName() + "'s turn");
                    char opt = '\u0000';
                    try {
                        while (opt != CHANGE_INPUT && opt != USE_POTION_INPUT && opt != ATTACK_INPUT && opt != CAST_INPUT && opt!=INFO && opt!=info ) {
                            System.out.println("Would " + currHero.getName() +
                                    " change weapon or armor (" + CHANGE_INPUT + ") or " +
                                    " use potion (" + USE_POTION_INPUT + ") or " +
                                    "attack monster (" + ATTACK_INPUT + ") or " +
                                    "cast spell (" + CAST_INPUT + ") (" + info +"/" + INFO +")");
                            opt = scan.next().charAt(0);
                            System.out.println(DIVIDER);
                        }
                        if(opt==info||opt==INFO){
                            System.out.print(currHero.battleDisplay());
                            displayEnemies(enemies);
                            System.out.println("What would you like to do during this turn");
                            try{
                                while (opt != CHANGE_INPUT && opt != USE_POTION_INPUT && opt != ATTACK_INPUT && opt != CAST_INPUT) {
                                    System.out.println("Would " + currHero.getName() +
                                            " change weapon or armor (" + CHANGE_INPUT + ") or " +
                                            " use potion (" + USE_POTION_INPUT + ") or " +
                                            "attack monster (" + ATTACK_INPUT + ") or " +
                                            "cast spell (" + CAST_INPUT + ")");
                                    opt = scan.next().charAt(0);
                                    System.out.println(DIVIDER);
                                }
                            }catch (Exception e){
                                ErrorMessage.printErrorInvalidInput();
                            }
                        }
                        if (opt == CHANGE_INPUT) {
                            currHero.change();
                            currHero.battleDisplay();
                        }
                        if (opt == USE_POTION_INPUT) {
                            if(currHero.getInventory().numPotions()!=0){
                                currHero.usePotion();
                                currHero.battleDisplay();
                            }
                            else{
                                System.out.println(currHero.getName() + " has no potion to consume");
                                System.out.println(currHero.getName() + "'s turn is wasted.");
                            }

                        }
                        if (opt == ATTACK_INPUT) {
                            AttackResult res = enemies.get(i).receiveBasicAttack(currHero.doBasicAttack());
                            switch (res) {
                                case DEAD:
                                    int monsterId = 0;
                                    displayEnemies(enemies);
                                    try {
                                        while (monsterId < 1 || monsterId > enemies.size()) {
                                            System.out.println(currHero.getName() + "has defeated her/his monster, which other monster would " +
                                                    currHero.getName() + "like to attack (monster #)");
                                            monsterId = scan.nextInt();
                                            scan.nextLine();
                                            try{
                                                if (enemies.get(monsterId-1).getHealthPower()==0){
                                                    monsterId = 0;
                                                }
                                            }catch(Exception o) { ErrorMessage.printErrorOutOfRange(); }
                                        }
                                        AttackResult sres = enemies.get(monsterId - 1).receiveBasicAttack(currHero.doBasicAttack());
                                        if (sres == AttackResult.DODGE) {
                                            System.out.println(enemies.get(monsterId - 1).getName() + " dodged " + currHero.getName() + "'s attack");
                                        }
                                        else if (sres == AttackResult.SUCCESS) {
                                            System.out.print(" from " + currHero.getName() + "\n");
                                            System.out.println(enemies.get(monsterId - 1));
                                        }
                                        else if (sres == AttackResult.KILL) {
                                            System.out.println(currHero.getName() + " killed " + enemies.get(monsterId - 1).getName());
                                            if(defeatedMonsters(enemies)) {
                                                break;
                                            }
                                        }
                                    } catch (InputMismatchException e) {
                                        ErrorMessage.printErrorInvalidInput();
                                    }
                                    break;
                                case DODGE:
                                    System.out.println(enemies.get(i).getName() + " dodged " + currHero.getName() + "'s attack");
                                    break;
                                case SUCCESS:
                                    System.out.print(" from " + currHero.getName() + "\n");
                                    System.out.println(enemies.get(i));
                                    break;
                                case KILL:
                                    System.out.println(currHero.getName() + " killed " + enemies.get(i).getName());
                                    if(defeatedMonsters(enemies)){
                                        break;
                                    }
                                    break;
                            }
                        }
                        if (opt == CAST_INPUT) {
                            if(currHero.getInventory().numSpell()!=0) {
                                Spell casted = currHero.castSpell();
                                AttackResult res = enemies.get(i).receiveSpell(casted, currHero.castSpellDamage(casted));
                                switch (res) {
                                    case DEAD:
                                        int monsterId = 0;
                                        displayEnemies(enemies);
                                        try {
                                            while (monsterId < 1 || monsterId > enemies.size()) {
                                                System.out.println(currHero.getName() + "has defeated her/his monster, which other monster would " +
                                                        currHero.getName() + "like to cast spell on? (monster #)");
                                                monsterId = scan.nextInt();
                                                scan.nextLine();
                                                try {
                                                    if (enemies.get(monsterId - 1).getHealthPower() == 0) {
                                                        monsterId = 0;
                                                    }
                                                } catch (Exception o) {
                                                    ErrorMessage.printErrorOutOfRange();
                                                }
                                            }
                                            AttackResult sres = enemies.get(monsterId-1).receiveSpell(casted, currHero.castSpellDamage(casted));
                                            if (sres == AttackResult.DODGE) {
                                                System.out.println(enemies.get(monsterId - 1).getName() + " dodged " + currHero.getName() + "'s spell");
                                                System.out.println(enemies.get(monsterId - 1));
                                            } else if (sres == AttackResult.SUCCESS) {
                                                System.out.print(" from " + currHero.getName() + "\n");
                                                System.out.println(enemies.get(monsterId - 1));
                                            } else if (sres == AttackResult.KILL) {
                                                System.out.println(currHero.getName() + " killed " + enemies.get(monsterId - 1).getName());
                                                if (defeatedMonsters(enemies)) {
                                                    break;
                                                }
                                            }
                                        } catch (InputMismatchException e) {
                                            ErrorMessage.printErrorInvalidInput();
                                        }
                                        break;
                                    case DODGE:
                                        System.out.println(enemies.get(i).getName() + " dodged " + currHero.getName() + "'s attack");
                                        break;
                                    case SUCCESS:
                                        System.out.print(" from " + currHero.getName() + "\n");
                                        System.out.println(enemies.get(i));
                                        break;
                                    case KILL:
                                        System.out.println(currHero.getName() + " killed " + enemies.get(i).getName());
                                        if (defeatedMonsters(enemies)) {
                                            break;
                                        }
                                        break;
                                }
                            }
                            else{
                                System.out.println(currHero.getName() + "has no spell to cast");
                                System.out.println(currHero.getName() + "'s turn is wasted.");
                            }
                        }
                    } catch (Exception e) {
                        ErrorMessage.printErrorInvalidInput();
                    }
                    System.out.println(DIVIDER);
                }
                // enemy's turn
                if (enemies.get(i).getHealthPower() > 0) {
                    Monster currMonster = enemies.get(i);
                    System.out.println(currMonster.getName() + "'s turn");
                    AttackResult res = this.team.get(i).receiveBasicAttack(currMonster.doBasicAttack());
                    switch (res){
                        case DEAD:
                            OptionalInt index = IntStream.range(0, this.team.size())
                                .filter(idx -> this.team.get(idx).getHealthPower() > 0)
                                .findFirst();
                            try {
                                AttackResult sres = this.team.get(index.getAsInt()).receiveBasicAttack(currMonster.doBasicAttack());
                                if (sres == AttackResult.DODGE) {
                                    System.out.println(this.team.get(index.getAsInt()).getName() + " dodged " + currMonster.getName() + "'s attack");
                                    this.team.get(index.getAsInt()).battleDisplay();
                                }
                                else if (sres == AttackResult.SUCCESS) {
                                    System.out.print(" from " + currMonster.getName() + "\n");
                                    this.team.get(index.getAsInt()).battleDisplay();
                                }
                                else if (sres == AttackResult.KILL) {
                                    System.out.println(currMonster.getName() + " killed " + this.team.get(index.getAsInt()).getName());
                                    if(everyoneFainted()) {
                                        break;
                                    }
                                }
                            } catch (NoSuchElementException e) {
                                System.out.println("All heroes are dead");
                                break;
                            }
                            break;
                        case DODGE:
                            System.out.println(this.team.get(i).getName() + " dodged " + currMonster.getName()  + "'s attack");
                            break;
                        case SUCCESS:
                            System.out.print(" from " + currMonster.getName() + "\n");
                            this.team.get(i).battleDisplay();
                            break;
                        case KILL:
                            System.out.println(currMonster.getName() + " killed " + this.team.get(i).getName());
                            if(everyoneFainted()){
                                break;
                            }
                            break;
                    }
                }
            }
            // Post-round regeneration
            for (int j=0; j<team.size(); j++){
                if(this.team.get(j).getHealthPower()>0) {
                    this.team.get(j).regen(HP_REGEN, MANA_REGEN);
                }
            }
            round++;
            System.out.println(DIVIDER);
        }
        if (defeatedMonsters(enemies)) {
            endBattle(true);
        }
        else {
            endBattle(false);
        }
        return;
    }
    // Helpers
    private boolean everyoneFainted(){
        for(int i=0; i<this.team.size();i++){
            if (this.team.get(i).getHealthPower()>0){
                return false;
            }
        }
        return true;
    }
    private boolean defeatedMonsters(List<Monster> enemies){
        for(int i=0; i<enemies.size();i++){
            if (enemies.get(i).getHealthPower()>0){
                return false;
            }
        }
        return  true;
    }
    private void displayEnemies(List<Monster> enemies){
        System.out.println("================= MONSTERS AWAKE ===================");
        int i = 1;
        Iterator<Monster> itr = enemies.iterator();
        while (itr.hasNext()) {
            Monster temp =  itr.next();
            if (temp.getHealthPower()>0) {
                System.out.println(ANSI_BOLD + "MONSTER " + i );
                System.out.print(temp);
                System.out.println(DIVIDER);
            }
            i++;
        }
    }
    private void endBattle(boolean win){
        if(win){
            System.out.println("You Won this Battle");
            int monsterLevel = getMaxLevel();
            for(int i=0; i<this.team.size(); i++){
                // Reward hero that don't faint
                Hero currHero = this.team.get(i);
                if(currHero.getHealthPower()!=0){
                    System.out.println(currHero.getName() + " received $" + BOUNTY_MULTIPLIER*monsterLevel);
                    currHero.setMoney(currHero.getMoney()+(BOUNTY_MULTIPLIER*monsterLevel));
                    System.out.println(currHero.getName() + " gained " + BOUNTY_EXP + " experience points"  );
                    currHero.setExperience(currHero.getExperience()+ BOUNTY_EXP);
                }
                // revive hero
                else{
                    System.out.println("Reviving "+ currHero.getName()+"...");
                    currHero.revive(WIN_REVIVE_HP_MULTIPLIER);
                }
            }
        }
        else{
            System.out.println("You Lost this Battle");
            for(int i=0; i<this.team.size(); i++){
                Hero currHero = this.team.get(i);
                System.out.println(currHero.getName() + " lost $" + currHero.getMoney()* TAX_MULTIPLIER);
                currHero.setMoney(currHero.getMoney()* TAX_MULTIPLIER);
                System.out.println("Reviving "+ currHero.getName()+"...");
                currHero.revive(LOSE_REVIVE_HP_MULTIPLIER);
            }
        }
    }
    private void talkToMerchant (Hero hero, Merchant merchant) {
        merchant.maskItems(hero.getLevel());
        System.out.println("Would " + hero.getName() + " like to buy or sell an item? (" + BUY_INPUT + "/" + SELL_INPUT + ")");
        char bs = '\u0000';
        try {
            bs = scan.next().charAt(0);
            System.out.println(DIVIDER);
            while (bs != BUY_INPUT && bs != SELL_INPUT) {
                System.out.println("Would " + hero.getName() + " like to buy or sell an item? (" + BUY_INPUT + "/" + SELL_INPUT + ")");
                bs = scan.next().charAt(0);
                System.out.println(DIVIDER);
            }
            if (bs == BUY_INPUT) {
                hero.buy(merchant.sell());
            }
            else if(bs == SELL_INPUT){
                hero.sell();
            }
            char yn = '\u0000';
            try {
                while (yn != YES_INPUT && yn != NO_INPUT) {
                    System.out.println("Is " + hero.getName() + " done talking to merchant? (y/n)");
                    yn = scan.next().charAt(0);
                    System.out.println(DIVIDER);
                }
                if (yn == YES_INPUT) {
                    return;
                }
                else {
                    talkToMerchant(hero, merchant);
                }
            } catch (InputMismatchException e) {
                ErrorMessage.printErrorInvalidInput();
            }
        } catch (InputMismatchException e) {
            ErrorMessage.printErrorInvalidInput();
        }
    }


}