package de.gematik.demis.utils;

import de.gematik.demis.control.ConfigurationLoader;
import java.nio.file.Path;

public class PathUtils {

  public static String getRelativPath(Path selectedFile) {
    Path pathToJar = ConfigurationLoader.getInstance().getPathToJar();
    if (pathToJar == null) {
      return selectedFile.toFile().getAbsolutePath();
    } else {
      return pathToJar.getParent().relativize(selectedFile).toString();
    }
  }

  //  private String getRelativPath(final String xincludeAbsoluteLocation, final File
  // mainDocumentDestination) {
  //    Path pathAbsolute = Paths.get(xincludeAbsoluteLocation);
  //    Path pathBase = Paths.get(mainDocumentDestination.getAbsoluteFile().getParent());
  //    Path pathRelative;
  //    try {
  //      pathRelative = pathBase.relativize(pathAbsolute);
  //    } catch (IllegalArgumentException exception) {
  //      LOGGER.debug("Die beiden Pfade xinclude: \"" + xincludeAbsoluteLocation + "\" und der Pfad
  // zum Haupdokument: \""
  //          + mainDocumentDestination.getAbsolutePath() + "\" liegen auf unterschiedlichen
  // Laufwerken und es kann kein relativer Pfad bestimmt werden. "
  //          + "Es wird mit dem absoluten Pfad gearbeitet!");
  //      return xincludeAbsoluteLocation;
  //    }
  //    return pathRelative.toString();
  //  }
}
