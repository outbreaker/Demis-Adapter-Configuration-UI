package de.gematik.demis.ui.value.editor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.swing.*;
import java.awt.*;

@RunWith(JUnitPlatform.class)
class StringListEditorTest {

    public static final String DUMMY_LIST = "a,b,cc,fd,gg,hh,ttt";
    public static final String[] DUMMY_ARRAY = {"a", "b", "cc", "fd", "gg", "hh", "ttt"};
    public static final String DUMMY_LIST_2 = "a,cc,fd,gg,hh,ttt";

    @Test
    void createWithValue() {
        StringListEditor stringListEditor = new StringListEditor(DUMMY_ARRAY);
        Assert.assertEquals(DUMMY_LIST, stringListEditor.getValue());
        stringListEditor.setValue(DUMMY_LIST_2);
        Assert.assertEquals(DUMMY_LIST_2, stringListEditor.getValue());
    }

    @Test
    void getSetValue() {
        StringListEditor stringListEditor = new StringListEditor();
        Assert.assertEquals("", stringListEditor.getValue());
        stringListEditor.setValue(DUMMY_LIST);
        Assert.assertEquals(DUMMY_LIST, stringListEditor.getValue());
        Component component = stringListEditor.getViewComponent().getComponent(0);
        Assert.assertEquals(JScrollPane.class.toString(), component.getClass().toString());
        Component jListComp = ((JScrollPane) component).getViewport().getComponent(0);
        Assert.assertEquals(JList.class.toString(), jListComp.getClass().toString());
        ListModel model = ((JList) jListComp).getModel();
        Assert.assertEquals(DefaultListModel.class.toString(), model.getClass().toString());
        Object[] values = ((DefaultListModel) model).toArray();
        Assert.assertArrayEquals(DUMMY_LIST.split(","), values);

        ((DefaultListModel) model).removeElementAt(1);
        values = ((DefaultListModel) model).toArray();
        Assert.assertArrayEquals(DUMMY_LIST_2.split(","), values);
        Assert.assertEquals(DUMMY_LIST_2, stringListEditor.getValue());

    }

    @Test
    void getViewComponent() {
        StringListEditor stringListEditor = new StringListEditor();
        Assert.assertNotNull(stringListEditor.getViewComponent());
    }
}