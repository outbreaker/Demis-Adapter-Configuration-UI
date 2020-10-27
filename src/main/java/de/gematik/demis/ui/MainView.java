package de.gematik.demis.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainView {

  private static final Logger LOG = LoggerFactory.getLogger(MainView.class.getName());
  private static MainView instance = new MainView();
  private JFrame frame;
  private JClosableTabbedPane configTabs;

  private MainView() {
    LOG.info("Use Language: " + Locale.getDefault().getCountry());
    frame = new JFrame();
    frame.setJMenuBar(new Menu().createMenuBar());
    configTabs = new JClosableTabbedPane();
    frame.add(new ToolBar().getToolBar(), BorderLayout.NORTH);
    frame.add(configTabs, BorderLayout.CENTER);

    frame.setTitle("Demis Adapter-Konfigurator");
    frame.setSize(900, 600);
    frame.setLocation(200, 200);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

  public void addCloeTab(IConfigurationView configurationView) {
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
      if (component instanceof LaboratoryView){
        configTabs.setSelectedIndex(i);
        JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue( verticalScrollBar.getMaximum() );
        return;
      }
    }
  }
}
