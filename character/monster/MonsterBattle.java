package character.monster;

import character.AttackResult;
import character.Battle;
import character.items.spells.Spell;

public interface MonsterBattle extends Battle {
    AttackResult receiveSpell(Spell spell, double spellDamage);
}
