package de.gematik.demis.ui.value.editor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.swing.*;

@RunWith(JUnitPlatform.class)
class StringEditorTest {

    public static final String TEXT = "MeinText";
    public static final String TEXT2 = "NeuerText";

    @Test
    void createWithValue() {
        StringEditor stringEditor = new StringEditor(TEXT);
        Assert.assertNotNull(stringEditor.getValue());
        stringEditor.setValue(TEXT2);
        Assert.assertEquals(TEXT2,stringEditor.getValue());
    }

    @Test
    void getSetValue() {
        StringEditor stringEditor = new StringEditor();
        Assert.assertNotNull(stringEditor.getValue());
        stringEditor.setValue(TEXT);
        Assert.assertEquals(TEXT,stringEditor.getValue());
    }

    @Test
    void getViewComponent() {
        StringEditor stringEditor = new StringEditor();
        Assert.assertNotNull(stringEditor.getViewComponent());
        Assert.assertEquals(JTextField.class.toString(),
                stringEditor.getViewComponent().getComponent(0).getClass().toString());
        Assert.assertNotNull(((JTextField) stringEditor.getViewComponent().getComponent(0)).getText());
        stringEditor.setValue(TEXT);
        Assert.assertEquals(TEXT,((JTextField) stringEditor.getViewComponent().getComponent(0)).getText());

    }
}