import java.util.*;

public class Main {
  public static void main(String[] args) {
    EditorPanel ep = new EditorPanel();
    JFrame frame = new JFrame("FruitEditor");
    
    frame.add(ep);
    frame.pack();
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
