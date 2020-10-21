package de.gematik.demis.ui.actions;

import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.ui.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DemisMenuActionListener implements ActionListener {
    private static Logger LOG = LoggerFactory.getLogger(DemisMenuActionListener.class.getName());

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "OPEN_ALL":
                JFileChooser jFileChooser = new JFileChooser("C:\\Demis\\Demis-Adapter-1.1.0 -- Docker LAB-01.01.01.100");
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int opt = jFileChooser.showOpenDialog(MainView.getInstance().getMainComponent());
                if (opt == JFileChooser.APPROVE_OPTION) {
                    File folderToLoad = jFileChooser.getSelectedFile();
                    new ConfigurationLoader().loadAll(folderToLoad);
                    LOG.debug("Selected Folder to Load all Configurations: " + folderToLoad);
                }
                break;
            case "OPEN":
                break;
            case "SAVE_ALL":
                break;
            default:
                LOG.warn("Action for Command \"" + actionEvent.getActionCommand() + "\" not implemented");
        }
    }


}
