package de.gematik.demis.utils;

import de.gematik.demis.control.ConfigurationLoader;
import java.nio.file.Path;

public class PathUtils {

  public static String getRelativPath(Path selectedFile) {
    Path pathToJar = ConfigurationLoader.getInstance().getPathToJar();
    if (pathToJar == null) {
      return selectedFile.toFile().getAbsolutePath();
    } else {
      try {
        return pathToJar.getParent().relativize(selectedFile).toString();
      } catch (Exception e) {
        return selectedFile.toFile().getAbsolutePath();
      }
    }
  }
}
