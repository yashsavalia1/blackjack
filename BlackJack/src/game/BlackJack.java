package game;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import playingCards.Deck;

public class BlackJack {
  public static JFrame frame = new JFrame("Blackjack");
  
  public static JPanel panel = new JPanel();
  
  public static void main(String[] args) {
    frame.setVisible(true);
    frame.setDefaultCloseOperation(3);
    frame.setSize(1200, 600);
    JLabel label = new JLabel(
        "<html>WELCOME TO BLACKJACK!<br>by Yash Savalia<br>Blackjacks pay 3 : 2<br>Dealer stands on 17s</html>", 
        0);
    label.setFont(new Font("", 1, 20));
    frame.add(label);
    label.setVisible(false);
    label.setVisible(true);
    pause(5000L);
    label.setVisible(false);
    boolean inPlay = true;
    boolean firstTime = true;
    int cannotPlayCount = 0;
    String[] choices = { "1", "2", "3" };
    String numPlayers = showNumberOfPlayersDialog(choices);
    Player[] players = new Player[Integer.parseInt(numPlayers) + 1];
    while (inPlay) {
      byte b;
      int j;
      Player[] arrayOfPlayer;
      for (j = (arrayOfPlayer = players).length, b = 0; b < j; ) {
        Player p = arrayOfPlayer[b];
        if (p != null && p.cannotPlay())
          cannotPlayCount++; 
        b++;
      } 
      if (cannotPlayCount == players.length - 1) {
        JOptionPane.showMessageDialog(frame, "No one has enough tokens to play!");
        System.exit(0);
        break;
      } 
      for (int i = 0; i < players.length - 1; i++) {
        if (firstTime) {
          String playerName = JOptionPane.showInputDialog(frame, 
              "What is the name of Player " + (i + 1) + "?");
          players[i] = new Player(playerName, false);
        } 
        if (players[i].getBankRoll() < 10) {
          JOptionPane.showMessageDialog(frame, String.valueOf(players[i].toString()) + " ran out of tokens.");
          Player[] tempPlayers = new Player[players.length - 1];
          int k = 0;
          for (int m = 0; m < players.length; m++) {
            if (m != i) {
              tempPlayers[k] = players[m];
              k++;
            } 
          } 
          players = tempPlayers;
          cannotPlayCount--;
          i--;
        } else {
          String wager = "0";
          do {
            String[] wagers = getWagers(players[i].getBankRoll());
            wager = (String)JOptionPane.showInputDialog(frame, "What is your wager, " + players[i] + "?" + 
                "  You have " + players[i].getBankRoll() + " tokens.", null, 3, null, (Object[])wagers, wagers[0]);
          } while (Integer.parseInt(wager) > players[i].getBankRoll());
          players[i].setWager(Integer.parseInt(wager));
        } 
      } 
      firstTime = false;
      if (cannotPlayCount != players.length - 1) {
        players[players.length - 1] = new Player("Dealer", true);
        BlackJack game = new BlackJack();
        game.playRound(players);
        int anotherRound = JOptionPane.showConfirmDialog(frame, "Another Round?", "", 0);
        if (anotherRound == 0) {
          inPlay = true;
          continue;
        } 
        inPlay = false;
        System.exit(0);
        continue;
      } 
      System.exit(0);
    } 
  }
  
  private static String showNumberOfPlayersDialog(String[] choices) {
    while (true) {
      String numPlayers = (String)JOptionPane.showInputDialog(frame, "How Many Players?", null, 3, null, (Object[])choices, 
          choices[0]);
      if (numPlayers == null) {
        int yourChoice = JOptionPane.showConfirmDialog(frame, "Are you sure, you want to exit the game ?", "", 
            0);
        if (yourChoice == 0)
          System.exit(0); 
      } 
      if (numPlayers != null)
        return numPlayers; 
    } 
  }
  
  private static String[] getWagers(int currentBankRoll) {
    List<String> wagers = new ArrayList<>();
    for (String str : Arrays.<String>asList(new String[] { "10", "20", "50", "100", "200", "500", "1000" })) {
      if (Integer.parseInt(str) <= currentBankRoll)
        wagers.add(str); 
    } 
    return wagers.<String>toArray(new String[0]);
  }
  
  public void playRound(Player[] players) {
    Deck deck = new Deck();
    deck.shuffle();
    JLabel label = new JLabel("Dealer is dealing the deck...", 0);
    label.setFont(new Font("", 1, 20));
    frame.add(label);
    frame.setVisible(true);
    pause(2500L);
    label.setVisible(false);
    FirstDealPanel firstPanel = new FirstDealPanel();
    byte b;
    int i;
    Player[] arrayOfPlayer1;
    for (i = (arrayOfPlayer1 = players).length, b = 0; b < i; ) {
      Player p = arrayOfPlayer1[b];
      p.startRound(deck);
      frame.add(firstPanel);
      firstPanel.displayPlayerCards(p);
      pause(3000L);
      frame.remove(firstPanel);
      b++;
    } 
    PlayerPanel playPanel = new PlayerPanel(players[players.length - 1]);
    int j;
    Player[] arrayOfPlayer2;
    for (j = (arrayOfPlayer2 = players).length, i = 0; i < j; ) {
      Player p = arrayOfPlayer2[i];
      if (!p.getHand().isBlackJack() || p.isDealer()) {
        label.setText(String.valueOf(p.toString()) + "'s Turn");
        label.setVisible(true);
        pause(2500L);
        label.setVisible(false);
        frame.add(playPanel);
        playPanel.displayPlayerCards(p);
        playPanel.setVisible(true);
        p.playRound(deck);
        playPanel.setVisible(false);
      } 
      i++;
    } 
    FinishPanel fPanel = new FinishPanel();
    Player[] arrayOfPlayer3;
    for (int k = (arrayOfPlayer3 = players).length; j < k; ) {
      Player p = arrayOfPlayer3[j];
      if (!p.isDealer()) {
        int originalBankRoll = p.getBankRoll();
        int outcome = p.finishRound(players[players.length - 1]);
        String winText = "";
        if (outcome == 0) {
          winText = "Wins!";
        } else if (outcome == 1) {
          winText = "Loses!";
        } else if (outcome == 2) {
          winText = "Pushes with the dealer.";
        } 
        label.setText(String.valueOf(p.toString()) + " " + winText);
        label.setVisible(true);
        pause(2500L);
        label.setVisible(false);
        frame.add(fPanel);
        fPanel.endGame(p, originalBankRoll);
        fPanel.setVisible(true);
        pause(5000L);
        fPanel.setVisible(false);
      } 
      p.clear();
      j++;
    } 
  }
  
  public static void pause(long time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      System.out.println("Error Occured.");
    } 
  }
}
