package de.gematik.demis.ui.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.entities.Laboratory;
import de.gematik.demis.ui.MainView;
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
import javax.swing.filechooser.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemisMenuActionListener implements ActionListener {

  private static Logger LOG = LoggerFactory.getLogger(DemisMenuActionListener.class.getName());
  private static File lastPath;

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    var messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());

    switch (actionEvent.getActionCommand()) {
      case "OPEN_ALL":
        JFileChooser jFileChooser = new JFileChooser(
            "C:\\Demis\\Demis-Adapter-1.1.0 -- Docker LAB-01.01.01.100");
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

        ConfigurationLoader.getInstance().getLaboratoryViews().forEach(
            lab -> {
              Path path = lab.getPath();
              if (path == null) {
                JFileChooser jsonFileChooser = getJsonFileChooser(messages);
                if (jsonFileChooser.showSaveDialog(MainView.getInstance().getMainComponent()) == JFileChooser.APPROVE_OPTION) {
                  File folderToLoad = jsonFileChooser.getSelectedFile();
                  if (!folderToLoad.getAbsolutePath().toLowerCase().endsWith(".json")) {
                    lastPath = new File(folderToLoad.getAbsolutePath() + ".json");
                  } else {
                    lastPath = folderToLoad.getAbsoluteFile();
                  }
                  path = lastPath.toPath();
                  saveJson(path, lab.getLaboratory());
                }
              } else {
                saveJson(path, lab.getLaboratory());
              }
            }
        );

        ConfigurationLoader.getInstance().getPropertiesViews().forEach(props -> {
          Path path = props.getPath();
          if (path == null) {
            JFileChooser propsFileChooser = getPropsFileChooser(messages);
            if (propsFileChooser.showSaveDialog(MainView.getInstance().getMainComponent()) == JFileChooser.APPROVE_OPTION) {
              File folderToLoad = propsFileChooser.getSelectedFile();
              if (!folderToLoad.getAbsolutePath().toLowerCase().endsWith(".properties")) {
                lastPath = new File(folderToLoad.getAbsolutePath() + ".properties");
              } else {
                lastPath = folderToLoad.getAbsoluteFile();
              }
              path = lastPath.toPath();
              saveProperties(path, props.getProperties());
            }
          } else {
            saveProperties(path, props.getProperties());
          }

        });
        break;
      default:
        LOG.warn("Action for Command \"" + actionEvent.getActionCommand() + "\" not implemented");
    }
  }

  private void saveProperties(Path path, Properties properties) {
    try {
      properties.store(new FileOutputStream(path.toFile()), null);
    } catch (IOException e) {
      LOG.error("Failed to Save Properties to " + path.toFile().getAbsolutePath(), e);
      //TODO POPUP with Error
    }
  }

  private void saveJson(Path path, Laboratory laboratory) {
    LOG.debug("Save Laboratory Json to: " + path.toFile().getAbsolutePath());
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), laboratory);
    } catch (IOException e) {
      LOG.error("Failed to Save Json to " + path.toFile().getAbsolutePath(), e);
      //TODO POPUP with Error
    }

  }

  private JFileChooser getJsonFileChooser(ResourceBundle messages) {
    return getUniversalFileChooser(messages, "LOAD_JSON_DESCRIPTION", "json");
  }

  private JFileChooser getPropsFileChooser(ResourceBundle messages) {
    return getUniversalFileChooser(messages, "LOAD_PROPERTIES_DESCRIPTION", "properties");
  }

  private JFileChooser getUniversalFileChooser(ResourceBundle messages, String description, String fileEnds) {
    JFileChooser jFileChooser = new JFileChooser(lastPath);
    jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    jFileChooser.setFileFilter(new FileFilter() {
      @Override
      public boolean accept(File file) {
        return file.getName().toLowerCase().endsWith(fileEnds)
            || file.isDirectory();
      }

      @Override
      public String getDescription() {
        return messages.getString(description);
      }
    });
    return jFileChooser;
  }


}
