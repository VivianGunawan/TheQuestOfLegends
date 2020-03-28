package character;

import character.items.spells.Spell;

public interface Battle {
    // methods that facilitate a battle
    AttackResult receiveBasicAttack(double damage);
    AttackResult receiveSpell(Spell spell, double spellDamage); // No Operation in heroes
    double doBasicAttack();
    Spell castSpell(); // No Operation in Monster
    double castSpellDamage(Spell spell); // No Operation in Monster
    void regen(); // No Operation in Monster
}
