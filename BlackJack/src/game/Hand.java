package game;

import java.util.ArrayList;
import java.util.List;
import playingCards.Card;

public class Hand {
  private List<Card> cards = new ArrayList<>();
  
  private int highAces = 0;
  
  private int score = 0;
  
  private int faceDownCard;
  
  public boolean addCard(Card c) {
    this.cards.add(c);
    if (!addScore(c.getValue()))
      return false; 
    return true;
  }
  
  private boolean addScore(int add) {
    if (add == 1) {
      this.highAces++;
      this.score += 11;
    } else if (add > 10) {
      this.score += 10;
    } else {
      this.score += add;
    } 
    return checkScore();
  }
  
  private boolean checkScore() {
    while (this.score > 21) {
      if (this.highAces != 0) {
        this.highAces--;
        this.score -= 10;
        continue;
      } 
      return false;
    } 
    return true;
  }
  
  public int getScore() {
    return this.score;
  }
  
  public int getFaceDownCard() {
    return this.faceDownCard;
  }
  
  public Card getCard(int index) {
    return this.cards.get(index);
  }
  
  public boolean isBlackJack() {
    if (this.score == 21 && this.cards.size() == 2)
      return true; 
    return false;
  }
  
  public boolean hasHighAces() {
    return (this.highAces != 0);
  }
  
  public int numCards() {
    return this.cards.size();
  }
  
  public String toString() {
    String allCards = "";
    for (Card c : this.cards)
      allCards = String.valueOf(allCards) + c.toString(); 
    return allCards;
  }
}
