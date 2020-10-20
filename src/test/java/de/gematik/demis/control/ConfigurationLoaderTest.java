package de.gematik.demis.control;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(JUnitPlatform.class)
@SelectPackages("de.gematik.demis.control")
class ConfigurationLoaderTest {
    @Test
    public void test(){

    }

    //@Test
    public void loadAll() {
//        new ConfigurationLoader().loadAll(new File("C:\\Demis\\Demis-Adapter-1.1.0 -- Docker LAB-01.01.01.100"));
    }

    @Test
    void listFilesUsingFileWalk() throws IOException {
        URL resource = getClass().getClassLoader().getResource("Demis-Adapter");
        Assert.assertNotNull(resource);
        String absolutePath = new File(resource.getFile()).getAbsolutePath();
        Set<Path> paths = new ConfigurationLoader().listFilesUsingFileWalk(absolutePath, 10);
        Assert.assertEquals(5, paths.size());
        Assert.assertEquals(2, paths.stream().filter(f -> f.toFile().getAbsolutePath().endsWith("properties")).collect(Collectors.toSet()).size());
        Assert.assertEquals(3, paths.stream().filter(f -> f.toFile().getAbsolutePath().endsWith("json")).collect(Collectors.toSet()).size());
    }
}
