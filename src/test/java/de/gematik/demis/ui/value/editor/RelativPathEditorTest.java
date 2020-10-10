package de.gematik.demis.ui.value.editor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.swing.*;
import java.io.File;

@RunWith(JUnitPlatform.class)
class RelativPathEditorTest {

    @Test
    void getSetValue() {
        RelativPathEditor relativPathEditor = new RelativPathEditor();
        Assert.assertNotNull(relativPathEditor.getValue());
        Assert.assertEquals("", relativPathEditor.getValue());
        String path = new File("test.file").getAbsolutePath();
        relativPathEditor.setValue(path);
        Assert.assertEquals(path, relativPathEditor.getValue());
    }

    @Test
    void getViewComponent() {
        RelativPathEditor relativPathEditor = new RelativPathEditor();
        Assert.assertNotNull(relativPathEditor.getViewComponent());
        Assert.assertEquals(JTextField.class.toString(), relativPathEditor.getViewComponent().getComponent(0).getClass().toString());
        Assert.assertEquals("", ((JTextField) relativPathEditor.getViewComponent().getComponent(0)).getText());
        String path = new File("test.file").getAbsolutePath();
        relativPathEditor.setValue(path);
        Assert.assertEquals(path, ((JTextField) relativPathEditor.getViewComponent().getComponent(0)).getText());
    }
}