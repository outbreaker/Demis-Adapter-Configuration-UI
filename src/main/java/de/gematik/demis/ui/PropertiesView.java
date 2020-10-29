package de.gematik.demis.ui;

import de.gematik.demis.entities.ADAPTER_Properties;
import de.gematik.demis.entities.APP_Properties;
import de.gematik.demis.entities.IProperties;
import de.gematik.demis.entities.VALUE_TYPE;
import de.gematik.demis.ui.value.editor.IValueTypeView;
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
    } else if (APP_Properties.containsProperties(prop)) {
      values = APP_Properties.values();
    } else {
      return;
    }
    Arrays.stream(values)
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
                ((RelativPathListEditor) editor).setFileExtension(new String[] {"json"});
              }

              editor.setExpertEditor(e.isExpertValue());
              editor.checkExpertMode();
              String property = prop.getProperty(e.getKey());
              if (property == null) {
                LOG.error(
                    "File: \""
                        + file.getName()
                        + "\" Property \""
                        + e.getKey()
                        + "\" has no Value!");
              } else {
                editor.setValue(property);
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
