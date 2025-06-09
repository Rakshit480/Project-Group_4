package ca.sheridancollege.project;

import java.util.*;

public class WarGame extends Game {
    private WarPlayer player1;
    private WarPlayer player2;

    public WarGame(String title) {
        super(title);
        this.player1 = new WarPlayer("Rakshit");
        this.player2 = new WarPlayer("Bot");
    }

    @Override
    public void play() {
        List<PlayingCard> fullDeck = createShuffledDeck();
        dealCards(fullDeck);

        Scanner scanner = new Scanner(System.in);
        int round = 1;
        int maxRounds = 100; // optional safety limit to avoid infinite loops

        while (player1.hasCards() && player2.hasCards() && round <= maxRounds) {
            System.out.println("\n--- Round " + round + " ---");
            System.out.println("Press Enter to throw a card or type 'q' to quit: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                System.out.println("Game ended by user.");
                return;
            }

            playRound();
            round++;
        }

        declareWinner();
    }

    private void playRound() {
        PlayingCard c1 = player1.playCard();
        PlayingCard c2 = player2.playCard();

        List<PlayingCard> pot = new ArrayList<>();
        pot.add(c1);
        pot.add(c2);

        System.out.println(player1.getName() + " plays: " + c1);
        System.out.println(player2.getName() + " plays: " + c2);

        if (c1.getRankValue() > c2.getRankValue()) {
            player1.collectCards(pot);
            System.out.println(player1.getName() + " wins the round.");
        } else if (c2.getRankValue() > c1.getRankValue()) {
            player2.collectCards(pot);
            System.out.println(player2.getName() + " wins the round.");
        } else {
            System.out.println("It's a WAR! (Tie-breaker not implemented yet)");
        }

        System.out.println("Cards left:");
        System.out.println(player1.getName() + ": " + player1.getDeck().size());
        System.out.println(player2.getName() + ": " + player2.getDeck().size());
    }

    private void dealCards(List<PlayingCard> deck) {
        for (int i = 0; i < deck.size(); i++) {
            if (i % 2 == 0) player1.addCard(deck.get(i));
            else player2.addCard(deck.get(i));
        }
    }

    private List<PlayingCard> createShuffledDeck() {
        List<PlayingCard> deck = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                deck.add(new PlayingCard(ranks[i], suit, i + 2)); // 2 = 2, Ace = 14
            }
        }

        Collections.shuffle(deck);
        return deck;
    }

    @Override
    public void declareWinner() {
        System.out.println("\n=== GAME OVER ===");

        int p1Count = player1.getDeck().size();
        int p2Count = player2.getDeck().size();

        if (p1Count > p2Count) {
            System.out.println("🏆 " + player1.getName() + " wins the game!");
        } else if (p2Count > p1Count) {
            System.out.println("🏆 " + player2.getName() + " wins the game!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}
