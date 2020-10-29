package de.gematik.demis.control;

import de.gematik.demis.entities.IProperties;
import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.ui.AbstractConfigurationView;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationLoader {

  private static final Logger LOG =
      LoggerFactory.getLogger(DemisMenuActionListener.class.getName());
  private static ConfigurationLoader instance;
  private List<PropertiesView> propertiesViews = new ArrayList<>();
  private List<LaboratoryView> laboratoryViews = new ArrayList<>();
  private Path pathToJar;
  private Path pathToMainFolder;

  private ConfigurationLoader() {}

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

      paths.stream()
          .filter(f -> (f.toFile().getAbsolutePath().endsWith("properties")))
          .forEach(f -> MainView.getInstance().addTab(add(new PropertiesView(f))));
      paths.stream()
          .filter(f -> (f.toFile().getAbsolutePath().endsWith("json")))
          .forEach(f -> MainView.getInstance().addCloseTab(add(new LaboratoryView(f))));
      Optional<Path> first =
          paths.stream()
              .filter(
                  f ->
                      (f.toFile().getAbsolutePath().endsWith("jar")
                          && f.toFile().getAbsolutePath().toLowerCase().contains("client")))
              .findFirst();
      first.ifPresent(path -> pathToJar = path);
      pathToMainFolder = pathToJar.toFile().getParentFile().getParentFile().toPath();
      if (!laboratoryViews.isEmpty() || !propertiesViews.isEmpty()) {
        MainView.getInstance().setConfigurationControl(true);
      }
    } catch (IOException e) {
      String failed = "Failed to read all Files";
      LOG.error(failed, e);
      throw new RuntimeException(failed, e);
    }
  }

  public boolean checkPath(String dir) {
    boolean config = false;
    boolean client = false;
    boolean data = false;

    try (Stream<Path> stream1 = Files.walk(Paths.get(dir), 1);
        Stream<Path> stream2 = Files.walk(Paths.get(dir), 1);
        Stream<Path> stream3 = Files.walk(Paths.get(dir), 1)) {
      config =
          stream1
              .filter(file -> Files.isDirectory(file))
              .anyMatch(f -> f.toFile().getAbsolutePath().toLowerCase().endsWith("config"));
      client =
          stream2
              .filter(file -> Files.isDirectory(file))
              .anyMatch(f -> f.toFile().getAbsolutePath().toLowerCase().endsWith("client"));
      data =
          stream3
              .filter(file -> Files.isDirectory(file))
              .anyMatch(f -> f.toFile().getAbsolutePath().toLowerCase().endsWith("data"));
    } catch (Exception e) {
      LOG.error("Failed to check folder", e);
    }
    return config && client && data;
  }

  public Set<Path> listFilesUsingFileWalk(String dir, int depth) throws IOException {
    try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
      return stream
          .filter(file -> !Files.isDirectory(file))
          .filter(file -> !file.toFile().getAbsolutePath().toLowerCase().contains("\\jre"))
          .filter(
              f ->
                  (f.toFile().getAbsolutePath().endsWith("properties")
                      || f.toFile().getAbsolutePath().endsWith("json")
                      || f.toFile().getAbsolutePath().endsWith("jar")))
          .collect(Collectors.toSet());
    }
  }

  public List<PropertiesView> getPropertiesViews() {
    return propertiesViews;
  }

  public List<LaboratoryView> getLaboratoryViews() {
    return laboratoryViews;
  }

  public void setPropertiesValue(IProperties property, String value) {
    propertiesViews.forEach(prop -> prop.setPropertiesValue(property, value));
  }

  public void setJsonValue(LABORATORY_JSON property, String value) {
    laboratoryViews.forEach(lab -> lab.setJsonValue(property, value));
  }

  public Optional<LaboratoryView> showFirstViewWith(LABORATORY_JSON property) {
    Optional<LaboratoryView> first =
        laboratoryViews.stream().filter(lab -> lab.contains(property)).findFirst();
    first.ifPresent(laboratoryView -> MainView.getInstance().showTabFor(laboratoryView));
    return first;
  }

  public boolean hasUnsavedChanges() {
    if (propertiesViews.stream().anyMatch(AbstractConfigurationView::hasUnsavedChanges)) {
      return true;
    } else return laboratoryViews.stream().anyMatch(AbstractConfigurationView::hasUnsavedChanges);
  }

  public void closeAllViews() {
    propertiesViews = new ArrayList<>();
    laboratoryViews = new ArrayList<>();
    MainView.getInstance().getJTabs().removeAll();
    MainView.getInstance().setConfigurationControl(false);
  }

  public boolean hasConfiguration() {
    return !propertiesViews.isEmpty() || !laboratoryViews.isEmpty();
  }

  public void addNewLaboratoryConfiguration() {
    MainView.getInstance().addCloseTab(add(new LaboratoryView()));
  }

  public Path getPathToJar() {
    return pathToJar;
  }

  public Path getPathToMainFolder() {
    return pathToMainFolder;
  }
}
