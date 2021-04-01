package de.gematik.demis.ui.value.editor;

import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.ui.MainView;
import de.gematik.demis.utils.ImageUtils;
import de.gematik.demis.utils.PathUtils;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RelativPathEditor extends AbstractEditor {

  private static String lastPath;
  private final JTextField relativPath;
  private final JButton dialogJb;
  private String fileExtension;
  private String fileExtensionDescription;
  private boolean selectDir;


  public RelativPathEditor(boolean selectDir, String fileExtension) {
    setFileExtensions(fileExtension);
    this.selectDir = selectDir;
    this.setLayout(new BorderLayout());
    relativPath = new JTextField();
    relativPath.setEditable(false);
    add(relativPath, BorderLayout.CENTER);
    dialogJb = new JButton(ImageUtils.loadResizeImage("OPEN_FILE", 15));
    if (selectDir) {
      dialogJb.addActionListener(actionEvent -> select(JFileChooser.DIRECTORIES_ONLY));
    } else {
      dialogJb.addActionListener(actionEvent -> select(JFileChooser.FILES_ONLY));
    }
    dialogJb.setEnabled(!isExpertEditor());
    relativPath.setEnabled(!isExpertEditor());
    add(dialogJb, BorderLayout.EAST);
  }

  public void setFileExtensions(String fileExtension) {
    this.fileExtension = fileExtension;
    fileExtensionDescription = "." + fileExtension;
  }

  private void select(int mode) {
    JFileChooser jFileChooser =
        new JFileChooser(
            (lastPath == null
                ? ConfigurationLoader.getInstance().getPathToMainFolder().toFile().getAbsolutePath()
                : lastPath));
    jFileChooser.setFileSelectionMode(mode);
    if (fileExtension != null) {
      jFileChooser.setFileFilter(
          new FileFilter() {
            @Override
            public boolean accept(File file) {
              if (file.getName().toLowerCase().endsWith(fileExtension)) {
                return true;
              }
              return file.isDirectory();
            }

            @Override
            public String getDescription() {
              return fileExtensionDescription;
            }
          });
    }
    int opt = jFileChooser.showOpenDialog(MainView.getInstance().getMainComponent());
    if (opt == JFileChooser.APPROVE_OPTION) {
      File selectedFile = jFileChooser.getSelectedFile();
      lastPath = selectedFile.getAbsolutePath();
      relativPath.setText(PathUtils.getRelativPath(selectedFile.toPath()));
      fireTabChangedEvent(jFileChooser);
    }
  }

  protected void fireTabChangedEvent(Object jFileChooser) {
    ChangeEvent evt = new ChangeEvent(jFileChooser);
    Object[] listeners = listenerList.getListenerList();
    for (int i = 0; i < listeners.length; i = i + 2) {
      if (listeners[i] == ChangeListener.class) {
        ((ChangeListener) listeners[i + 1]).stateChanged(evt);
      }
    }
  }

  public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
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
    listenerList.add(ChangeListener.class, changeListener);
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
