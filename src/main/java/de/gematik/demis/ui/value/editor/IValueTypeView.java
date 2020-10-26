package de.gematik.demis.ui.value.editor;

import javax.swing.JComponent;

public interface IValueTypeView {

  String getValue();

  void setValue(String value);

  JComponent getViewComponent();
}
