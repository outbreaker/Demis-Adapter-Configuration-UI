package de.gematik.demis.entities;

public interface IProperties {

  String getKey();

  VALUE_TYPE getType();

  boolean isOptional();

  String getDisplayName();

  String getToolTip();

  String getDefaultValue();
}
