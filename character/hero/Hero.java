package character.hero;

import character.*;
import character.items.Item;
import character.items.armors.Armor;
import character.items.potions.*;
import character.items.spells.Spell;
import character.items.weapons.Weapon;

import util.ErrorMessage;
import static util.IOConstants.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public abstract class Hero extends character.Character implements HeroBattle, Buyer, Seller {
    // represents hero in the game, could be a warrior,sorcerer or paladin.
    // Fields
    private double experience;
    private double mana;
    private double strength;
    private double agility;
    private double dexterity;
    private double money;
    private int hands;
    private Inventory inventory;
    private List<ItemQuantity> weapons; // equipped weapons
    private Armor armor; // equipped armor
    Scanner scan = new Scanner(System.in);

    // Constructor
    public Hero(String name, int experience, double mana, double strength, double agility, double dexterity, double money, int hands) {
        super(name, 1);
        this.experience = experience;
        this.mana = mana;
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.money = money;
        this.hands = hands;
        this.weapons = new ArrayList<ItemQuantity>();
        this.armor = new Armor(null,0,0,0);
        this.inventory = new Inventory();
    }

    // Methods
    // Accessors
    public double getExperience() {
        return experience;
    }
    public double getStrength() {
        return strength;
    }
    public double getAgility() {
        return agility;
    }
    public double getDexterity() {
        return dexterity;
    }
    public double getMoney() {
        return money;
    }
    public Inventory getInventory() {
        return inventory;
    }

    // Mutators
    public void setMoney(double money) {
        this.money = money;
    }
    public void setExperience(double experience) {
        this.experience = experience;
        if(this.experience>this.getLevel()* CharacterDefaults.EXP_MULTIPLIER) {
            levelUp();
        }
    }
    public void setStrength(double strength) {
        this.strength = strength;
    }
    public void setAgility(double agility) {
        this.agility = agility;
    }
    public void setDexterity(double dexterity) {
        this.dexterity = dexterity;
    }

    // Explore Inventory
    public void exploreInventory(){
        System.out.println("Explore "+ this.getName() +"'s Inventory");
        System.out.print(this.inventory);
        char opt = '\u0000';
        try {
            System.out.println("Would " + this.getName()+" like to change weapon (" + CHANGE_WEAPON_INPUT + ") or change armor (" + CHANGE_ARMOR_INPUT + ") or consume potion (" + CONSUME_POTION_INPUT + ")? (n for none)");
            while(opt!=CHANGE_WEAPON_INPUT && opt!=CHANGE_ARMOR_INPUT && opt!=CONSUME_POTION_INPUT && opt!=NONE){
                opt = scan.next().charAt(0);
                System.out.println(DIVIDER);
            }
            if (opt == CHANGE_WEAPON_INPUT){
                this.changeWeapon();
            }
            else if (opt == CHANGE_ARMOR_INPUT){
                this.changeArmor();
            }
            else if (opt == CONSUME_POTION_INPUT){
                this.consumePotions();
            }
            else if (opt == NONE){
                return;
            }
            System.out.println("Is " + this.getName() + " done exploring inventory? (" +YES_INPUT + "/" + NO_INPUT + ")");
            char yn = '\u0000';
            try{
                while(yn!=YES_INPUT && yn!=NO_INPUT){
                    yn = scan.next().charAt(0);
                    System.out.println(DIVIDER);
                }
                if (yn==YES_INPUT){
                    return;
                }
                else{
                    exploreInventory();
                }
            }catch(InputMismatchException e){
                ErrorMessage.printErrorInvalidInput();
            }
        } catch (InputMismatchException e) {
            ErrorMessage.printErrorInvalidInput();
        }
    }

    // Buyer Interface Method
    public void buy(List<Item> availableItems){
        if(this.money!=0){
            buyItem(availableItems);
            char yn = '\u0000';
            try{
                while (yn!=YES_INPUT&&yn!=NO_INPUT){
                    System.out.println("Would "+ this.getName() + " like to purchase more items? (" + YES_INPUT + "/" + NO_INPUT + ")" );
                    yn = scan.next().charAt(0);
                    System.out.println(DIVIDER);
                }
                if (yn==YES_INPUT){
                    buy(availableItems);
                }
            }catch (Exception e){
                ErrorMessage.printErrorInvalidInput();
            }
        }
        else{
            System.out.println(this.getName() + " ran out of money");
        }
    }
    // Seller Interface Method
    public List<Item> sell(){
        if(this.inventory.numItems()!=0){
            sellItem();
            char yn = '\u0000';
            try{
                while (yn!=YES_INPUT&&yn!=NO_INPUT){
                    System.out.println("Would "+ this.getName() + " like to sell more character? (" + YES_INPUT + "/" + NO_INPUT + ")" );
                    yn = scan.next().charAt(0);
                    System.out.println(DIVIDER);
                }
                if (yn==YES_INPUT){
                    sellItem();
                }
            }catch (Exception e){
                ErrorMessage.printErrorInvalidInput();
            }
        }
        else{
            System.out.println(this.getName() + " has no item in inventory");
        }
        return null;
    }

    // Battle interface Methods
    public void change(){
        System.out.println(this.getName() +"'s Inventory");
        System.out.print(this.inventory);
        char opt = '\u0000';
        try {
            System.out.println("Would " + this.getName()+" like to change weapon (" + CHANGE_WEAPON_INPUT + ") or change armor (" + CHANGE_ARMOR_INPUT + ")? (n for none)");
            while(opt!=CHANGE_WEAPON_INPUT && opt!=CHANGE_ARMOR_INPUT && opt!=NONE){
                opt = scan.next().charAt(0);
                System.out.println(DIVIDER);
            }
            if (opt == CHANGE_WEAPON_INPUT){
                this.changeWeapon();
            }
            else if (opt == CHANGE_ARMOR_INPUT){
                this.changeArmor();
            }
            else if (opt == NONE){
                return;
            }
            System.out.println("Is " + this.getName() + " done changing weapons or armor? (" +YES_INPUT + "/" + NO_INPUT + ")");
            char yn = '\u0000';
            try{
                while(yn!=YES_INPUT && yn!=NO_INPUT){
                    yn = scan.next().charAt(0);
                    System.out.println(DIVIDER);
                }
                if (yn==YES_INPUT){
                    return;
                }
                else{
                    change();
                }
            }catch(InputMismatchException e){
                ErrorMessage.printErrorInvalidInput();
            }
        } catch (InputMismatchException e) {
            ErrorMessage.printErrorInvalidInput();
        }
    }
    public void usePotion(){
        if (this.inventory.numPotions()!=0){
            this.inventory.maskInventoryPDisplay();
            int potionIndex = 0;
            try{
                while(potionIndex<1||potionIndex>this.inventory.numItems()||!(this.inventory.getItem(potionIndex-1) instanceof Potion)){
                    System.out.println("Which potion would " + this.getName() +" like to consume? (item #)");
                    potionIndex = scan.nextInt();
                    System.out.println(DIVIDER);
                    scan.nextLine();
                    System.out.println(DIVIDER);
                }
                consume((Potion) this.inventory.getItem(potionIndex-1));
            }catch (InputMismatchException e){
                ErrorMessage.printErrorInvalidInput();
            }
        }
        else{
            System.out.println(this.getName() + "has no potions");
        }
    }
    public double doBasicAttack() {
        return (this.strength+weaponDamage())* CharacterDefaults.STRENGTH_MULTIPLIER;
    }
    public AttackResult receiveBasicAttack(double damage) {
        if (this.getHealthPower()==0){
            return AttackResult.DEAD;
        }
        else{
            if( Math.random() < this.agility* CharacterDefaults.AGILITY_MULTIPLIER){
                return AttackResult.DODGE;
            }
            else{
                double effectiveDamage = 0;
                if(damage - this.armor.getDamageReduction()>0){
                    effectiveDamage = damage - this.armor.getDamageReduction();
                }
                if(this.getHealthPower()-(effectiveDamage)<=0){
                    this.setHealthPower(0);
                    return  AttackResult.KILL;
                }
                else{
                    this.setHealthPower(this.getHealthPower()-(effectiveDamage));
                    System.out.print(this.getName() + " received " + effectiveDamage + " damage");
                }
                return AttackResult.SUCCESS;
            }
        }
    }
    public Spell castSpell() {
        if(this.inventory.numSpell()==0){
            System.out.print(this.getName() + "has no spell to cast");
            return null;
        }
        else {
            this.inventory.maskInventorySDisplay();
            int spellIndex =0;
            try {
                System.out.println("Which spell would " + this.getName() + " like to cast? (item #)");
                spellIndex = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
                while ((spellIndex < 1 || spellIndex > this.inventory.numItems()) || !(this.inventory.getItem(spellIndex - 1) instanceof Spell)) {
                    ErrorMessage.printErrorOutOfRange();
                    System.out.println("Please enter valid item #");
                    spellIndex = scan.nextInt();
                    System.out.println(DIVIDER);
                    scan.nextLine();
                }
            } catch (InputMismatchException e) {
                ErrorMessage.printErrorInvalidInput();
            }
            Item tempS = this.inventory.getItem(spellIndex- 1);
            Spell spell = (Spell) tempS;
            if (this.mana - spell.getMana()<0){
                System.out.println(this.getName() + " doesn't have enough mana to cast this spell");
                return null;
            }
            else{
                this.mana -= spell.getMana();
                this.inventory.removeItem(tempS, 1);
                return spell;
            }
        }
    }
    public double castSpellDamage(Spell spell) {
        if(spell==null){
            return 0;
        }
        else{
            return (spell.getDamage()+(this.dexterity* CharacterDefaults.DEXTERITY_MULTIPLIER)*spell.getDamage());
        }
    }
    public void regen(double hp_regen, double mana_regen) {
        System.out.println("=================== REGENERATION ===================");
        System.out.println("Healing " + this.getName() + "...");
        this.setHealthPower(this.getHealthPower() + (this.getHealthPower() * hp_regen));
        System.out.println("Replenishing " + this.getName() + "'s Mana...");
        this.mana += this.mana * mana_regen;
        System.out.println(this.battleDisplay());
    }

    // Displays
    // Basic Display Pre-start game
    @Override
    public String toString() {
        String out = this.getClass().getSimpleName() + "\n" +
                        super.toString() +
                        "Experience Points: " + this.experience + "\n" +
                        "Mana: " + this.mana + "\n" +
                        "Strength: " + this.strength + "\n" +
                        "Agility: " + this.agility + "\n" +
                        "Dexterity: " + this.dexterity + "\n" +
                        "Money: " + this.money + "\n";
        return out;
    }
    public String battleDisplay(){
        String out = "==============================\n";
        out += this.getClass().getSimpleName() + "\n" +
                super.toString() +
                "Mana: " + this.mana + "\n" +
                "Equipped armor: \n" + this.armor.battleDisplay() +
                "Equipped weapons: \n" + battleDisplayW() ;
        return out ;
    }
    private String battleDisplayW(){
        String out = new String();
        Iterator<ItemQuantity> itr = this.weapons.iterator();
        while(itr.hasNext()){
            ItemQuantity temp = itr.next();
            out += temp.getQ() + "x"  + "\n";
            Weapon tempw = (Weapon) temp.getI();
            out += tempw.battleDisplay();
            out +="------------------------------\n";
        }
        return out;
    }

    // Private Methods (Helpers)
    // Level up hero
    protected void levelUp() {
        this.experience = this.experience % CharacterDefaults.EXP_MULTIPLIER;
        this.setLevel(this.getLevel()+1);
        this.setHealthPower(CharacterDefaults.HP_MULTIPLIER*this.getLevel());
        this.strength = this.strength + (this.strength * CharacterDefaults.SKILLS_MULTIPLIER);
        this.agility = this.agility + (this.agility * CharacterDefaults.SKILLS_MULTIPLIER);
        this.dexterity = this.dexterity + (this.dexterity * CharacterDefaults.SKILLS_MULTIPLIER);
        this.mana = this.mana*0.1;
    }

    // prompts user to buy single item
    private void buyItem(List<Item> availableItems){
        System.out.println("Which item would " + this.getName() + " like to buy? (item #)");
        int itemIndex = 0;
        try{
            itemIndex = scan.nextInt();
            System.out.println(DIVIDER);
            scan.nextLine();
            while(itemIndex<1||itemIndex> availableItems.size()){
                ErrorMessage.printErrorOutOfRange();
                System.out.println("Please enter valid item #");
                itemIndex = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
            }
        }
        catch(InputMismatchException e){
            ErrorMessage.printErrorInvalidInput();
        }
        Item temp = availableItems.get(itemIndex-1);
        int maxpq = (int) (this.getMoney()/temp.getPrice());
        if(maxpq>0){
            System.out.print("How many " + temp.getName() + " would " + this.getName() + " like to purchase?\n" + "up to " + maxpq + " purchasable\n" );
            int pq = 0;
            try{
                pq  = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
                while(pq<1||pq>maxpq){
                    ErrorMessage.printErrorOutOfRange();
                    System.out.println("Please purchase affordable quantity of " + temp.getName());
                    pq = scan.nextInt();
                    System.out.println(DIVIDER);
                    scan.nextLine();
                }
            }
            catch(InputMismatchException e){
                ErrorMessage.printErrorInvalidInput();
            }
            double paid = pq*temp.getPrice();
            System.out.println(this.getName() + " paid merchant $" + paid );
            this.money -= paid;
            System.out.println(this.getName() + "successfully purchased " + pq + " " + temp.getName());
            System.out.println(this.getName() + " has remaining $" + this.money);
            this.inventory.addItem(temp, pq);
            System.out.println(this.inventory);
        }
        else{
            System.out.println("Sorry but" + this.getName() +" is too broke to purchase " + temp.getName());
        }
    }
    // prompts user to sell single item
    private void sellItem(){
        System.out.print(this.inventory);
        System.out.println("Which item would " + this.getName() + " like to sell? (item #)");
        int itemIndex = 0;
        try{
            itemIndex = scan.nextInt();
            System.out.println(DIVIDER);
            scan.nextLine();
            while(itemIndex<1||itemIndex>this.inventory.numItems()){
                ErrorMessage.printErrorOutOfRange();
                System.out.println("Please enter valid item #");
                itemIndex = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
            }
        }
        catch(InputMismatchException e){
            ErrorMessage.printErrorInvalidInput();
        }
        Item temp = this.inventory.getItem(itemIndex-1);
        int maxrq = this.inventory.getQuantity(itemIndex-1);
        if(maxrq>0){
            System.out.print("How many " + temp.getName() + " would " + this.getName() + " like to sell?\n" + "up to " + maxrq + " sellable\n" );
            int rq = 0;
            try{
                rq  = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
                while(rq<1||rq>maxrq){
                    ErrorMessage.printErrorOutOfRange();
                    System.out.println("Please purchase affordable quantity of " + temp.getName());
                    rq = scan.nextInt();
                    System.out.println(DIVIDER);
                    scan.nextLine();
                }
            }
            catch(InputMismatchException e){
                ErrorMessage.printErrorInvalidInput();
            }
            double paid = rq*temp.getPrice()*0.5;
            System.out.println(this.getName() + " received $" + paid );
            this.money += paid;
            System.out.println(this.getName() + "successfully sold " + rq + " " + temp.getName());
            System.out.println(this.getName() + " has $" + this.money);
            this.inventory.removeItem(temp, rq);
            System.out.println(this.inventory);
        }
        else{
            System.out.println("Sorry but" + this.getName() +" do not own " + temp.getName());
        }
    }

    // prompts user to change weapons
    private void changeWeapon() {
        System.out.println("Would " + this.getName() + " like to equip or unequip weapons? (" + EQUIP_INPUT + "/" + UNEQUIP_INPUT + ")");
        char eu = '\u0000';
        try {
            eu = scan.next().charAt(0);
            System.out.println(DIVIDER);
            while (eu != EQUIP_INPUT && eu != UNEQUIP_INPUT) {
                System.out.println("Would " + this.getName() + " like to equip or unequip weapons? (" + EQUIP_INPUT + "/" + UNEQUIP_INPUT + ")");
                eu = scan.next().charAt(0);
                System.out.println(DIVIDER);
            }
            if (eu == EQUIP_INPUT) {
                if (numHandsUsed() != this.hands) {
                    equip();
                    char yn = '\u0000';
                    if (this.inventory.numWeapons() > 0 && this.numHandsUsed() < hands) {
                        try {
                            while (yn != YES_INPUT && yn != NO_INPUT) {
                                System.out.println("Would " + this.getName() + " like to equip more items? (" + YES_INPUT + "/" + NO_INPUT + ")");
                                yn = scan.next().charAt(0);
                                System.out.println(DIVIDER);
                            }
                            while (yn == YES_INPUT) {
                                equip();
                                if (this.inventory.numWeapons() != 0) {
                                    System.out.println("Would " + this.getName() + " like to equip more items? (" + YES_INPUT + "/" + NO_INPUT + ")");
                                    yn = scan.next().charAt(0);
                                    System.out.println(DIVIDER);
                                } else {
                                    break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            ErrorMessage.printErrorInvalidInput();
                        }
                    }
                }
            } else {
                if (numHandsUsed() > 0) {
                    unequip();
                    char yn = '\u0000';
                    if (this.weapons.size() != 0) {
                        try {
                            while (yn != YES_INPUT && yn != NO_INPUT) {
                                System.out.println("Would " + this.getName() + " like to unequip more weapons? (" + YES_INPUT + "/" + NO_INPUT + ")");
                                yn = scan.next().charAt(0);
                                System.out.println(DIVIDER);
                            }
                            while (yn == YES_INPUT) {
                                unequip();
                                if (this.weapons.size() != 0) {
                                    System.out.println("Would " + this.getName() + " like to unequip more weapons? (" + YES_INPUT + "/" + NO_INPUT + ")");
                                    yn = scan.next().charAt(0);
                                    System.out.println(DIVIDER);
                                } else {
                                    break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            ErrorMessage.printErrorInvalidInput();
                        }
                    }
                }
            }
            System.out.println("Is " + this.getName() + " done changing weapons? (" + YES_INPUT + "/" + NO_INPUT + ")");
            char yn = '\u0000';
            try {
                while (yn != YES_INPUT && yn != NO_INPUT) {
                    yn = scan.next().charAt(0);
                    System.out.println(DIVIDER);
                }
                if (yn == YES_INPUT) {
                    return;
                } else {
                    changeWeapon();
                    ;
                }
            } catch (InputMismatchException e) {
                ErrorMessage.printErrorInvalidInput();
            }
        } catch (InputMismatchException e) {
            ErrorMessage.printErrorInvalidInput();
        }
    }
    // gets number of hands that is currently equipped with weapon
    private int numHandsUsed(){
        int h = 0;
        int i = 1;
        String out = new String();
        Iterator<ItemQuantity> itr = this.weapons.iterator();
        while(itr.hasNext()){
            ItemQuantity tempq = itr.next();
            Weapon tempw = (Weapon) tempq.getI();
            h += tempq.getQ() * tempw.getReqHands();
            i++;
        }
        return h;
    }
    // equips weapon by adding to weapon list
    private void addWeapon(Item item, int pq){
        ItemQuantity temp = new ItemQuantity(item, pq);
        if(this.weapons.contains(temp)){
            this.weapons.get(this.weapons.indexOf(temp)).addQ(pq);
        }
        else{
            this.weapons.add(temp);
        }
    }
    // unequip weapon by removing from weapon list
    private void removeWeapon(Item item, int rq){
        ItemQuantity temp = new ItemQuantity(item, rq);
        if (this.weapons.contains(temp)){
            this.weapons.get(this.weapons.indexOf(temp)).remQ(rq);
            if(this.weapons.get(this.weapons.indexOf(temp)).getQ()==0){
                this.weapons.remove(temp);
            }
        }
    }
    // prompts user to select weapon to equip from inventory
    private void equip(){
        this.inventory.maskInventoryWDisplay();
        System.out.println("Which weapon would " + this.getName() + " like to equip? (item #)");
        int  weaponIndex = 0;
        try{
            weaponIndex = scan.nextInt();
            System.out.println(DIVIDER);
            scan.nextLine();
            while((weaponIndex<1||weaponIndex>this.inventory.numItems())||!(this.inventory.getItem(weaponIndex-1) instanceof Weapon)){
                ErrorMessage.printErrorOutOfRange();
                System.out.println("Please enter valid item #");
                weaponIndex = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
            }
        }
        catch(InputMismatchException e){
            ErrorMessage.printErrorInvalidInput();
        }
        Item temp = this.inventory.getItem(weaponIndex-1);
        int maxeq = Math.min((this.hands-numHandsUsed()),this.inventory.getQuantity(weaponIndex-1));
        if(maxeq>0) {
            System.out.print("How many " + temp.getName() + " would " + this.getName() + " like to equip?\n" + "up to " + maxeq + " equipable\n");
            int eq = 0;
            try {
                eq = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
                while (eq < 1 || eq > maxeq) {
                    ErrorMessage.printErrorOutOfRange();
                    System.out.println("Please equip equipable quantity of " + temp.getName());
                    eq = scan.nextInt();
                    System.out.println(DIVIDER);
                    scan.nextLine();
                }
            } catch (InputMismatchException e) {
                ErrorMessage.printErrorInvalidInput();
            }
            this.inventory.removeItem(temp, eq);
            System.out.println(eq + " " + temp.getName() + " removed from " + this.getName() + "'s inventory");
            addWeapon(temp, eq);
            System.out.println(this.getName() + "successfully equipped " + eq + " " + temp.getName());
            System.out.println("Hands used: " + this.numHandsUsed());
            displayEquippedW();
            this.inventory.maskInventoryWDisplay();
        }
        return;
    }
    // prompts user to select weapon to unequip from inventory
    private void unequip(){
        displayEquippedW();
        int weaponIndex = 0;
        try{
            System.out.println("Which weapon would "+ this.getName() + " like to unequip? (weapon #)");
            weaponIndex = scan.nextInt();
            System.out.println(DIVIDER);
            scan.nextLine();
            while(weaponIndex<1||weaponIndex>this.weapons.size()){
                ErrorMessage.printErrorOutOfRange();
                System.out.println("Please enter valid weapon #");
                weaponIndex = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
            }
        }
        catch(InputMismatchException e){
            ErrorMessage.printErrorInvalidInput();
        }
        Item temp = this.weapons.get(weaponIndex-1).getI();
        int maxrq = this.weapons.get(weaponIndex-1).getQ();
        if(maxrq>0){
            System.out.print("How many " + temp.getName() + " would " + this.getName() + " like to unequip?\n" + "up to " + maxrq + " unequipable\n" );
            int rq = 0;
            try{
                rq  = scan.nextInt();
                System.out.println(DIVIDER);
                scan.nextLine();
                while(rq<1||rq>maxrq){
                    ErrorMessage.printErrorOutOfRange();
                    System.out.println("Please unequip unequipable quantity of " + temp.getName());
                    rq = scan.nextInt();
                    System.out.println(DIVIDER);
                    scan.nextLine();
                }
            }
            catch(InputMismatchException e){
                ErrorMessage.printErrorInvalidInput();
            }
            removeWeapon(temp,rq);
            System.out.println(this.getName() + " unequipped " + rq + " " + temp.getName() );
            displayEquippedW();
            this.inventory.addItem(temp, rq);
            System.out.println(rq + " " + temp.getName() + " returned to " + this.getName() + "'s inventory");
            this.inventory.maskInventoryWDisplay();
        }
        else{
            System.out.println("Sorry but" + temp.getName() +" is not equipped on " + this.getName());
        }
    }
    // inventory view of equipped weapons
    private void displayEquippedW(){
        int i = 1;
        String out = new String();
        Iterator<ItemQuantity> itr = this.weapons.iterator();
        while(itr.hasNext()){
            ItemQuantity temp = itr.next();
            out+="Weapon " + i + "\n" + temp.toString() +"\n---------------------------\n" ;
            i++;
        }
        out = "\033[0;1m"+ "EQUIPPED WEAPONS \n"+ "\u001B[0m" +"---------------------------\n" + out;
        System.out.print(out);
    }
    // computes total damage of equipped weapons
    private double weaponDamage(){
        double d = 0;
        int i = 1;
        String out = new String();
        Iterator<ItemQuantity> itr = this.weapons.iterator();
        while(itr.hasNext()){
            ItemQuantity tempq = itr.next();
            Weapon tempw = (Weapon) tempq.getI();
            d += tempq.getQ() * tempw.getDamage();
            i++;
        }
        return d;
    }

    // prompts user to equip or unequip weapon
    private void changeArmor() {
        System.out.println("Would " + this.getName() + " like to equip or unequip armor? (" + EQUIP_INPUT + "/" + UNEQUIP_INPUT + ")");
        char eu = '\u0000';
        try {
            eu = scan.next().charAt(0);
            while (eu != EQUIP_INPUT && eu != UNEQUIP_INPUT) {
                System.out.println("Would " + this.getName() + " like to equip or unequip armor? (" + EQUIP_INPUT + "/" + UNEQUIP_INPUT + ")");
                eu = scan.next().charAt(0);
                System.out.println(DIVIDER);
            }
            if (eu == EQUIP_INPUT) {
                if (this.armor.getName() == null) {
                    int armorIndex = '\u0000';
                    if (this.inventory.numArmors() != 0) {
                        this.inventory.maskInventoryADisplay();
                        try {
                            System.out.println("Which armor would " + this.getName() + " like to equip? (item #)");
                            armorIndex = scan.nextInt();
                            System.out.println(DIVIDER);
                            scan.nextLine();
                            while ((armorIndex < 1 || armorIndex > this.inventory.numItems()) || !(this.inventory.getItem(armorIndex - 1) instanceof Armor)){
                                ErrorMessage.printErrorOutOfRange();
                                System.out.println("Please enter valid item #");
                                armorIndex = scan.nextInt();
                                System.out.println(DIVIDER);
                                scan.nextLine();
                            }
                        } catch (InputMismatchException e) {
                            ErrorMessage.printErrorInvalidInput();
                        }
                        Item temp = this.inventory.getItem(armorIndex - 1);
                        this.inventory.removeItem(temp, 1);
                        System.out.println(1 + " " + temp.getName() + " removed from " + this.getName() + "'s inventory");
                        this.inventory.maskInventoryADisplay();
                        this.armor = (Armor) temp;
                        System.out.println(this.getName() + " successfully equipped " + temp.getName());
                        System.out.println("Currently Equipped Armor");
                        System.out.println(this.armor);
                    }
                }
            } else if (eu == UNEQUIP_INPUT) {
                if (this.armor.getName() != null) {
                    Item temp = this.armor;
                    this.inventory.addItem(temp, 1);
                    System.out.println(1 + " " + temp.getName() + " added to " + this.getName() + "'s inventory");
                    this.inventory.maskInventoryADisplay();
                    this.armor = new Armor(null, 0, 0, 0);
                    System.out.println(this.getName() + "successfully unequiped " + temp.getName());
                }
            }
            System.out.println("Is " + this.getName() + " done changing armor? (" + YES_INPUT + "/" + NO_INPUT + ")");
            char yn = '\u0000';
            try {
                while (yn != YES_INPUT && yn != NO_INPUT) {
                    yn = scan.next().charAt(0);
                    System.out.println(DIVIDER);
                }
                if (yn == YES_INPUT) {
                    return;
                } else {
                    changeArmor();
                    ;
                }
            } catch (InputMismatchException e) {
                ErrorMessage.printErrorInvalidInput();
            }
        } catch (InputMismatchException e) {
            ErrorMessage.printErrorInvalidInput();
        }
    }

    // prompts user to consume potions
    private void consumePotions(){
        if (this.inventory.numPotions()!=0){
            this.inventory.maskInventoryPDisplay();
            int potionIndex = 0;
            try{
                while(potionIndex<1||potionIndex>this.inventory.numItems()||!(this.inventory.getItem(potionIndex-1) instanceof Potion)){
                    System.out.println("Which potion would " + this.getName() +" like to consume? (item #)");
                    potionIndex = scan.nextInt();
                    System.out.println(DIVIDER);
                    scan.nextLine();
                }
                consume((Potion) this.inventory.getItem(potionIndex-1));
                this.inventory.maskInventoryPDisplay();
                System.out.println("Is " + this.getName() + " done consuming potions? (" + YES_INPUT + "/" + NO_INPUT + ")");
                char yn = '\u0000';
                try{
                    while(yn!=YES_INPUT&&yn!=NO_INPUT){
                        yn = scan.next().charAt(0);
                        System.out.println(DIVIDER);
                    }
                    if (yn==YES_INPUT){
                        return;
                    }
                    else{
                        consumePotions();
                    }
                }catch(InputMismatchException e){
                    ErrorMessage.printErrorInvalidInput();
                }
            }catch (InputMismatchException e){
                ErrorMessage.printErrorInvalidInput();
            }
        }
        else{
            System.out.println(this.getName() + "has no potions");
        }
    };
    // consumes potion  and remove from inventory
    private void consume(Potion potion){
        if (potion instanceof Health){
            this.setHealthPower(this.getHealthPower()+potion.getEffect());
        }
        else if (potion instanceof Mana){
            this.mana+=potion.getEffect();
        }
        else if (potion instanceof Strength){
            this.strength+=potion.getEffect();
        }
        else if (potion instanceof Dexterity){
            this.dexterity+=potion.getEffect();
        }
        else if (potion instanceof Agility){
            this.agility+=potion.getEffect();
        }
        else if (potion instanceof Experience){
            this.experience+=potion.getEffect();
            if(this.experience>this.getLevel()* CharacterDefaults.EXP_MULTIPLIER){
                this.levelUp();
            }
        }
        System.out.println(this.getName() + "'s " + potion.getClass().getSimpleName() + " increased by " + potion.getEffect());
        this.inventory.removeItem(potion,1);
    }

}