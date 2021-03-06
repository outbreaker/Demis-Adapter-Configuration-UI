package de.gematik.demis.ui;

import de.gematik.demis.ui.actions.DemisMenuActionListener;
import de.gematik.demis.utils.ImageUtils;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Menu {

  private static final Logger LOG = LoggerFactory.getLogger(Menu.class.getName());
  private final ActionListener demisMenuActionListener = new DemisMenuActionListener();
  private final ResourceBundle messages =
      ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
  private List<JMenuItem> configurationItems = new ArrayList<>();

  JMenuBar createMenuBar() {

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu(messages.getString("FILE"));
    fileMenu.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            // UI Workaround
            MainView.getInstance().getJTabs().setVisible(false);
            MainView.getInstance().getJTabs().setVisible(true);
          }
        });
    fileMenu.add(getMenuItem(messages, "NEW_LAB", false));
    fileMenu.add(getMenuItem(messages, "OPEN_ALL", true));
    fileMenu.add(getMenuItem(messages, "SAVE_ALL", false));
    fileMenu.add(getMenuItem(messages, "CLOSE", false));
    fileMenu.add(getMenuItem(messages, "EXIT", true));
    //    JMenu editMenu = new JMenu(messages.getString("EDIT")); //disabled for first Version
    JMenu helpMenu = new JMenu(messages.getString("HELP"));
    helpMenu.add(getMenuItem(messages, "HELP_WDB", true));
    helpMenu.add(getMenuItem(messages, "ABOUT", true));
    menuBar.add(fileMenu);
    //    menuBar.add(editMenu);//disabled for first Version
    menuBar.add(helpMenu);
    return menuBar;
  }

  private JMenuItem getMenuItem(ResourceBundle messages, String name, boolean enabled) {
    JMenuItem jMenuItem = new JMenuItem(messages.getString(name));
    jMenuItem.setActionCommand(name);
    jMenuItem.setEnabled(enabled);
    if (!enabled) {
      configurationItems.add(jMenuItem);
    }
    ImageIcon imageIcon = ImageUtils.loadResizeImage(name, 25);
    if (imageIcon != null) {
      jMenuItem.setIcon(imageIcon);
    }
    jMenuItem.addActionListener(demisMenuActionListener);
    return jMenuItem;
  }

  public void setConfigurationControl(boolean status) {
    configurationItems.forEach(i -> i.setEnabled(status));
  }
}
