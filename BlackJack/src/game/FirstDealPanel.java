package game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class FirstDealPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  
  private Player player;
  
  public FirstDealPanel() {
    Border b = BorderFactory.createTitledBorder("");
    setBorder(b);
    setLayout(new FlowLayout());
  }
  
  public void displayPlayerCards(Player player) {
    this.player = player;
    removeAll();
    setVisible(false);
    ((TitledBorder)getBorder()).setTitle(player + "'s cards");
    setVisible(true);
  }
  
  protected void paintComponent(Graphics g) {
    repaint();
    super.paintComponent(g);
    g.drawRoundRect(50, 50, 100, 150, 20, 20);
    g.drawRoundRect(175, 50, 100, 150, 20, 20);
    g.setFont(new Font("", 0, 18));
    if (this.player.isDealer()) {
      g.setColor(Color.RED);
      g.fillRect(60, 60, 80, 130);
      g.setColor(Color.BLACK);
    } else {
      g.drawString(this.player.getHand().getCard(0).toString(), 65, 125);
      g.drawString("Score: " + this.player.getHand().getScore(), 25, 275);
      if (this.player.getHand().isBlackJack()) {
        g.setColor(Color.MAGENTA);
        g.drawString("BLACKJACK!", 25, 315);
        g.setColor(Color.BLACK);
      } 
    } 
    g.drawString(this.player.getHand().getCard(1).toString(), 190, 125);
  }
}
