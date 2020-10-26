package de.gematik.demis.ui.value.editor;

import java.awt.BorderLayout;
import java.text.ParseException;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntEditor extends JPanel implements IValueTypeView {

  private static Logger LOG = LoggerFactory.getLogger(IntEditor.class.getName());
  private JFormattedTextField field;

  public IntEditor() {
    this.setLayout(new BorderLayout());
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
    if (field.getValue() == null) {
      return "";
    } else {
      return String.valueOf(field.getValue());
    }
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
