package de.gematik.demis.ui.value.editor;

import de.gematik.demis.ui.MainView;
import de.gematik.demis.utils.ImageUtils;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RelativPathEditor extends AbstractEditor {

  private static String lastPath;
  private final JTextField relativPath;
  private final JButton dialogJb;

  public RelativPathEditor() {
    this.setLayout(new BorderLayout());
    relativPath = new JTextField();
    relativPath.setEditable(false);
    add(relativPath, BorderLayout.CENTER);
    dialogJb = new JButton(ImageUtils.loadResizeImage("OPEN_FILE", 15));
    dialogJb.addActionListener(actionEvent -> selectFolder());
    dialogJb.setEnabled(!isExpertEditor());
    relativPath.setEnabled(!isExpertEditor());
    add(dialogJb, BorderLayout.EAST);
  }

  private void selectFolder() {
    JFileChooser jFileChooser = new JFileChooser(lastPath);
    jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int opt = jFileChooser.showOpenDialog(MainView.getInstance().getMainComponent());
    if (opt == JFileChooser.APPROVE_OPTION) {
      File folderToLoad = jFileChooser.getSelectedFile();
      lastPath = folderToLoad.getAbsolutePath();
      relativPath.setText(lastPath);
    }
  }

  @Override
  public String getValue() {
    return relativPath.getText();
  }

  @Override
  public void setValue(String value) {
    relativPath.setText(value);
  }

  @Override
  public JComponent getViewComponent() {
    return this;
  }

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    relativPath.addKeyListener(
        new KeyAdapter() {
          @Override
          public void keyTyped(KeyEvent e) {
            changeListener.stateChanged(new ChangeEvent(RelativPathEditor.this));
          }
        });
  }

  @Override
  public void checkExpertMode() {
    dialogJb.setEnabled(!isExpertEditor());
    relativPath.setEnabled(!isExpertEditor());
  }

  @Override
  public void activateForExperts() {
    dialogJb.setEnabled(true);
    relativPath.setEnabled(true);
  }
}
