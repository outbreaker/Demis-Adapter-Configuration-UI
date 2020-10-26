package de.gematik.demis.ui.value.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlEditor extends AbstractEditor {

  private static Logger LOG = LoggerFactory.getLogger(UrlEditor.class.getName());
  private JTextField field;

  public UrlEditor() {
    this.setLayout(new BorderLayout());
    field = new JTextField();
    field.addFocusListener(
        new FocusListener() {
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
    UrlValidator urlValidator =
        new UrlValidator(
            schemes,
            new RegexValidator(
                "^https://[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.\\w+(:\\d+)?(/[\\w-=.?]+)*/?$"),
            UrlValidator.ALLOW_LOCAL_URLS);
    return urlValidator.isValid(field.getText())
        && field.getText().toLowerCase().startsWith("https");
  }

  @Override
  public String getValue() {
    if (field.getText() == null) {
      return "";
    } else {
      return field.getText();
    }
  }

  @Override
  public void setValue(String value) {
    field.setText(value);
  }

  @Override
  public JComponent getViewComponent() {
    return this;
  }

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    field.addKeyListener(
        new KeyAdapter() {
          @Override
          public void keyTyped(KeyEvent e) {
            changeListener.stateChanged(new ChangeEvent(UrlEditor.this));
          }
        });
  }

  @Override
  public void checkExpertMode() {
    field.setEnabled(!isExpertEditor());
  }

  @Override
  public void activateForExperts() {
    field.setEnabled(true);
  }
}
