package game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class PlayerPanel extends JPanel implements ActionListener {
  private static final long serialVersionUID = 1L;
  
  private Player player;
  
  private Player dealer;
  
  static JButton hitButton = new JButton("Hit");
  
  static JButton standButton = new JButton("Stand");
  
  static HintAdvisor hintAdvisor = new HintAdvisor();
  
  public PlayerPanel(Player dealer) {
    Border b = BorderFactory.createTitledBorder("");
    setBorder(b);
    setLayout(new FlowLayout(1));
    this.dealer = dealer;
  }
  
  public void displayPlayerCards(Player player) {
    this.player = player;
    setVisible(false);
    removeAll();
    ((TitledBorder)getBorder()).setTitle(player + "'s Turn");
    setVisible(true);
    if (player != this.dealer) {
      hitButton.addActionListener(this);
      standButton.addActionListener(this);
      add(hitButton);
      add(standButton);
    } 
  }
  
  protected void paintComponent(Graphics g) {
    repaint();
    super.paintComponent(g);
    g.setFont(new Font("", 0, 18));
    int xVal = 200;
    for (int i = 0; i < this.player.getHand().numCards(); i++) {
      g.drawRoundRect(xVal + i * 125, 400, 100, 150, 20, 20);
      g.drawString(this.player.getHand().getCard(i).toString(), xVal + 15 + i * 125, 475);
    } 
    String soft = this.player.getHand().hasHighAces() ? "Soft " : "";
    g.drawString("Score: " + soft + this.player.getHand().getScore(), 25, 315);
    if (this.player.isBusted()) {
      g.setFont(new Font("", 1, 22));
      g.setColor(Color.RED);
      g.drawString("BUSTED!", 25, 500);
      g.setFont(new Font("", 0, 18));
      g.setColor(Color.BLACK);
    } else if (!this.player.isActive()) {
      g.drawString("STANDED", 25, 500);
    } 
    if (this.player != this.dealer) {
      g.drawString("Dealer's Cards", 120, 85);
      g.drawString("Your Cards", 120, 385);
      g.drawString("Bankroll: " + this.player.getBankRoll(), 25, 335);
      g.drawString("Hint: " + 
          hintAdvisor.getHint(this.player.getHand().getScore(), this.player.getHand().hasHighAces()), 300, 315);
      g.drawLine(15, 285, 875, 285);
      g.drawRoundRect(200, 100, 100, 150, 20, 20);
      g.drawRoundRect(325, 100, 100, 150, 20, 20);
      g.setColor(Color.RED);
      g.fillRect(210, 110, 80, 130);
      g.setColor(Color.BLACK);
      g.drawString(this.dealer.getHand().getCard(1).toString(), 340, 175);
    } 
  }
  
  public void actionPerformed(ActionEvent e) {
    this.player.recieveInput(e);
  }
}
