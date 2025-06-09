package ca.sheridancollege.project;

import java.util.*;

public class WarPlayer extends Player {
    private Queue<PlayingCard> deck = new LinkedList<>();

    public WarPlayer(String name) {
        super(name);
    }

    public void addCard(PlayingCard card) {
        deck.add(card);
    }

    public PlayingCard playCard() {
        return deck.poll();
    }

    public void collectCards(List<PlayingCard> cards) {
        deck.addAll(cards);
    }

    public boolean hasCards() {
        return !deck.isEmpty();
    }

    public Queue<PlayingCard> getDeck() {
        return deck;
    }

    @Override
    public void play() {
        // Not needed for this game
    }
}
