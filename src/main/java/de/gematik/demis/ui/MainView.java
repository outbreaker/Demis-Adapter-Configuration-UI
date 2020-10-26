package de.gematik.demis.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Locale;
import javax.swing.JFrame;

public class MainView {

  private static MainView instance = new MainView();
  private JFrame frame;
  private JClosableTabbedPane configTabs;

  private MainView() {
    System.out.println(">>>>>>>" + Locale.getDefault().getCountry());
    frame = new JFrame();
    frame.setJMenuBar(new Menu().createMenuBar());
    configTabs = new JClosableTabbedPane();
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
}
