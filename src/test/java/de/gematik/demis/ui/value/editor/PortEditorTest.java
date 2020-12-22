package de.gematik.demis.ui.value.editor;

import de.gematik.demis.exceptions.ParseRuntimeException;
import javax.swing.JFormattedTextField;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class PortEditorTest {
  @Test
  void getSetValue() {
    PortEditor portEditor = new PortEditor();
    Assert.assertEquals("", portEditor.getValue());
    portEditor.setValue("0");
    Assert.assertEquals("0", portEditor.getValue());
    portEditor.setValue("65535");
    Assert.assertEquals("65535", portEditor.getValue());
  }

  @Test
  void getSetValueWrong() {
    PortEditor portEditor = new PortEditor();
    Assertions.assertThrows(
        ParseRuntimeException.class,
        () -> {
          portEditor.setValue("10000000");
        });
    Assertions.assertThrows(
        ParseRuntimeException.class,
        () -> {
          portEditor.setValue("65536");
        });
    Assertions.assertThrows(
        ParseRuntimeException.class,
        () -> {
          portEditor.setValue("-1");
        });
  }

  @Test
  void setWrongValue() {
    PortEditor portEditor = new PortEditor();
    Assertions.assertThrows(
        NumberFormatException.class,
        () -> {
          portEditor.setValue("abc");
        });
  }

  @Test
  void getViewComponent() {
    PortEditor portEditor = new PortEditor();
    Assert.assertNotNull(portEditor.getViewComponent());
    Assert.assertEquals(
        JFormattedTextField.class.toString(),
        portEditor.getViewComponent().getComponent(0).getClass().toString());
    Assert.assertNull(
        ((JFormattedTextField) portEditor.getViewComponent().getComponent(0)).getValue());
    portEditor.setValue("32");
    Assert.assertEquals(
        "32",
        ((JFormattedTextField) portEditor.getViewComponent().getComponent(0))
            .getValue()
            .toString());
  }
}
