import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class FruitFrame extends JFrame {
  // MAIN PANELS: FRUITPANEL, MAPPANEL, TILEPANEL, MAPLISTPANEL.
  private FruitPanel fruitPanel;
  private MapPanel mapPanel;
  private TilePanel tilePanel;
  private MapListPanel mapListPanel;
  
  // PANES -> SCROLL PANES.
  private JScrollPane mapScroller;
  private JScrollPane tileScroller;
  private JScrollPane mapListScroller;
  // PANES -> SPLIT PANES.
  private JSplitPane splitter;
  
  // OUTER PANELS.
  private JPanel leftPanel;
  private JPanel rightPanel;
  
  public FruitFrame() {
    // Initialize panels.
    initPanel();
    
    setTitle("FruitEditor");
    pack();
    
    setMenuBar(fruitPanel.getMenuBar());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);
  }
  
  private void initPanel() {
    fruitPanel = new FruitPanel();
    mapPanel = new MapPanel();
    tilePanel = new TilePanel();
    mapListPanel = new MapListPanel();
    
    mapScroller = new JScrollPane(mapPanel);
    tileScroller = new JScrollPane(tilePanel);
    mapListScroller = new JScrollPane(mapListPanel);
    
    fruitPanel.setLayout(new BorderLayout());
    
    leftPanel = new JPanel(new FlowLayout());
    rightPanel = new JPanel(new BorderLayout());
    
    leftPanel.add(tileScroller);
    leftPanel.add(mapListScroller);
    rightPanel.add(mapScroller);
    
    // Make a JSplitPane to divide the tileset and the map panels.
    // Tileset and map list panels go left, map panel goes right.
    splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    splitter.setDividerLocation(240);
    
    add(fruitPanel, BorderLayout.CENTER);
    add(splitter, BorderLayout.CENTER);
  }
  
  //==============================
  // GET PANELS.
  //==============================
  public MapPanel getMapPanel() {
    if (mapPanel == null) { return null; }
    return mapPanel;
  }
  
  public TilePanel getTilePanel() {
    if (tilePanel == null) { return null; }
    return tilePanel;
  }
  
  public MapListPanel getMapListPanel() {
    if (mapListPanel == null) { return null; }
    return mapListPanel;
  }
}
