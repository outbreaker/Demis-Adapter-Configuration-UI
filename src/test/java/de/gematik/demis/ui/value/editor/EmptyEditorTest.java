package de.gematik.demis.ui.value.editor;

import de.gematik.demis.entities.VALUE_TYPE;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.swing.*;

@RunWith(JUnitPlatform.class)
class EmptyEditorTest {

    @Test
    void getSetValue() {
        EmptyEditor emptyEditor = new EmptyEditor(VALUE_TYPE.INT);
        emptyEditor.setValue("Value");
        Assert.assertEquals("", emptyEditor.getValue());
    }

    @Test
    void getViewComponent() {
        EmptyEditor emptyEditor = new EmptyEditor(VALUE_TYPE.INT);
        Assert.assertNotNull(emptyEditor.getViewComponent());
        Assert.assertEquals(JLabel.class.toString(), emptyEditor.getViewComponent().getClass().toString());
    }
}