package de.gematik.demis.ui;

import de.gematik.demis.entities.ADAPTER_Properties;
import de.gematik.demis.entities.APP_Properties;
import de.gematik.demis.entities.IProperties;
import de.gematik.demis.entities.VALUE_TYPE;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import de.gematik.demis.ui.value.editor.RelativPathEditor;
import de.gematik.demis.ui.value.editor.RelativPathListEditor;
import de.gematik.demis.ui.value.editor.ValueTypeEditorFactory;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesView extends AbstractConfigurationView {

  private static final Logger LOG = LoggerFactory.getLogger(PropertiesView.class.getName());
  private final Map<IProperties, IValueTypeView> editors = new HashMap<>();
  private final Properties prop = new Properties();
  private final Path path;
  private final ResourceBundle messages =
      ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
  private IProperties[] values;

  public PropertiesView(Path path) {
    this.path = path;
    initComponents(path.toFile());
  }

  private void initComponents(File file) {
    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream(file));
    } catch (IOException e) {
      LOG.error("Could not load Properties-File \"" + file.getAbsolutePath() + "\"", e);
    }

    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;

    if (ADAPTER_Properties.containsProperties(prop)) {
      values = ADAPTER_Properties.values();
      convertProxyFromSinglelineToMultiline(prop);
    } else if (APP_Properties.containsProperties(prop)) {
      values = APP_Properties.values();
    } else {
      return;
    }
    Arrays.stream(values)
        .filter(e -> !e.isDeprecated())
        .forEach(
            e -> {
              c.weighty = 0.1;
              c.fill = GridBagConstraints.BOTH;
              c.gridx = 0;
              c.insets = new Insets(0, 10, 0, 10); // top padding
              c.anchor = GridBagConstraints.LAST_LINE_START;
              c.weightx = 0;
              JLabel label = new JLabel(e.getDisplayName());
              label.setToolTipText(e.getToolTip());
              this.add(label, c);

              c.gridx = 1;
              c.weightx = 1.0;
              if (e.getType() == VALUE_TYPE.STRING_LIST) {
                c.weighty = 0.5;
                c.fill = GridBagConstraints.BOTH;
              }
              IValueTypeView editor = ValueTypeEditorFactory.createEditor(e.getType());
              if (e == ADAPTER_Properties.LABOR_CONFIGFILE
                  && editor instanceof RelativPathListEditor) {
                ((RelativPathListEditor) editor).setFileExtension("json");
              }
                if (e == ADAPTER_Properties.IDP_LAB_TRUSTSTORE
                        && editor instanceof RelativPathEditor) {
                    ((RelativPathEditor) editor).setFileExtension("truststore");
                }

              editor.setExpertEditor(e.isExpertValue());
              editor.checkExpertMode();

              String property =
                  prop.containsKey(e.getKey()) ? prop.getProperty(e.getKey()) : e.getDefaultValue();
              if (property == null) {
                LOG.error(
                    "File: \""
                        + file.getName()
                        + "\" Property \""
                        + e.getKey()
                        + "\" has no Value!");
              } else {
                try {
                  editor.setValue(property);
                } catch (NumberFormatException exception) {
                  editor.setValue(e.getDefaultValue());
                  handleSetValueException(exception, property, e.getKey());
                } catch (Exception exception) {
                  handleSetValueException(exception, property, e.getKey());
                }
              }
              editor.addChangeListener(
                  s -> {
                    setUnsaved();
                  });
              this.add(editor.getViewComponent(), c);
              editors.put(e, editor);
              c.gridy++;
            });
    this.repaint();
  }

  private void handleSetValueException(Exception exception, String value, String property) {
    ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
    if (exception instanceof NumberFormatException) {
      JOptionPane.showMessageDialog(
          MainView.getInstance().getMainComponent(),
          messages
              .getString("ERROR_SET_VALUE_AS_NUMBER")
              .replace("##VALUE##", value)
              .replace("##PROPERTY##", property),
          messages.getString("ERROR_SET_VALUE_AS_NUMBER_TITLE"),
          JOptionPane.WARNING_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(
          MainView.getInstance().getMainComponent(),
          messages
              .getString("ERROR_SET_VALUE")
              .replace("##VALUE##", value)
              .replace("##PROPERTY##", property),
          messages.getString("ERROR_SET_VALUE_TITLE"),
          JOptionPane.WARNING_MESSAGE);
    }
    setUnsaved();
  }

  private void convertProxyFromSinglelineToMultiline(Properties prop) {
    String proxyKey = ADAPTER_Properties.IDP_LAB_PROXY.getKey();
    String proxy = prop.getProperty(proxyKey);
    if (prop.containsKey(proxyKey) && proxy.length() > 0) {
      int i = proxy.lastIndexOf(":");
      String[] hostPort = {proxy.substring(0, i), proxy.substring(i + 1)};
      prop.remove(proxyKey);
      prop.put(ADAPTER_Properties.IDP_LAB_PROXY_HOST.getKey(), hostPort[0]);
      prop.put(ADAPTER_Properties.IDP_LAB_PROXY_PORT.getKey(), hostPort[1]);
      setUnsaved();
    }
  }

  public void checkExpertMode() {
    editors.values().forEach(IValueTypeView::checkExpertMode);
  }

  public void activateForExperts() {
    editors.values().forEach(IValueTypeView::activateForExperts);
  }

  private Component createComponent(VALUE_TYPE valueType) {
    switch (valueType) {
      case BOOLEAN:
        return new JCheckBox();
      default:
        return new JLabel(valueType.toString());
    }
  }

  public Properties getProperties() {
    editors.forEach(
        (k, v) -> {
          prop.put(k.getKey(), v.getValue());
        });
    return prop;
  }

  public Path getPath() {
    return path;
  }

  @Override
  public String getName() {
    return path == null ? messages.getString("NEW_PROPS") + " *" : path.toFile().getName();
  }

  public void setPropertiesValue(IProperties property, String value) {
    if (editors.containsKey(property)) {
      editors.get(property).setValue(value);
      setUnsaved();
    }
  }

  public IValueTypeView getEditor(IProperties property) {
    if (editors.containsKey(property)) return editors.get(property);
    return null;
  }

  public boolean hasEditor(IProperties property) {
    return editors.containsKey(property);
  }
}
