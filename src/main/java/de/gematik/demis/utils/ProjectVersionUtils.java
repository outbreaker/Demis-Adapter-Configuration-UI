package de.gematik.demis.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectVersionUtils {
  private static Logger LOG = LoggerFactory.getLogger(ImageUtils.class.getName());

  private ProjectVersionUtils() {}

  public static String getProjectVersion() {
    String projectVersion = "";
    Properties prop = new Properties();
    InputStream input = null;

    try {
      input = new FileInputStream("build.gradle");
      prop.load(input);
      LOG.debug(prop.getProperty("version"));
      projectVersion = prop.getProperty("version");
    } catch (IOException ex) {
      LOG.debug("Could not get Version!");
    }
    return projectVersion;
  }
}
