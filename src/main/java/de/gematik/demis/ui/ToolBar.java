package de.gematik.demis.ui;

import de.gematik.demis.ui.actions.DemisMenuActionListener;
import de.gematik.demis.utils.ImageUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class ToolBar {

  private final JToolBar toolBar = new JToolBar();
  private final ResourceBundle messages =
      ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
  private final List<JButton> configurationButtons = new ArrayList<>();
  private final JToggleButton expert;

  public ToolBar() {
    DemisMenuActionListener demisMenuActionListener = new DemisMenuActionListener();
    addButton(demisMenuActionListener, "NEW_LAB", false);
    addButton(demisMenuActionListener, "OPEN_ALL", true);
    addButton(demisMenuActionListener, "SAVE_ALL", false);
    toolBar.addSeparator();
    expert = new JToggleButton("Expert - Mode");
    expert.setEnabled(false);
    expert.addActionListener(demisMenuActionListener);
    expert.setActionCommand("EXPERT");
    toolBar.add(expert);
    toolBar.addSeparator();
    addButton(demisMenuActionListener, "CLOSE", false);
  }

  private void addButton(
      DemisMenuActionListener demisMenuActionListener, String name, boolean enabled) {
    JButton button = new JButton(ImageUtils.loadResizeImage(name, 25));
    button.addActionListener(demisMenuActionListener);
    button.setActionCommand(name);
    button.setToolTipText(messages.getString(name));
    button.setEnabled(enabled);
    if (!enabled) {
      configurationButtons.add(button);
    }
    toolBar.add(button);
  }

  public JToolBar getToolBar() {
    return toolBar;
  }

  public void setConfigurationControl(boolean status) {
    configurationButtons.forEach(b -> b.setEnabled(status));
    expert.setEnabled(status);
  }
}
