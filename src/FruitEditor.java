import java.awt.*;
import javax.swing.*;

public class FruitEditor {
  
  // VARIABLES.
  public static final int WIDTH = 800;
  public static final int HEIGHT = 600;
  
  // MAIN FRAME.
  private JFrame fruitFrame;
  
  // PANELS.
  private FruitPanel fruitPanel;
  /*private TilePanel tilePanel;
  private FilePanel filePanel;
  private MapPanel mapPanel;*/
  
  public FruitEditor() {
    fruitFrame = new JFrame("FruitEditor");
    fruitPanel = new FruitPanel();
    
    fruitFrame.pack();
    
    fruitFrame.add(fruitPanel);
    fruitFrame.setJMenuBar(fruitPanel.getMenuBar());
    
    fruitFrame.setLocationRelativeTo(null);
    fruitFrame.setVisible(true);
    fruitFrame.setResizable(false);
  }
}
