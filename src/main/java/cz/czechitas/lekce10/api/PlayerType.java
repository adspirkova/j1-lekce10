package cz.czechitas.lekce10.api;

public enum PlayerType {

    FOOD,
    GOOD,
    BAD;

    public boolean isCatching(PlayerType otherPlayerType) {
        if (this == BAD && otherPlayerType == GOOD) return true;
        if (this == GOOD && otherPlayerType == FOOD) return true;
        return false;
    }
}
