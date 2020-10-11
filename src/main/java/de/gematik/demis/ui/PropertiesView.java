package de.gematik.demis.ui;

import de.gematik.demis.entities.ADAPTER_Properties;
import de.gematik.demis.entities.APP_Properties;
import de.gematik.demis.entities.IProperties;
import de.gematik.demis.entities.VALUE_TYPE;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import de.gematik.demis.ui.value.editor.ValueTypeEditorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Properties;

public class PropertiesView extends JPanel {
    private static Logger LOG = LoggerFactory.getLogger(PropertiesView.class.getName());


    public PropertiesView(Path path) {
        initComponents(path.toFile());
    }

    private void initComponents(File file) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(file));
        } catch (IOException e) {
            LOG.error("Could not load Properties-File \"" + file.getAbsolutePath() + "\"", e);
        }

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                PropertiesView.this.invalidate();
                PropertiesView.this.revalidate();
                PropertiesView.this.repaint();
                System.out.println("repaint");
            }

            @Override
            public void componentMoved(ComponentEvent componentEvent) {

            }

            @Override
            public void componentShown(ComponentEvent componentEvent) {

            }

            @Override
            public void componentHidden(ComponentEvent componentEvent) {

            }
        });
//        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(
//                new javax.swing.border.EmptyBorder(0, 0, 0, 0), "Properties"
//                , javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM
//                , new java.awt.Font("Dia\u006cog", java.awt.Font.BOLD, 12)
//                , java.awt.Color.red), getBorder()));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
//        ((GridBagLayout) getLayout()).columnWidths = new int[]{0, 0, 0};
//        ((GridBagLayout) getLayout()).rowHeights = new int[]{0, 0, 0, 0};
//        ((GridBagLayout) getLayout()).columnWeights = new double[]{0.0, 0.0, 1.0E-4};
//        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};

        c.gridy = 0;

        IProperties[] values;
        if (ADAPTER_Properties.containsProperties(prop)) {
            values = ADAPTER_Properties.values();
        } else if (APP_Properties.containsProperties(prop)) {
            values = APP_Properties.values();
        } else {
            return;
        }
        Arrays.stream(values).forEach(e -> {
                    c.weighty = 0.1;
                    c.fill = GridBagConstraints.BOTH;
                    c.gridx = 0;
                    c.insets = new Insets(0, 10, 0, 10);  //top padding
                    c.anchor = GridBagConstraints.LAST_LINE_START;
                    c.weightx = 0;
                    this.add(new Label(e.getKey()), c);

                    c.gridx = 1;
                    c.weightx = 1.0;
                    if (e.getType() == VALUE_TYPE.STRING_LIST) {
                        c.weighty = 0.5;
                        c.fill = GridBagConstraints.BOTH;
                    }
                    IValueTypeView editor = ValueTypeEditorFactory.createEditor(e.getType());
                    String property = prop.getProperty(e.getKey());
                    if (property == null)
                        LOG.error("File: \"" + file.getName() + "\" Property \"" + e.getKey() + "\" has no Value!");
                    else
                        editor.setValue(property);
                    this.add(editor.getViewComponent(), c);

                    c.gridy++;

                }
        );
        this.repaint();
    }

    private Component createComponent(VALUE_TYPE valueType) {
        switch (valueType) {
            case BOOLEAN:
                return new JCheckBox();
            default:
                return new JLabel(valueType.toString());
        }
    }

}
