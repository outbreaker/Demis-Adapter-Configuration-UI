package de.gematik.demis.ui.value.editor;

import de.gematik.demis.entities.VALUE_TYPE;

public class ValueTypeEditorFactory {

  private ValueTypeEditorFactory() {
    // Factory Class
  }

  public static IValueTypeView createEditor(VALUE_TYPE type) {
    switch (type) {
      case BOOLEAN:
        return new BooleanEditor();
      case INT:
        return new IntEditor();
      case PORT:
        return new PortEditor();
      case STRING_LIST:
        return new StringListEditor();
      case RELATIVE_PATH_FILE:
        return new RelativPathEditor(false, "truststore");
      case RELATIVE_PATH_DIR:
        return new RelativPathEditor(true, "");
      case STRING:
        return new StringEditor();
      case PASSWORD:
        return new PasswordEditor();
      case URL:
        return new UrlEditor();
      case PATH_LIST:
        return new RelativPathListEditor();
      case SELECT_FIX_FHIR_BASEPATH:
        return new FhirBasePathEditor();
      default:
        return new EmptyEditor(type);
    }
  }
}
