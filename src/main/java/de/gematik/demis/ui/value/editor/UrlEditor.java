package de.gematik.demis.ui.value.editor;

import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class UrlEditor extends JPanel implements IValueTypeView {
    private static Logger LOG = LoggerFactory.getLogger(UrlEditor.class.getName());
    private JTextField field;

    public UrlEditor() {
        this.setLayout(new BorderLayout());
        field = new JTextField();
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {

            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (checkContent()) {
                    field.setBorder(new JTextField().getBorder());
                } else {
                    field.setBorder(new LineBorder(Color.RED));

                }
            }
        });
        this.add(field, BorderLayout.CENTER);
    }

    public boolean checkContent() {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes, new RegexValidator("^https://[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.\\w+(:\\d+)?(/[\\w-=.?]+)*/?$"), UrlValidator.ALLOW_LOCAL_URLS);
        System.out.println("URL: " + urlValidator.isValid(field.getText()));
        return urlValidator.isValid(field.getText()) && field.getText().toLowerCase().startsWith("https");
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
