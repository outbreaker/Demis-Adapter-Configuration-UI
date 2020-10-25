package de.gematik.demis.ui.value.editor;

import de.gematik.demis.utils.ImageUtils;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    add(button, BorderLayout.EAST);
  }

  public PasswordEditor(String authcertpassword) {
    this();
    setValue(authcertpassword);
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

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    pwField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent keyEvent) {
        changeListener.stateChanged(new ChangeEvent(PasswordEditor.this));
      }
    });
  }

  class myChangeL implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent e) {
      if (((JButton) e.getSource()).getModel().isPressed()) {
        try {
          clearPwField.setText(pwField.getDocument().getText(0, pwField.getDocument().getLength()));
          remove(pwField);
          add(clearPwField, BorderLayout.CENTER);
          revalidate();
        } catch (BadLocationException ex) {
          LOG.error("Can't show Password clear!", e);
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
