package de.gematik.demis.ui;

import java.awt.Component;
import java.nio.file.Path;
import javax.swing.event.ChangeListener;

public interface IConfigurationView {

  String getName();

  Component getComponent();

  void addChangeListener(ChangeListener changeListener);

  boolean hasUnsavedChanges();

  void setSaved();

  Path getPath();
}
