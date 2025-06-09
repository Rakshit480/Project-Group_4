package ca.sheridancollege.project;

import java.util.*;

public class WarGame extends Game {
    private WarPlayer player1;
    private WarPlayer player2;
    private int round = 1;
    private int maxRounds;
    private int p1Wins = 0;
    private int p2Wins = 0;

    public WarGame(String title) {
        super(title);
        Scanner scanner = new Scanner(System.in);

        // 👤 Player enters name
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        this.player1 = new WarPlayer(playerName);

        // 🤖 Fixed bot name
        String botName = "Jarvis";
        this.player2 = new WarPlayer(botName);

        // 🎮 Game rules & instructions
        System.out.println("\nWelcome, " + player1.getName() + "!");
        System.out.println("You will be playing a card game called WAR against " + botName + ".");
        System.out.println("Each round, both players flip their top card.");
        System.out.println("Whichever card has the higher value wins the round and collects both cards.");
        System.out.println("If both cards are equal in value, a 'WAR' happens:");
        System.out.println(" - Each player puts one card face down, then one card face up.");
        System.out.println(" - The new face-up cards are compared to break the tie.");
        System.out.println(" - The winner takes all the cards in the round.");
        System.out.println(" - If a player doesn't have enough cards to complete the WAR, they lose.");
        System.out.println("------------------------------------------------------");

        // Ask user for number of rounds
        System.out.print("Enter number of rounds to play (max 100): ");
        this.maxRounds = scanner.nextInt();
        scanner.nextLine(); // clear input buffer

        if (this.maxRounds > 100) {
            this.maxRounds = 100;
            System.out.println("Max rounds limited to 100.");
        }
    }

    @Override
    public void play() {
        List<PlayingCard> fullDeck = createShuffledDeck();
        dealCards(fullDeck);

        Scanner scanner = new Scanner(System.in);

        while (player1.hasCards() && player2.hasCards() && round <= maxRounds) {
            System.out.println("\n--- Round " + round + " ---");

            if (round == maxRounds) {
                System.out.println("FINAL ROUND");
            }

            System.out.print("Press Enter to play or type 'q' to quit: ");
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
        List<PlayingCard> pot = new ArrayList<>();

        PlayingCard c1 = player1.playCard();
        PlayingCard c2 = player2.playCard();
        pot.add(c1);
        pot.add(c2);

        System.out.println(player1.getName() + " plays: " + c1);
        System.out.println(player2.getName() + " plays: " + c2);

        if (c1.getRankValue() > c2.getRankValue()) {
            player1.collectCards(pot);
            p1Wins++;
            System.out.println(player1.getName() + " wins the round.");
        } else if (c2.getRankValue() > c1.getRankValue()) {
            player2.collectCards(pot);
            p2Wins++;
            System.out.println(player2.getName() + " wins the round.");
        } else {
            System.out.println("It's a tie — this means WAR!");

            if (player1.getDeck().size() < 2) {
                System.out.println(player1.getName() + " doesn't have enough cards to continue the WAR.");
                System.out.println(player2.getName() + " wins the game by default.");
                player2.collectCards(pot);
                player2.collectCards(new ArrayList<>(player1.getDeck()));
                player1.getDeck().clear();
                return;
            }

            if (player2.getDeck().size() < 2) {
                System.out.println(player2.getName() + " doesn't have enough cards to continue the WAR.");
                System.out.println(player1.getName() + " wins the game by default.");
                player1.collectCards(pot);
                player1.collectCards(new ArrayList<>(player2.getDeck()));
                player2.getDeck().clear();
                return;
            }

            System.out.println("Each player now lays down one card face-down and one card face-up...");
            System.out.println("The new face-up cards will determine the winner of the WAR.");

            PlayingCard faceDown1 = player1.playCard();
            PlayingCard faceDown2 = player2.playCard();
            PlayingCard warCard1 = player1.playCard();
            PlayingCard warCard2 = player2.playCard();

            pot.add(faceDown1);
            pot.add(faceDown2);
            pot.add(warCard1);
            pot.add(warCard2);

            System.out.println(player1.getName() + "'s WAR card: " + warCard1);
            System.out.println(player2.getName() + "'s WAR card: " + warCard2);

            if (warCard1.getRankValue() > warCard2.getRankValue()) {
                player1.collectCards(pot);
                p1Wins++;
                System.out.println(player1.getName() + " wins the WAR and takes all the cards from this round.");
            } else if (warCard2.getRankValue() > warCard1.getRankValue()) {
                player2.collectCards(pot);
                p2Wins++;
                System.out.println(player2.getName() + " wins the WAR and takes all the cards from this round.");
            } else {
                System.out.println("Another tie in WAR! No tie-breaker logic beyond this point — cards are discarded.");
            }
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
                deck.add(new PlayingCard(ranks[i], suit, i + 2)); // Ace = 14
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

        System.out.println("Final Card Count:");
        System.out.println(player1.getName() + ": " + p1Count);
        System.out.println(player2.getName() + ": " + p2Count);

        System.out.println("\nRound Wins:");
        System.out.println(player1.getName() + ": " + p1Wins);
        System.out.println(player2.getName() + ": " + p2Wins);

        System.out.println("Total Rounds Played: " + (round - 1));

        if (p1Count > p2Count) {
            System.out.println(player1.getName() + " wins the game!");
        } else if (p2Count > p1Count) {
            System.out.println(player2.getName() + " wins the game!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}
