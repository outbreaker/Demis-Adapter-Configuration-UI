package de.gematik.demis.ui;

import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.entities.ADAPTER_Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class PropertiesViewTest {

  @Test
  void loadProperties() throws IOException {
    URL resource = getClass().getClassLoader().getResource("Demis-Adapter");
    String absolutePath = new File(resource.getFile()).getAbsolutePath();
    Set<Path> paths = ConfigurationLoader.getInstance().listFilesUsingFileWalk(absolutePath, 10);
    Assert.assertEquals(
        2,
        paths.stream()
            .filter(f -> f.toFile().getAbsolutePath().endsWith("properties"))
            .collect(Collectors.toSet())
            .size());
    paths.stream()
        .filter(f -> f.toFile().getAbsolutePath().endsWith("properties"))
        .forEach(PropertiesView::new);
  }

  @Test
  void loadProperties_1_6_3() throws IOException {
    URL resource = getClass().getClassLoader().getResource("Demis-Adapter-1.6.3");
    String absolutePath = new File(resource.getFile()).getAbsolutePath();
    Set<Path> paths = ConfigurationLoader.getInstance().listFilesUsingFileWalk(absolutePath, 10);
    Assert.assertEquals(
        2,
        paths.stream()
            .filter(f -> f.toFile().getAbsolutePath().endsWith("properties"))
            .collect(Collectors.toSet())
            .size());
    paths.stream()
        .filter(f -> f.toFile().getAbsolutePath().endsWith("properties"))
        .forEach(PropertiesView::new);
  }

  @Test
  void checkConvertProxyTo_1_6_3() throws IOException {
    URL resource = getClass().getClassLoader().getResource("Demis-Adapter");
    String oldPropertiesFile =
        new File(resource.getFile()).getAbsolutePath()
            + File.separator
            + "client"
            + File.separator
            + "demis-adapter-api.properties";
    String destination =
        new File(resource.getFile()).getParentFile().getAbsolutePath()
            + File.separator
            + "Demis-Adapter-Test"
            + new Date().getTime();
    File tmpDir = new File(destination);
    tmpDir.mkdir();
    String destinationFile = destination + File.separator + "demis-adapter-api.properties";
    Files.copy(Path.of(oldPropertiesFile), Path.of(destinationFile));
    Properties propertiesConverted = new PropertiesView(Path.of(destinationFile)).getProperties();
    Properties propertiesOld = new Properties();
    try (FileInputStream inStream = new FileInputStream(new File(oldPropertiesFile))) {
      propertiesOld.load(inStream);
    }
    Assert.assertTrue(propertiesOld.containsKey(ADAPTER_Properties.IDP_LAB_PROXY.getKey()));
    Assert.assertFalse(propertiesConverted.containsKey(ADAPTER_Properties.IDP_LAB_PROXY.getKey()));
    Assert.assertTrue(
        propertiesConverted.containsKey(ADAPTER_Properties.IDP_LAB_PROXY_PORT.getKey()));
    Assert.assertTrue(
        propertiesConverted.containsKey(ADAPTER_Properties.IDP_LAB_PROXY_HOST.getKey()));
    Assert.assertTrue(
        propertiesConverted.containsKey(ADAPTER_Properties.IDP_LAB_PROXY_PASSWORD.getKey()));
    Assert.assertTrue(
        propertiesConverted.containsKey(ADAPTER_Properties.IDP_LAB_PROXY_USERNAME.getKey()));
    Assert.assertEquals(
        "100.168.100.10",
        propertiesConverted.getProperty(ADAPTER_Properties.IDP_LAB_PROXY_HOST.getKey()));
    Assert.assertEquals(
        "1234", propertiesConverted.getProperty(ADAPTER_Properties.IDP_LAB_PROXY_PORT.getKey()));
    tmpDir.deleteOnExit();

    for (File file : tmpDir.listFiles()) {
      file.delete();
    }
    tmpDir.delete();
  }
}
