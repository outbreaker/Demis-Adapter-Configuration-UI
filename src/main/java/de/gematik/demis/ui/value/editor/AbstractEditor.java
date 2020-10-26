package de.gematik.demis.ui.value.editor;

import javax.swing.JPanel;

public abstract class AbstractEditor extends JPanel implements IValueTypeView {

  private boolean expert;

  @Override
  public boolean isExpertEditor() {
    return expert;
  }

  @Override
  public void setExpertEditor(boolean value) {
    this.expert = value;
  }
}
