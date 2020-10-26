package de.gematik.demis.entities;

public interface IJson {

  String getKey();

  boolean isOptional();

  String getDisplayName();

  String getToolTip();

  String getDefaultValue();
}
