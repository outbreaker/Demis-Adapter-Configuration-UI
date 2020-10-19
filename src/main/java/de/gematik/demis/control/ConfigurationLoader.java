package de.gematik.demis.control;

import de.gematik.demis.ui.LaboratoryView;
import de.gematik.demis.ui.MainView;
import de.gematik.demis.ui.PropertiesView;
import de.gematik.demis.ui.actions.DemisMenuActionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
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
            Set<Path> paths = listFilesUsingFileWalk(folder.getAbsolutePath(), 10);

            paths.stream().filter(f -> (f.toFile().getAbsolutePath().endsWith("properties")))
                    .forEach(f -> MainView.getInstance().addTab(f.getFileName().toString(), new JScrollPane(new PropertiesView(f))));
            paths.stream().filter(f -> (f.toFile().getAbsolutePath().endsWith("json")))
                    .forEach(f -> MainView.getInstance().addTab(f.getFileName().toString(), new JScrollPane(new LaboratoryView(f))));
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
