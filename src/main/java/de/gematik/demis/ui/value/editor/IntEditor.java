package de.gematik.demis.ui.value.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class IntEditor extends JPanel implements IValueTypeView {
    private static Logger LOG = LoggerFactory.getLogger(IntEditor.class.getName());
    private JFormattedTextField field;

    public IntEditor() {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("#####");
        } catch (ParseException e) {
            LOG.error("MaskFormatter initializing with error", e);
        }
        field = new JFormattedTextField(formatter);
        this.add(field, BorderLayout.CENTER);
    }

    @Override
    public String getValue() {
        if (field.getValue() == null)
            return "";
        else
            return String.valueOf(field.getValue());
    }

    @Override
    public void setValue(String value) {
        field.setValue(Integer.valueOf(value));
    }

    @Override
    public JComponent getViewComponent() {
        return this;
    }
}
