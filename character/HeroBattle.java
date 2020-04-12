package character;

import character.items.spells.Spell;

public interface HeroBattle extends Battle{
    Spell castSpell();
    double castSpellDamage(Spell spell);
    void regen(double hp_regen, double mana_regen);
}
