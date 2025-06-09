package ca.sheridancollege.project;

public class PlayingCard extends Card {
    private String rank;
    private String suit;
    private int rankValue;

    public PlayingCard(String rank, String suit, int rankValue) {
        this.rank = rank;
        this.suit = suit;
        this.rankValue = rankValue;
    }

    public int getRankValue() {
        return rankValue;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
