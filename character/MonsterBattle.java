package character;

import character.items.spells.Spell;

public interface MonsterBattle extends Battle {
    AttackResult receiveSpell(Spell spell, double spellDamage);
}
