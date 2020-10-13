package de.gematik.demis.ui.value.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class StringEditor extends JPanel implements IValueTypeView {
    private static Logger LOG = LoggerFactory.getLogger(StringEditor.class.getName());
    private JTextField field;

    public StringEditor() {
        this.setLayout(new BorderLayout());
        field = new JTextField();
        this.add(field, BorderLayout.CENTER);
    }

    @Override
    public String getValue() {
        if (field.getText() == null)
            return "";
        else
            return field.getText();
    }

    @Override
    public void setValue(String value) {
        field.setText(value);
    }

    @Override
    public JComponent getViewComponent() {
        return this;
    }
}
