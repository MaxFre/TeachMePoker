package player;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import deck.Card;


/**
 * The Player class holds values and a few methods for the playable Player
 * 
 * @author Tzeentch
 * @version 0.2
 */
public class Player {

  private String name;
  private int playerPot;
  private String decision = "";
  private Card card1, card2;
  private int alreadyPaid = 0;
  private int highCard;

  /**
   * Method which forces the player to pay the small blind when it is his turn
   * 
   * @param i The amount to pay
   */
  public void smallBlind(int i) {

    this.alreadyPaid += i;
    System.out.println("Player paid small blind(" + i + ")");

  }


  /**
   * Method which forces the player to pay the big blind when it is his turn
   * 
   * @param i The amount to pay
   */
  public void bigBlind(int i) {

    this.alreadyPaid += i;
    System.out.println("Player paid big blind(" + i + ")");
  }


  /*
   * Method which returns the players decision.
   */
  public String getDecision() {

    return decision;
  }


  /**
   * Method which resets the players decision and his "stored cards on the table"
   * 
   * @param resetDecision Resets his decision
   */
  public void reset(String resetDecision) {

    decision = resetDecision;
    alreadyPaid = 0;
  }


  /**
   * Method which returns the players pot
   * 
   * @return playerPot
   */
  public int getPlayerPot() {

    return playerPot;
  }


  public void setPlayerPot(int newValue) {

    this.playerPot += newValue;
  }


  public String getName() {

    return name;
  }


  public int getAlreadyPaid() {

    return alreadyPaid;
  }


  public void setAlreadyPaid(int newValue) {

    this.alreadyPaid += newValue;
  }


  public int getHighCard() {

    return highCard;
  }


  public void getHandStrength() {
    
    // TODO Auto-generated method stub
    
  }


  public void setDecision(String playerDecision) {
    this.decision = playerDecision;
    
  }

}
