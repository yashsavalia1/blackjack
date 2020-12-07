package game;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class FinishPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  
  private Player player;
  
  private int newBankRoll;
  
  private int changingBankRoll;
  
  private int oldBankRoll;
  
  public FinishPanel() {
    Border b = BorderFactory.createTitledBorder("");
    setBorder(b);
    setLayout(new FlowLayout(1));
  }
  
  public void endGame(Player player, int originalBankRoll) {
    this.player = player;
    this.newBankRoll = player.getBankRoll();
    this.oldBankRoll = originalBankRoll;
    this.changingBankRoll = originalBankRoll;
  }
  
  protected void paintComponent(Graphics g) {
    repaint();
    super.paintComponent(g);
    g.setFont(new Font("", 0, 24));
    g.drawString(String.valueOf(this.player.toString()) + "'s Bankroll: " + this.changingBankRoll, 500, 280);
    if (this.changingBankRoll < this.newBankRoll) {
      this.changingBankRoll++;
      BlackJack.pause((3000 / (this.newBankRoll - this.oldBankRoll)));
    } 
  }
}
