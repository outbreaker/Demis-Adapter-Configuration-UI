package de.gematik.demis.control;

import de.gematik.demis.ui.actions.DemisMenuActionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigurationLoader {
    private static Logger LOG = LoggerFactory.getLogger(DemisMenuActionListener.class.getName());

    public void loadAll(File folder) {
        LOG.debug("LoadAll for " + folder.getAbsolutePath());
        try {
            listFilesUsingFileWalk(folder.getAbsolutePath(), 10)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Path> listFilesUsingFileWalk(String dir, int depth) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> !file.toFile().getAbsolutePath().toLowerCase().contains("\\jre"))
                    .filter(f -> (f.toFile().getAbsolutePath().endsWith("properties") || f.toFile().getAbsolutePath().endsWith("json")))
//                    .filter(f -> f.toFile().getAbsolutePath().endsWith("json"))
//                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }
}
