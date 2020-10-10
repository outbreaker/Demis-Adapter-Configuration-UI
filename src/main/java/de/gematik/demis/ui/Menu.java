package de.gematik.demis.ui;

import de.gematik.demis.ui.actions.DemisMenuActionListener;
import de.gematik.demis.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

class Menu {
    private static Logger LOG = LoggerFactory.getLogger(Menu.class.getName());
    private ActionListener demisMenuActionListener = new DemisMenuActionListener();

    JMenuBar createMenuBar() {
        var messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(messages.getString("FILE"));
        fileMenu.add(getMenuItem(messages, "OPEN_ALL"));
        fileMenu.add(getMenuItem(messages, "OPEN"));
        fileMenu.add(getMenuItem(messages, "CLOSE"));
        fileMenu.add(getMenuItem(messages, "EXIT"));
        JMenu editMenu = new JMenu(messages.getString("EDIT"));
        JMenu helpMenu = new JMenu(messages.getString("HELP"));
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

    private JMenuItem getMenuItem(ResourceBundle messages, String name) {
        JMenuItem jMenuItem = new JMenuItem(messages.getString(name));
        jMenuItem.setActionCommand(name);
        ImageIcon imageIcon = ImageUtils.loadResizeImage(name, 25);
        if (imageIcon != null)
            jMenuItem.setIcon(imageIcon);
        jMenuItem.addActionListener(demisMenuActionListener);
        return jMenuItem;
    }
}
