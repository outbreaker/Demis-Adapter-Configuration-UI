package de.gematik.demis.ui.value.editor;

import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.ui.MainView;
import de.gematik.demis.utils.ImageUtils;
import de.gematik.demis.utils.PathUtils;
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
import javax.swing.filechooser.FileFilter;

public class RelativPathEditor extends AbstractEditor {

  private static String lastPath;
  private final JTextField relativPath;
  private final JButton dialogJb;
  private String[] fileExtension;
  private String fileExtensionDescription;

  public RelativPathEditor(String[] fileExtension) {
    this();
    setFileExtensions(fileExtension);
  }

  public RelativPathEditor() {
    this.setLayout(new BorderLayout());
    relativPath = new JTextField();
    relativPath.setEditable(false);
    add(relativPath, BorderLayout.CENTER);
    dialogJb = new JButton(ImageUtils.loadResizeImage("OPEN_FILE", 15));
    dialogJb.addActionListener(actionEvent -> selectFile());
    dialogJb.setEnabled(!isExpertEditor());
    relativPath.setEnabled(!isExpertEditor());
    add(dialogJb, BorderLayout.EAST);
  }

  public void setFileExtensions(String[] fileExtension) {
    this.fileExtension = fileExtension;
    fileExtensionDescription = "";
    for (String ext : fileExtension) {
      fileExtensionDescription += " *." + ext + ",";
    }
    fileExtensionDescription =
        fileExtensionDescription.substring(0, fileExtensionDescription.length() - 1);
  }

  private void selectFile() {
    JFileChooser jFileChooser =
        new JFileChooser(
            (lastPath == null
                ? ConfigurationLoader.getInstance().getPathToMainFolder().toFile().getAbsolutePath()
                : lastPath));
    jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    if (fileExtension != null) {
      jFileChooser.setFileFilter(
          new FileFilter() {
            @Override
            public boolean accept(File file) {
              for (String ext : fileExtension) {
                if (file.getName().toLowerCase().endsWith(ext)) return true;
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
