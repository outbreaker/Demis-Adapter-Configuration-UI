package de.gematik.demis.ui;

import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.ui.actions.DemisMenuActionListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainView {

  private static final Logger LOG = LoggerFactory.getLogger(MainView.class.getName());
  private static MainView instance = new MainView();
  private final JFrame frame;
  private final JClosableTabbedPane configTabs;
  private final Menu menuBar;
  private final ToolBar toolBar;
  private final ResourceBundle messages =
      ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());

  private MainView() {
    LOG.info("Use Language: " + Locale.getDefault().getCountry());
    frame = new JFrame();
    menuBar = new Menu();
    frame.setJMenuBar(menuBar.createMenuBar());
    configTabs = new JClosableTabbedPane();
    toolBar = new ToolBar();
    frame.add(toolBar.getToolBar(), BorderLayout.NORTH);
    frame.add(configTabs, BorderLayout.CENTER);

    frame.setTitle("Demis Adapter-Konfigurator");
    frame.setSize(900, 600);
    frame.setLocation(200, 200);

    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(
        new WindowAdapter() {

          @Override
          public void windowClosing(WindowEvent e) {
            super.windowClosing(e);

            closeApplication();
          }
        });
  }

  public void closeApplication() {
    if (ConfigurationLoader.getInstance().hasUnsavedChanges()) {
      int i =
          JOptionPane.showConfirmDialog(
              MainView.getInstance().getMainComponent(),
              messages.getString("CLOSE_APP_WITH_UNSAVED_CHANGES"),
              messages.getString("CLOSE_APP_WITH_UNSAVED_CHANGES_TITLE"),
              JOptionPane.YES_NO_CANCEL_OPTION);
      if (i == JOptionPane.YES_OPTION) {
        new DemisMenuActionListener().saveAll();
        exit();
      } else if (i == JOptionPane.NO_OPTION) {
        exit();
      }
    } else {
      int i =
          JOptionPane.showConfirmDialog(
              MainView.getInstance().getMainComponent(),
              messages.getString("CLOSE_APP"),
              messages.getString("CLOSE_APP_TITLE"),
              JOptionPane.YES_NO_OPTION);
      if (i == JOptionPane.YES_OPTION) {
        exit();
      }
    }
  }

  private void exit() {
    frame.dispose();
    System.exit(0);
  }

  public static MainView getInstance() {
    if (instance == null) {
      instance = new MainView();
    }
    return instance;
  }

  public void addTab(IConfigurationView configurationView) {
    configTabs.addTab(configurationView);
  }

  public void addCloseTab(IConfigurationView configurationView) {
    configTabs.addClosableTab(configurationView);
  }

  public Component getMainComponent() {
    return getInstance().frame;
  }

  public JClosableTabbedPane getJTabs() {
    return getInstance().configTabs;
  }

  public void show() {
    frame.setVisible(true);
  }

  public void showTabFor(LaboratoryView laboratoryView) {
    for (int i = 0; i < configTabs.getTabCount(); i++) {
      JScrollPane jScrollPane = (JScrollPane) configTabs.getComponentAt(i);
      Component component = jScrollPane.getViewport().getComponent(0);
      if (component instanceof LaboratoryView) {
        configTabs.setSelectedIndex(i);
        JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        return;
      }
    }
  }

  public void setConfigurationControl(boolean status) {
    menuBar.setConfigurationControl(status);
    toolBar.setConfigurationControl(status);
  }
}
