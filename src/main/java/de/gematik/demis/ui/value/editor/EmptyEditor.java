package de.gematik.demis.ui.value.editor;

import de.gematik.demis.entities.VALUE_TYPE;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class EmptyEditor extends JLabel implements IValueTypeView {

  private VALUE_TYPE type;

  public EmptyEditor(VALUE_TYPE type) {
    this.type = type;
  }

  @Override
  public String getValue() {
    return "";
  }

  @Override
  public void setValue(String value) {
    //Nothing
  }

  @Override
  public JComponent getViewComponent() {
    return new JLabel("Editor for Value Type \"" + type + "\" not implemented!");
  }
}
