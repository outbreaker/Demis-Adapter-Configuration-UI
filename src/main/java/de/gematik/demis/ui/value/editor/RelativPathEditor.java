package de.gematik.demis.ui.value.editor;

import de.gematik.demis.ui.MainView;
import de.gematik.demis.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RelativPathEditor extends JPanel implements IValueTypeView {

    private JTextField relativPath;
    private static String lastPath;

    public RelativPathEditor() {
        this.setLayout(new BorderLayout());
        relativPath = new JTextField();
        relativPath.setEditable(false);
        add(relativPath, BorderLayout.CENTER);
        JButton dialogJb = new JButton(ImageUtils.loadResizeImage("Folder-Open-icon",15));
        dialogJb.addActionListener(actionEvent -> selectFolder());
        add(dialogJb,BorderLayout.EAST);
    }

    private void selectFolder(){
        JFileChooser jFileChooser = new JFileChooser(lastPath);
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int opt = jFileChooser.showOpenDialog(MainView.getInstance().getMainComponent());
        if (opt == JFileChooser.APPROVE_OPTION) {
            File folderToLoad = jFileChooser.getSelectedFile();
            lastPath = folderToLoad.getAbsolutePath();
            relativPath.setText(lastPath);
        }
    }

    @Override
    public String getValue() {
        return relativPath.getText();
    }

    @Override
    public void setValue(String value) {
        relativPath.setText(value);
    }

    @Override
    public JComponent getViewComponent() {
        return this;
    }
}
