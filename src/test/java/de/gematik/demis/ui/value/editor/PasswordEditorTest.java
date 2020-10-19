package de.gematik.demis.ui.value.editor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class PasswordEditorTest {

    public static final String PW = "MeinPa12!%&/ssword";
    public static final String PW_2 = "fafs2!%&/ssword";

    @Test
    void createWithValue() {
        PasswordEditor passwordEditor = new PasswordEditor(PW);
        Assert.assertEquals(PW, passwordEditor.getValue());
        passwordEditor.setValue(PW_2);
        Assert.assertEquals(PW_2, passwordEditor.getValue());
    }

    @Test
    void getSetValue() {
        PasswordEditor passwordEditor = new PasswordEditor();
        Assert.assertNotNull(passwordEditor.getValue());
        passwordEditor.setValue(PW);
        Assert.assertEquals(PW, passwordEditor.getValue());
    }

    @Test
    void getViewComponent() {
        PasswordEditor passwordEditor = new PasswordEditor();
        Assert.assertNotNull(passwordEditor.getViewComponent());
        Assert.assertEquals(JPasswordField.class.toString(),
                passwordEditor.getViewComponent().getComponent(0).getClass().toString());
        Assert.assertEquals(JButton.class.toString(),
                passwordEditor.getViewComponent().getComponent(1).getClass().toString());

        Assert.assertNotNull(((JPasswordField) passwordEditor.getViewComponent().getComponent(0)).getText());
        passwordEditor.setValue(PW);
        Assert.assertEquals(PW, ((JPasswordField) passwordEditor.getViewComponent().getComponent(0)).getText());


        JButton jButton = (JButton) passwordEditor.getViewComponent().getComponent(1);
        JButton jButtonMock = mock(JButton.class);
        ButtonModel buttonModelMock = mock(ButtonModel.class);
        when(jButtonMock.getModel()).thenReturn(buttonModelMock);
        when(buttonModelMock.isPressed()).thenReturn(true);
        ChangeEvent changeEvent = new ChangeEvent(jButtonMock);
        Object[] listeners = jButton.getChangeListeners();
        ChangeListener listener1 = (ChangeListener) listeners[0];
        listener1.stateChanged(changeEvent);
        Assert.assertEquals(JTextField.class.toString(),
                passwordEditor.getViewComponent().getComponent(1).getClass().toString());
        JTextField jTextField = (JTextField) passwordEditor.getViewComponent().getComponent(1);
        Assert.assertEquals(PW, jTextField.getText());
        when(buttonModelMock.isPressed()).thenReturn(false);
        listener1.stateChanged(changeEvent);
        Assert.assertEquals(JPasswordField.class.toString(),
                passwordEditor.getViewComponent().getComponent(1).getClass().toString());

    }
}