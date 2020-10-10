package de.gematik.demis.ui.value.editor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.swing.*;
@RunWith(JUnitPlatform.class)
class BooleanEditorTest {

    @Test
    void getSetValue() {
        BooleanEditor booleanEditor = new BooleanEditor();
        Assert.assertFalse(Boolean.parseBoolean(booleanEditor.getValue()));
        booleanEditor.setValue(Boolean.TRUE.toString());
        Assert.assertTrue(Boolean.parseBoolean(booleanEditor.getValue()));
        booleanEditor.setValue(Boolean.FALSE.toString());
        Assert.assertFalse(Boolean.parseBoolean(booleanEditor.getValue()));
    }

    @Test
    void setWrongValue() {
        BooleanEditor booleanEditor = new BooleanEditor();
        Assert.assertFalse(Boolean.parseBoolean(booleanEditor.getValue()));
        booleanEditor.setValue("WrongValue");
        Assert.assertFalse(Boolean.parseBoolean(booleanEditor.getValue()));
        booleanEditor.setValue(Boolean.TRUE.toString());
        Assert.assertTrue(Boolean.parseBoolean(booleanEditor.getValue()));
        booleanEditor.setValue(Boolean.FALSE.toString());
        Assert.assertFalse(Boolean.parseBoolean(booleanEditor.getValue()));
    }

    @Test
    void getViewComponent() {
        BooleanEditor booleanEditor = new BooleanEditor();
        Assert.assertNotNull(booleanEditor.getViewComponent());
        Assert.assertEquals(JCheckBox.class.toString(),
                booleanEditor.getViewComponent().getComponent(0).getClass().toString());
        Assert.assertFalse(((JCheckBox)booleanEditor.getViewComponent().getComponent(0)).isSelected());
        booleanEditor.setValue(Boolean.TRUE.toString());
        Assert.assertTrue(((JCheckBox)booleanEditor.getViewComponent().getComponent(0)).isSelected());
    }
}