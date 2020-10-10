package de.gematik.demis.ui.value.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class BooleanEditor extends JPanel implements IValueTypeView {
    private static Logger LOG = LoggerFactory.getLogger(BooleanEditor.class.getName());
    private final JCheckBox booleanCheckBox = new JCheckBox();

    public BooleanEditor(){
        this.setLayout(new BorderLayout());
        add(booleanCheckBox,BorderLayout.CENTER);
    }

    @Override
    public String getValue() {
        return String.valueOf(booleanCheckBox.isSelected()).toLowerCase();
    }

    @Override
    public void setValue(String value) {
        try {
            booleanCheckBox.setSelected(Boolean.parseBoolean(value));
        } catch (Exception e) {
            LOG.error("Value \"" + value + "\" could not interpreted as boolean value.");
            booleanCheckBox.setSelected(false);
        }
    }

    @Override
    public JComponent getViewComponent() {
        return this;
    }
}
