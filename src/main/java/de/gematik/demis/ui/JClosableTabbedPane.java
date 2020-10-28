package de.gematik.demis.ui;

import de.gematik.demis.ui.actions.TabListener;
import de.gematik.demis.utils.ImageUtils;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JClosableTabbedPane extends JTabbedPane implements ChangeListener {

  private static final int WEIGHT = 100;
  private static final long serialVersionUID = -5528721340289834656L;

  public void addTab(final IConfigurationView configurationView) {
    super.addTab(configurationView.getName(), configurationView.getComponent());
    configurationView.addChangeListener(this);
  }

  public void addClosableTab(final IConfigurationView configurationView) {
    addTab(configurationView);
    addCloseButton(configurationView);
  }

  public int indexOfTabWithoutComponent(final String title) {
    for (int i = 0; i < getTabCount(); i++) {
      if (getTitleAt(i).equals(title == null ? "" : title) && getTabComponentAt(i) == null) {
        return i;
      }
    }
    return -1;
  }

  private void addCloseButton(IConfigurationView configurationView) {
    int index = indexOfTabWithoutComponent(configurationView.getName());
    JPanel pnlTab = new JPanel(new GridBagLayout());
    pnlTab.setOpaque(false);
    final JLabel lblTitle = new JLabel(configurationView.getName());
    if (configurationView.getPath() != null)
      lblTitle.setToolTipText(configurationView.getPath().toAbsolutePath().toString());
    final JButton btnClose = createCloseButton();

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = WEIGHT;

    pnlTab.add(lblTitle, gbc);

    if (this.tabPlacement == SwingConstants.LEFT) {
      gbc.gridy++;
    } else {
      gbc.gridx++;
    }
    gbc.weightx = 1;
    pnlTab.add(btnClose, gbc);

    setTabComponentAt(index, pnlTab);
    TabListener tabListeners = new TabListener(btnClose, this, configurationView);
    btnClose.addActionListener(tabListeners);
    pnlTab.addMouseListener(tabListeners);
    btnClose.addMouseListener(tabListeners);
  }

  private JButton createCloseButton() {
    final JButton btnClose =
        new JButton(ImageUtils.loadResizeImage("closeTabDisabled", 10)) {

          private static final long serialVersionUID = 3964294119405101848L;
          private static final int HEIGHT = 25;

          @Override
          public Dimension getPreferredSize() {
            Dimension dimension = super.getPreferredSize();
            dimension.setSize(HEIGHT, dimension.getHeight());
            return dimension;
          }
        };
    btnClose.setBorderPainted(false);
    btnClose.setFocusPainted(false);
    btnClose.setOpaque(false);
    btnClose.setContentAreaFilled(false);
    return btnClose;
  }

  @Override
  public void stateChanged(ChangeEvent changeEvent) {
    if (changeEvent.getSource() instanceof IConfigurationView) {
      IConfigurationView configurationView = (IConfigurationView) changeEvent.getSource();
      String title =
          configurationView.getName() + (configurationView.hasUnsavedChanges() ? " *" : "");
      String path =
          configurationView.getPath() == null
              ? ""
              : configurationView.getPath().toAbsolutePath().toString();
      int index = indexOfComponent(configurationView.getComponent());
      if (getTabComponentAt(index) instanceof JPanel) {
        Arrays.stream(((JPanel) getTabComponentAt(index)).getComponents())
            .filter(s -> s instanceof JLabel)
            .forEach(
                s -> {
                  ((JLabel) s).setText(title);
                  ((JLabel) s).setToolTipText(path);
                });
      } else {
        setTitleAt(index, title);
      }
    }
  }
}
