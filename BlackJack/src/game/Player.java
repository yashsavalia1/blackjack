package game;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import playingCards.Deck;

public class Player {
  private static final int WIN = 0;
  
  private static final int LOSE = 1;
  
  private static final int PUSH = 2;
  
  private String name;
  
  private Hand cards;
  
  private boolean active;
  
  private boolean busted;
  
  private boolean isDealer;
  
  private int bankRoll;
  
  private int wager;
  
  private boolean isHit;
  
  private int winLosePush;
  
  private boolean cannotPlay = false;
  
  private Object threadLock = new Object();
  
  public Player(String name, boolean dealer) {
    this.active = true;
    this.busted = false;
    this.cards = new Hand();
    this.name = name;
    this.isDealer = dealer;
    if (!this.isDealer)
      this.bankRoll = 1000; 
    this.cannotPlay = false;
  }
  
  public void startRound(Deck deck) {
    this.cards.addCard(deck.deal());
    this.cards.addCard(deck.deal());
  }
  
  public void playRound(Deck deck) {
    if (this.isDealer) {
      while (this.cards.getScore() < 17) {
        BlackJack.pause(3000L);
        boolean scoreCheck = this.cards.addCard(deck.deal());
        this.busted = !scoreCheck;
        this.active = scoreCheck;
      } 
      this.active = false;
      BlackJack.pause(3000L);
    } else {
      while (isActive()) {
        synchronized (this.threadLock) {
          try {
            this.threadLock.wait();
          } catch (InterruptedException e) {
            System.out.println("Error Occured");
          } 
          if (this.isHit) {
            if (!this.cards.addCard(deck.deal())) {
              bust();
              BlackJack.pause(3000L);
            } 
          } else {
            stand();
          } 
        } 
      } 
    } 
  }
  
  public int finishRound(Player dealer) {
    if (!this.isDealer)
      if (isBusted()) {
        this.winLosePush = LOSE;
      } else if (dealer.isBusted()) {
        this.winLosePush = WIN;
      } else if (dealer.getHand().getScore() == getHand().getScore()) {
        this.winLosePush = PUSH;
      } else if (dealer.getHand().getScore() < getHand().getScore()) {
        this.winLosePush = WIN;
      } else if (dealer.getHand().getScore() > getHand().getScore()) {
        this.winLosePush = LOSE;
      }  
    if (this.winLosePush == 0) {
      if (this.cards.isBlackJack()) {
        updateBankRoll((int)(this.wager * 2.5D));
      } else {
        updateBankRoll(this.wager * 2);
      } 
    } else if (this.winLosePush == 2) {
      updateBankRoll(this.wager);
    } else {
      updateBankRoll(0);
    } 
    return this.winLosePush;
  }
  
  public void recieveInput(ActionEvent e) {
    synchronized (this.threadLock) {
      if (((JButton)e.getSource()).getText().equals("Hit")) {
        this.isHit = true;
        this.threadLock.notify();
      } else if (((JButton)e.getSource()).getText().equals("Stand")) {
        this.isHit = false;
        this.threadLock.notify();
      } 
    } 
  }
  
  public void stand() {
    this.active = false;
  }
  
  public void bust() {
    this.busted = true;
    this.active = false;
  }
  
  public boolean isActive() {
    return this.active;
  }
  
  public boolean isBusted() {
    return this.busted;
  }
  
  public Hand getHand() {
    return this.cards;
  }
  
  public boolean isDealer() {
    return this.isDealer;
  }
  
  public int getBankRoll() {
    return this.bankRoll;
  }
  
  public void updateBankRoll(int amountToAdd) {
    this.bankRoll += amountToAdd;
    if (this.bankRoll < 10)
      this.cannotPlay = true; 
  }
  
  public int getWager() {
    return this.wager;
  }
  
  public void setWager(int wager) {
    this.wager = wager;
    this.bankRoll -= wager;
  }
  
  public boolean cannotPlay() {
    return this.cannotPlay;
  }
  
  public void clear() {
    this.cards = new Hand();
    this.active = true;
    this.busted = false;
  }
  
  public String toString() {
    return this.name;
  }
}
