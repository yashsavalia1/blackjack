package playingCards;

import java.util.ArrayList;
import java.util.List;

public class Deck {
  private List<Card> cardList = new ArrayList<>();
  
  public Deck() {
    Card[] cards = createCardArray();
    byte b;
    int i;
    Card[] arrayOfCard1;
    for (i = (arrayOfCard1 = cards).length, b = 0; b < i; ) {
      Card c = arrayOfCard1[b];
      this.cardList.add(c);
      b++;
    } 
  }
  
  private Card[] createCardArray() {
    Card[] cards = new Card[52];
    int count = 0;
    for (int i = 1; i <= 4; i++) {
      for (int j = 1; j <= 13; j++) {
        cards[count] = new Card(i, j);
        count++;
      } 
    } 
    return cards;
  }
  
  public Card deal() {
    if (!isEmpty())
      return this.cardList.remove(this.cardList.size() - 1); 
    return null;
  }
  
  public boolean isEmpty() {
    return (this.cardList.size() == 0);
  }
  
  public void shuffle() {
    for (int i = 0; i < this.cardList.size(); i++) {
      int p = (int)(Math.random() * this.cardList.size());
      Card swap = this.cardList.get(p);
      this.cardList.set(p, this.cardList.get(i));
      this.cardList.set(i, swap);
    } 
  }
}
