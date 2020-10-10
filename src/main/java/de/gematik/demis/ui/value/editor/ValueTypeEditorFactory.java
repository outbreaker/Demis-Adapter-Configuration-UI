package de.gematik.demis.ui.value.editor;

import de.gematik.demis.entities.VALUE_TYPE;

import javax.swing.*;

public class ValueTypeEditorFactory {

    private ValueTypeEditorFactory(){
        //Factory Class
    }

    public static IValueTypeView createEditor(VALUE_TYPE type){
        switch (type){
            case BOOLEAN: return new BooleanEditor();
            case INT: return new IntEditor();
            case STRING_LIST: return new StringListEditor();
            default: return new EmptyEditor(type);
        }
    }
}
