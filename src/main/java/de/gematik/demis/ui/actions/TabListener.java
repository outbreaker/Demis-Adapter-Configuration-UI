package de.gematik.demis.ui.actions;

import de.gematik.demis.ui.IConfigurationView;
import de.gematik.demis.ui.MainView;
import de.gematik.demis.utils.ImageUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class TabListener implements MouseListener, ActionListener {

  private final JButton btnClose;
  private final JTabbedPane jTabbedPane;
  private final IConfigurationView configurationView;

  public TabListener(final JButton btnClose, final JTabbedPane jTabbedPane, final IConfigurationView configurationView) {
    super();
    this.btnClose = btnClose;
    this.jTabbedPane = jTabbedPane;
    this.configurationView = configurationView;
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    if (e.getButton() == 2) {
      closeTab();
    } else if (e.getButton() == 1) {
      jTabbedPane.setSelectedComponent(configurationView.getComponent());
    }
  }

  @Override
  public void mousePressed(final MouseEvent e) {
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
    btnClose.setIcon(ImageUtils.loadResizeImage("closeTab", 15));
  }

  @Override
  public void mouseExited(final MouseEvent e) {
    btnClose.setIcon(ImageUtils.loadResizeImage("closeTabDisabled", 10));
  }

  private void closeTab() {
    var messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());

    if (JOptionPane
        .showConfirmDialog(MainView.getInstance().getMainComponent(), messages.getString("CLOSE_JSON_MSG"), messages.getString("CLOSE_JSON_TITLE"),
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
      int index = jTabbedPane.indexOfComponent(configurationView.getComponent());
      String tabTitle = jTabbedPane.getTitleAt(index);
      jTabbedPane.remove(index);
    }
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    closeTab();
  }

}

