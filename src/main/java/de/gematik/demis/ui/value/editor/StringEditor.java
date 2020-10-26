package de.gematik.demis.ui.value.editor;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringEditor extends AbstractEditor {

  private static Logger LOG = LoggerFactory.getLogger(StringEditor.class.getName());
  private JTextField field;

  public StringEditor(String value) {
    this();
    setValue(value);
  }

  public StringEditor() {
    this.setLayout(new BorderLayout());
    field = new JTextField();
    this.add(field, BorderLayout.CENTER);
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
            changeListener.stateChanged(new ChangeEvent(StringEditor.this));
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
