package de.gematik.demis.ui.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.entities.Laboratory;
import de.gematik.demis.ui.AbstractConfigurationView;
import de.gematik.demis.ui.LaboratoryView;
import de.gematik.demis.ui.MainView;
import de.gematik.demis.ui.MessageWithLinksPane;
import de.gematik.demis.ui.PropertiesView;
import de.gematik.demis.utils.ProjectVersionUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemisMenuActionListener implements ActionListener {

  private static final Logger LOG =
      LoggerFactory.getLogger(DemisMenuActionListener.class.getName());
  private static File lastPath;
  private final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    switch (actionEvent.getActionCommand()) {
      case "OPEN_ALL":
        JFileChooser jFileChooser =
            new JFileChooser("C:\\Demis\\Demis-Adapter-1.1.0 -- Docker LAB-01.01.01.100");
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int opt = jFileChooser.showOpenDialog(MainView.getInstance().getMainComponent());
        if (opt == JFileChooser.APPROVE_OPTION) {
          File folderToLoad = jFileChooser.getSelectedFile();
          ConfigurationLoader.getInstance().loadAll(folderToLoad);
          LOG.debug("Selected Folder to Load all Configurations: " + folderToLoad);
        }
        break;
      case "OPEN":
        break;
      case "SAVE_ALL":
        saveAll();
        break;
      case "EXPERT":
        if (actionEvent.getSource() instanceof JToggleButton) {
          JToggleButton jToggleButton = (JToggleButton) actionEvent.getSource();
          if (jToggleButton.isSelected()) {
            ConfigurationLoader.getInstance()
                .getLaboratoryViews()
                .forEach(LaboratoryView::activateForExperts);
            ConfigurationLoader.getInstance()
                .getPropertiesViews()
                .forEach(PropertiesView::activateForExperts);
          } else {
            ConfigurationLoader.getInstance()
                .getLaboratoryViews()
                .forEach(LaboratoryView::checkExpertMode);
            ConfigurationLoader.getInstance()
                .getPropertiesViews()
                .forEach(PropertiesView::checkExpertMode);
          }
        }
        break;
      case "HELP_WDB":
        openHelpDialog(messages);
        LOG.debug("CLick on Help!");
        break;
      case "ABOUT":
        openAboutDialog(messages);
        break;
      default:
        LOG.warn("Action for Command \"" + actionEvent.getActionCommand() + "\" not implemented");
    }
  }

  public void saveAll() {
    ConfigurationLoader.getInstance()
        .getLaboratoryViews().stream().filter(AbstractConfigurationView::hasUnsavedChanges)
        .forEach(
            lab -> {
              Path path = checkPath(messages, lab.getPath(), "json", "LOAD_JSON_DESCRIPTION");
              if (path != null) {
                saveJson(path, lab.getLaboratory());
                lab.setSaved();
              }
            });

    ConfigurationLoader.getInstance()
        .getPropertiesViews().stream().filter(AbstractConfigurationView::hasUnsavedChanges)
        .forEach(
            props -> {
              Path path =
                  checkPath(
                      messages, props.getPath(), "properties", "LOAD_PROPERTIES_DESCRIPTION");
              if (path != null) {
                saveProperties(path, props.getProperties());
                props.setSaved();
              }
            });
    String successMessage =
        ResourceBundle.getBundle("MessagesBundle", Locale.getDefault())
            .getString("SAVE_PROPERTIES_SUCCESSFULL");
    JOptionPane.showMessageDialog(
        MainView.getInstance().getMainComponent(),
        successMessage,
        "Information",
        JOptionPane.INFORMATION_MESSAGE);
  }

  private Path checkPath(
      final ResourceBundle messages,
      final Path path,
      final String fileType,
      final String descriptionId) {
    if (path == null) {
      JFileChooser universalFileChooser =
          getUniversalFileChooser(messages, descriptionId, fileType);
      if (universalFileChooser.showSaveDialog(MainView.getInstance().getMainComponent())
          == JFileChooser.APPROVE_OPTION) {
        File folderToLoad = universalFileChooser.getSelectedFile();
        if (!folderToLoad.getAbsolutePath().toLowerCase().endsWith("." + fileType)) {
          lastPath = new File(folderToLoad.getAbsolutePath() + "." + fileType);
        } else {
          lastPath = folderToLoad.getAbsoluteFile();
        }
        return lastPath.toPath();
      }
    }
    return path;
  }

  private void saveProperties(Path path, Properties properties) {
    try {
      properties.store(new FileOutputStream(path.toFile()), null);
    } catch (IOException e) {
      LOG.error("Failed to Save Properties to " + path.toFile().getAbsolutePath(), e);
      String errorMessage =
          ResourceBundle.getBundle("MessagesBundle", Locale.getDefault())
              .getString("SAVE_PROPERTIES_ERROR");
      errorMessage =
          errorMessage
              .replace("XX_PATH_XX", path.toString())
              .replace("XX_ERROR_XX", e.getMessage());
      JOptionPane.showMessageDialog(
          MainView.getInstance().getMainComponent(),
          errorMessage,
          "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void saveJson(Path path, Laboratory laboratory) {
    LOG.debug("Save Laboratory Json to: " + path.toFile().getAbsolutePath());
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), laboratory);
    } catch (IOException e) {
      LOG.error("Failed to Save Json to " + path.toFile().getAbsolutePath(), e);
      String errorMessage =
          ResourceBundle.getBundle("MessagesBundle", Locale.getDefault())
              .getString("SAVE_JSON_ERROR");
      errorMessage =
          errorMessage
              .replace("XX_PATH_XX", path.toString())
              .replace("XX_ERROR_XX", e.getMessage());
      JOptionPane.showMessageDialog(
          MainView.getInstance().getMainComponent(),
          errorMessage,
          "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private JFileChooser getUniversalFileChooser(
      ResourceBundle messages, String description, String fileEnds) {
    JFileChooser jFileChooser = new JFileChooser(lastPath);
    jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    jFileChooser.setFileFilter(
        new FileFilter() {
          @Override
          public boolean accept(File file) {
            return file.getName().toLowerCase().endsWith(fileEnds) || file.isDirectory();
          }

          @Override
          public String getDescription() {
            return messages.getString(description);
          }
        });
    return jFileChooser;
  }

  private void openHelpDialog(ResourceBundle messages) {
    String help = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault()).getString("HELP_DESCRIPTION");
    String error = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault()).getString("HELP_ERROR");
    JOptionPane.showMessageDialog(MainView.getInstance().getMainComponent(), new MessageWithLinksPane(help, error),"Hilfe", JOptionPane.QUESTION_MESSAGE);
  }

  private void openAboutDialog(ResourceBundle messages) {
    String version = ProjectVersionUtils.getProjectVersion();
    String versionMessage = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault()).getString("VERSION");
    JOptionPane.showMessageDialog(MainView.getInstance().getMainComponent(), versionMessage + version, "Information", JOptionPane.INFORMATION_MESSAGE);
  }
}
