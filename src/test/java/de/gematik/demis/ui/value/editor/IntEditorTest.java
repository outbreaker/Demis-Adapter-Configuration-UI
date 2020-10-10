package de.gematik.demis.ui.value.editor;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.swing.*;

@RunWith(JUnitPlatform.class)
class IntEditorTest {
    @Test
    void getSetValue() {
        IntEditor intEditor = new IntEditor();
        Assert.assertEquals("", intEditor.getValue());
        intEditor.setValue("125");
        Assert.assertEquals("125", intEditor.getValue());
        intEditor.setValue("85");
        Assert.assertEquals("85", intEditor.getValue());
    }

    @Test
    void setWrongValue() {
        IntEditor intEditor = new IntEditor();
        Assertions.assertThrows(NumberFormatException.class, () -> {
            intEditor.setValue("abc");
        });
    }

    @Test
    void getViewComponent() {
        IntEditor intEditor = new IntEditor();
        Assert.assertNotNull(intEditor.getViewComponent());
        Assert.assertEquals(JFormattedTextField.class.toString(),
                intEditor.getViewComponent().getComponent(0).getClass().toString());
        Assert.assertNull(((JFormattedTextField) intEditor.getViewComponent().getComponent(0)).getValue());
        intEditor.setValue("32");
        Assert.assertEquals("32",((JFormattedTextField) intEditor.getViewComponent().getComponent(0)).getValue().toString());

    }
}