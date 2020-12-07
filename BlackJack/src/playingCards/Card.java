package playingCards;

public class Card {
  public static final int SPADES = 1;
  
  public static final int HEARTS = 2;
  
  public static final int DIAMONDS = 3;
  
  public static final int CLUBS = 4;
  
  private int face;
  
  private int value;
  
  public Card(int suit, int value) {
    this.face = suit;
    this.value = value;
  }
  
  public int getFace() {
    return this.face;
  }
  
  public int getValue() {
    return this.value;
  }
  
  public String toString() {
    String faceStr = "";
    switch (this.face) {
      case 1:
        faceStr = "♠";
        break;
      case 2:
        faceStr = "♥";
        break;
      case 3:
        faceStr = "♦";
        break;
      case 4:
        faceStr = "♣";
        break;
    } 
    String val = "";
    if (this.value == 1) {
      val = "A";
    } else if (this.value == 11) {
      val = "J";
    } else if (this.value == 12) {
      val = "Q";
    } else if (this.value == 13) {
      val = "K";
    } else {
      val = (new StringBuilder(String.valueOf(this.value))).toString();
    } 
    return "(" + val + " of " + faceStr + ")";
  }
}
