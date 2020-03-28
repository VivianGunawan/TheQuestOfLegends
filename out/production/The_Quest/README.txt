Vivian Gunawan
U18391963
--------------------------------------

Compilation Instructions
javac -d out *.java
cd out
java TheQuestGameEngine

---------------------------------------

Class Usability:
If not found as comments in code ..

TheQuestGameEngine -> represents the quest game
Map -> represents the map made up of tiles

Tile -> abstract class representing common tile / market tile / Inaccessible tile
CommonTile -> representing common tile, monsters dwell here and may "awake" when heroes enter
MarketTile -> representing market tile. merchant dwell here
InaccessibleTile -> representing Inaccessible tile
TileType -> enum for types of tiles

Character -> abstract class representing hero/monster/merchant
Team -> represents a team of user selected heroes
Battle -> interface implemented by hero and monsters to facilitate battle
Transaction -> interface implemented by hero and merchant to perform transactions
AttackResult -> enum for attack results(dead, kill, success, dodged) for battle interface

Hero -> represents hero paladin, warrior, sorcerer
HeroType -> enum for types of heroes 
Inventory -> each hero has individual inventory
ItemQuantity -> "data type" that compares items and allows "storage" of item and quantity as a pair

Merchant -> represents a merchant that dwells in market tile, merchant name in this game is always "merchant" and level=0

Monster -> represent monster dragon, exoskeleton, spirit
MonsterType -> enum for types of monsters

Item -> abstract class representing items weapons, armors, potions, spells
ItemType -> enum for types of items
Weapon -> represent weapons
Armor -> represent armor
Potion -> represent potion healing...etc(6 types)
PotionType -> enum for types of potion by types I mean the attribute that it increase
Spell -> represents spell nice, lightning, fire
SpellType -> enum for types of potions

ColoredOutputs -> static values for colored terminal output to make your life less black and white
ErrorMessages -> static values for error messages used in "input validation"(not the focus of the class)
IOConstants -> static chars use for interacting with the game
Defaults -> where all the assumptions of the game is specified, values retrieved from sample files given

Explanation for design:

Enums were used instead of inheritance because they don't really perform different methods... or implemented differently, hence switch (enum type) perform well enough.

When heroes lose a fight they get revived by 0.25 their health, you can change this in the Defaults.

I think heroes have 2 hands (in the defaults again if you want to change this), hence I put them to good use and they can equip up to 2 weapons at a time.

You cannot quit while exploring inventory, talking to merchant or in battle, quit option will be available when you choose to leave tile. In the case of extreme frustration and rage quit just do Ctrl-z

Appropriate information are displayed after every action, typing I when available allows you to display a more detailed information.

Merchants... yeah they exist because I don't think items should randomly float around in the game.






 