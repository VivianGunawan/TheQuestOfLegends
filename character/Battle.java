package character;

public interface Battle {
    // methods that facilitate a battle
    AttackResult receiveBasicAttack(double damage);
    double doBasicAttack();
}
