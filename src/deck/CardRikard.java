package deck;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Card {
  private String suit;
  private int value;
  private ImageIcon icon;
  private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

  public Card(String suit, int value) {
    this.suit = suit;
    this.value = value;

    try {
      this.icon = new ImageIcon(ImageIO
          .read(classLoader.getResourceAsStream("/resources/images/" + value + suit + ".png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getSuit() {
    return suit;
  }

  public int getValue() {
    return value;
  }

  public ImageIcon getIcon() {
    return icon;
  }


}
