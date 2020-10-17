package de.gematik.demis.ui.value.editor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.swing.*;

@RunWith(JUnitPlatform.class)
class UrlEditorTest {


    public static final String HTTPS_URL_1 = "https://eineUrl.domain.td:1234/abc/def";
    public static final String HTTPS_URL_2 = "https://demis-int.rki.de/notification-api/fhir/";
    public static final String HTTPS_URL_3 = "https://demis.rki.de/notification-api/fhir/";
    public static final String HTTP_URL = "http://eineUrl.de:1234/abc/def";
    public static final String WRONG_URL_1 = "ftp://eineUrl.de:1234/abc/def";
    public static final String WRONG_URL_2 = "def.de";
    public static final String WRONG_URL_3 = "http://asbc.er:4565";

    @Test
    void getSetValue() {
        UrlEditor urlEditor = new UrlEditor();
        Assert.assertNotNull(urlEditor.getValue());
        urlEditor.setValue(HTTPS_URL_1);
        Assert.assertEquals(HTTPS_URL_1, urlEditor.getValue());
    }

    @Test
    void getViewComponent() {
        UrlEditor urlEditor = new UrlEditor();
        Assert.assertNotNull(urlEditor.getViewComponent());
        Assert.assertEquals(JTextField.class.toString(),
                urlEditor.getViewComponent().getComponent(0).getClass().toString());
        Assert.assertNotNull(((JTextField) urlEditor.getViewComponent().getComponent(0)).getText());
        urlEditor.setValue(HTTPS_URL_1);
        Assert.assertEquals(HTTPS_URL_1, ((JTextField) urlEditor.getViewComponent().getComponent(0)).getText());

    }

    @Test
    void checkContent() {
        UrlEditor urlEditor = new UrlEditor();
        Assert.assertNotNull(urlEditor.getValue());
        Assert.assertFalse(urlEditor.checkContent());
        urlEditor.setValue(HTTPS_URL_1);
        Assert.assertTrue(urlEditor.checkContent());
        urlEditor.setValue(HTTPS_URL_2);
        Assert.assertTrue(urlEditor.checkContent());
        urlEditor.setValue(HTTPS_URL_3);
        Assert.assertTrue(urlEditor.checkContent());
        urlEditor.setValue(HTTP_URL);
        Assert.assertFalse(urlEditor.checkContent());
        urlEditor.setValue(WRONG_URL_1);
        Assert.assertFalse(urlEditor.checkContent());
        urlEditor.setValue(WRONG_URL_2);
        Assert.assertFalse(urlEditor.checkContent());
        urlEditor.setValue(WRONG_URL_3);
        Assert.assertFalse(urlEditor.checkContent());
    }
}