package de.gematik.demis.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectVersionUtils {
  private static Logger LOG = LoggerFactory.getLogger(ProjectVersionUtils.class.getName());

  private ProjectVersionUtils() {}

  public static String getProjectVersion() {
    String projectVersion = "";

    try (InputStream input = ProjectVersionUtils.class.getClassLoader().getResourceAsStream("version.txt")){
      projectVersion =  new Scanner(input).useDelimiter("\n").next();
      LOG.debug("Version: " + projectVersion);
    } catch (IOException ex) {
      LOG.debug("Could not get Version!");
    }
    return projectVersion;
  }
}
