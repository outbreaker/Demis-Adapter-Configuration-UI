package de.gematik.demis.ui;

import de.gematik.demis.ui.actions.DemisMenuActionListener;
import de.gematik.demis.utils.ImageUtils;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class ToolBar {

  private JToolBar toolBar = new JToolBar();

  public ToolBar() {
    DemisMenuActionListener demisMenuActionListener = new DemisMenuActionListener();
    addButton(demisMenuActionListener, "NEW_JSON");
    addButton(demisMenuActionListener, "OPEN_ALL");
    addButton(demisMenuActionListener, "SAVE_ALL");
    toolBar.addSeparator();
    JToggleButton expert = new JToggleButton("Expert - Mode");
    expert.addActionListener(demisMenuActionListener);
    expert.setActionCommand("EXPERT");
    toolBar.add(expert);
    toolBar.addSeparator();
    addButton(demisMenuActionListener, "CLOSE");
  }

  private void addButton(DemisMenuActionListener demisMenuActionListener, String name) {
    JButton button = new JButton(ImageUtils.loadResizeImage(name, 25));
    button.addActionListener(demisMenuActionListener);
    button.setActionCommand(name);
    toolBar.add(button);
  }

  public JToolBar getToolBar() {
    return toolBar;
  }
}
