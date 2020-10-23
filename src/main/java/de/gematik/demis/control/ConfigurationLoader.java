package de.gematik.demis.control;

import de.gematik.demis.ui.LaboratoryView;
import de.gematik.demis.ui.MainView;
import de.gematik.demis.ui.PropertiesView;
import de.gematik.demis.ui.actions.DemisMenuActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JScrollPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationLoader {

  private static Logger LOG = LoggerFactory.getLogger(DemisMenuActionListener.class.getName());
  private static ConfigurationLoader instance;
  private final List<PropertiesView> propertiesViews = new ArrayList<>();
  private final List<LaboratoryView> laboratoryViews = new ArrayList<>();

  private ConfigurationLoader() {
  }

  public static ConfigurationLoader getInstance() {
    if (instance == null) {
      instance = new ConfigurationLoader();
    }
    return instance;
  }

  public PropertiesView add(PropertiesView propertiesView) {
    propertiesViews.add(propertiesView);
    return propertiesView;
  }

  public LaboratoryView add(LaboratoryView laboratoryView) {
    laboratoryViews.add(laboratoryView);
    return laboratoryView;
  }

  public void loadAll(File folder) {
    LOG.debug("LoadAll for " + folder.getAbsolutePath());
    try {
      Set<Path> paths = listFilesUsingFileWalk(folder.getAbsolutePath(), 10);

      paths.stream().filter(f -> (f.toFile().getAbsolutePath().endsWith("properties")))
          .forEach(f -> MainView.getInstance()
              .addTab(f.getFileName().toString(), new JScrollPane(add(new PropertiesView(f)))));
      paths.stream().filter(f -> (f.toFile().getAbsolutePath().endsWith("json")))
          .forEach(f -> MainView.getInstance()
              .addTab(f.getFileName().toString(), new JScrollPane(add(new LaboratoryView(f)))));
    } catch (IOException e) {
      String failed = "Failed to read all Files";
      LOG.error(failed, e);
      throw new RuntimeException(failed, e);
    }
  }

  public Set<Path> listFilesUsingFileWalk(String dir, int depth) throws IOException {
    try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
      return stream
          .filter(file -> !Files.isDirectory(file))
          .filter(file -> !file.toFile().getAbsolutePath().toLowerCase().contains("\\jre"))
          .filter(f -> (f.toFile().getAbsolutePath().endsWith("properties") || f.toFile()
              .getAbsolutePath().endsWith("json")))
//                    .filter(f -> f.toFile().getAbsolutePath().endsWith("json"))
//                    .map(Path::toString)
          .collect(Collectors.toSet());
    }
  }

  public List<PropertiesView> getPropertiesViews() {
    return propertiesViews;
  }

  public List<LaboratoryView> getLaboratoryViews() {
    return laboratoryViews;
  }
}
