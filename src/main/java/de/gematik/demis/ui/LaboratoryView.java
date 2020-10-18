package de.gematik.demis.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gematik.demis.entities.*;
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

public class LaboratoryView extends JPanel {
    private static Logger LOG = LoggerFactory.getLogger(LaboratoryView.class.getName());


    public LaboratoryView(Path path) {
        initComponents(path.toFile());
    }

    private void initComponents(File file) {
        LOG.debug("Laboratory File to load: " + file.getAbsolutePath());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readValue(file, Laboratory.class);
        } catch (IOException e) {
            LOG.error("Failed to load JSON",e);
            throw new RuntimeException(e.getMessage());
        }


//        Properties prop = new Properties();
//        try {
//            prop.load(new FileInputStream(file));
//        } catch (IOException e) {
//            LOG.error("Could not load Properties-File \"" + file.getAbsolutePath() + "\"", e);
//        }
//
//        this.addComponentListener(new ComponentListener() {
//            @Override
//            public void componentResized(ComponentEvent componentEvent) {
//                LaboratoryView.this.invalidate();
//                LaboratoryView.this.revalidate();
//                LaboratoryView.this.repaint();
//                System.out.println("repaint");
//            }
//
//            @Override
//            public void componentMoved(ComponentEvent componentEvent) {
//
//            }
//
//            @Override
//            public void componentShown(ComponentEvent componentEvent) {
//
//            }
//
//            @Override
//            public void componentHidden(ComponentEvent componentEvent) {
//
//            }
//        });
//        setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//
//        c.gridy = 0;
//
//        IProperties[] values;
//        if (ADAPTER_Properties.containsProperties(prop)) {
//            values = ADAPTER_Properties.values();
//        } else if (APP_Properties.containsProperties(prop)) {
//            values = APP_Properties.values();
//        } else {
//            return;
//        }
//        Arrays.stream(values).forEach(e -> {
//                    c.weighty = 0.1;
//                    c.fill = GridBagConstraints.BOTH;
//                    c.gridx = 0;
//                    c.insets = new Insets(0, 10, 0, 10);  //top padding
//                    c.anchor = GridBagConstraints.LAST_LINE_START;
//                    c.weightx = 0;
//                    this.add(new Label(e.getKey()), c);
//
//                    c.gridx = 1;
//                    c.weightx = 1.0;
//                    if (e.getType() == VALUE_TYPE.STRING_LIST) {
//                        c.weighty = 0.5;
//                        c.fill = GridBagConstraints.BOTH;
//                    }
//                    IValueTypeView editor = ValueTypeEditorFactory.createEditor(e.getType());
//                    String property = prop.getProperty(e.getKey());
//                    if (property == null)
//                        LOG.error("File: \"" + file.getName() + "\" Property \"" + e.getKey() + "\" has no Value!");
//                    else
//                        editor.setValue(property);
//                    this.add(editor.getViewComponent(), c);
//
//                    c.gridy++;
//
//                }
//        );
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
