package de.gematik.demis.ui.value.editor;

import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

public interface IValueTypeView {

  String getValue();

  void setValue(String value);

  JComponent getViewComponent();

  void addChangeListener(ChangeListener changeListener);

  boolean isExpertEditor();

  void setExpertEditor(boolean value);

  void checkExpertMode();

  void activateForExperts();
}
