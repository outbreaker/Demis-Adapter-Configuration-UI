package de.gematik.demis.ui.value.editor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.swing.*;
import java.awt.*;

@RunWith(JUnitPlatform.class)
class RelativPathListEditorTest {

    public static final String DUMMY_LIST = "../config/labor1.json,../config/labor2.json,../config/labor3.json,../config/labor4.json";
    public static final String DUMMY_LIST_2 = "../config/labor1.json,../config/labor3.json,../config/labor4.json";

    @Test
    void getSetValue() {
        RelativPathListEditor relativPathListEditor = new RelativPathListEditor();
        Assert.assertEquals("", relativPathListEditor.getValue());
        relativPathListEditor.setValue(DUMMY_LIST);
        Assert.assertEquals(DUMMY_LIST, relativPathListEditor.getValue());
        Component component = relativPathListEditor.getViewComponent().getComponent(0);
        Assert.assertEquals(JScrollPane.class.toString(), component.getClass().toString());
        Component jListComp = ((JScrollPane) component).getViewport().getComponent(0);
        Assert.assertEquals(JList.class.toString(), jListComp.getClass().toString());
        ListModel model = ((JList) jListComp).getModel();
        Assert.assertEquals(DefaultListModel.class.toString(), model.getClass().toString());
        Object[] values = ((DefaultListModel) model).toArray();
        Assert.assertArrayEquals(DUMMY_LIST.split(","),values);

        ((DefaultListModel) model).removeElementAt(1);
        values = ((DefaultListModel) model).toArray();
        Assert.assertArrayEquals(DUMMY_LIST_2.split(","),values);
        Assert.assertEquals(DUMMY_LIST_2, relativPathListEditor.getValue());

    }

    @Test
    void getViewComponent() {
        RelativPathListEditor relativPathListEditor = new RelativPathListEditor();
        Assert.assertNotNull(relativPathListEditor.getViewComponent());
    }
}