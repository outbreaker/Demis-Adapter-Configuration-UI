package de.gematik.demis.ui.value.editor;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringEditor extends JPanel implements IValueTypeView {

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
}
