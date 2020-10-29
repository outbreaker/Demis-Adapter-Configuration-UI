package de.gematik.demis.ui.value.editor;

import de.gematik.demis.entities.VALUE_TYPE;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class ValueTypeEditorFactoryTest {

    @Test
    void createEditor() {
        Assert.assertEquals(BooleanEditor.class, ValueTypeEditorFactory.createEditor(VALUE_TYPE.BOOLEAN, e.getFilter()).getClass());
        Assert.assertEquals(IntEditor.class, ValueTypeEditorFactory.createEditor(VALUE_TYPE.INT, e.getFilter()).getClass());
        Assert.assertEquals(StringListEditor.class, ValueTypeEditorFactory.createEditor(VALUE_TYPE.STRING_LIST, e.getFilter()).getClass());
        Assert.assertEquals(RelativPathEditor.class, ValueTypeEditorFactory.createEditor(VALUE_TYPE.RELATIVE_PATH, e.getFilter()).getClass());
        Assert.assertEquals(StringEditor.class, ValueTypeEditorFactory.createEditor(VALUE_TYPE.STRING, e.getFilter()).getClass());
        Assert.assertEquals(RelativPathListEditor.class, ValueTypeEditorFactory.createEditor(VALUE_TYPE.PATH_LIST, e.getFilter()).getClass());
        Assert.assertEquals(RelativPathEditor.class, ValueTypeEditorFactory.createEditor(VALUE_TYPE.PATH, e.getFilter()).getClass());
        Assert.assertEquals(UrlEditor.class, ValueTypeEditorFactory.createEditor(VALUE_TYPE.URL, e.getFilter()).getClass());
        Assert.assertEquals(PasswordEditor.class, ValueTypeEditorFactory.createEditor(VALUE_TYPE.PASSWORD, e.getFilter()).getClass());
        Assert.assertEquals(RelativPathListEditor.class, ValueTypeEditorFactory.createEditor(VALUE_TYPE.PATH_LIST, e.getFilter()).getClass());
    }
}