package de.gematik.demis.ui.value.editor;

import de.gematik.demis.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class PasswordEditor extends JPanel implements IValueTypeView {
    private static Logger LOG = LoggerFactory.getLogger(PasswordEditor.class.getName());

    private JPasswordField pwField;
    private JTextField clearPwField;

    public PasswordEditor() {
        this.setLayout(new BorderLayout());
        pwField = new JPasswordField();
        clearPwField = new JTextField();
        clearPwField.setEditable(false);
        add(pwField, BorderLayout.CENTER);
        JButton button = new JButton(ImageUtils.loadResizeImage("eye-icon", 15));
        button.addChangeListener(new myChangeL());
//        button.addChangeListener(e -> {
//            if (((JButton) e.getSource()).getModel().isPressed()) {
//                System.out.println(e);
//                try {
//                    clearPwField.setText(pwField.getDocument().getText(0, pwField.getDocument().getLength()));
//                    remove(pwField);
//                    add(clearPwField, BorderLayout.CENTER);
//                    revalidate();
//                } catch (BadLocationException ex) {
//                    LOG.error("Can't show Password clear!" , e);
//                }
//            } else {
//                remove(clearPwField);
//                add(pwField, BorderLayout.CENTER);
//                revalidate();
//            }
//            repaint();
//        });
        add(button, BorderLayout.EAST);
    }

    @Override
    public String getValue() {
        try {
            return pwField.getDocument().getText(0, pwField.getDocument().getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException("Can't extract Password " + e.getLocalizedMessage());
        }
    }

    @Override
    public void setValue(String value) {
        pwField.setText(value);
    }

    @Override
    public JComponent getViewComponent() {
        return this;
    }

    class myChangeL implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            if (((JButton) e.getSource()).getModel().isPressed()) {
                try {
                    clearPwField.setText(pwField.getDocument().getText(0, pwField.getDocument().getLength()));
                    remove(pwField);
                    add(clearPwField, BorderLayout.CENTER);
                    revalidate();
                } catch (BadLocationException ex) {
                    LOG.error("Can't show Password clear!" , e);
                }
            } else {
                remove(clearPwField);
                add(pwField, BorderLayout.CENTER);
                revalidate();
            }
            repaint();
        }
    }
}
