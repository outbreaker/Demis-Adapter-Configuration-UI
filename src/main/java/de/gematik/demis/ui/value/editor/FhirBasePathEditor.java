package de.gematik.demis.ui.value.editor;

import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.entities.ADAPTER_Properties;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FhirBasePathEditor extends AbstractEditor {

  private static Logger LOG = LoggerFactory.getLogger(FhirBasePathEditor.class.getName());
  private Map<String, String> VALUES = new HashMap<>();
  private JComboBox<String> field;

  public FhirBasePathEditor(String value) {
    this();
    setValue(value);
  }

  public FhirBasePathEditor() {
    this.setLayout(new BorderLayout());
    VALUES.put("https://demis.rki.de/notification-api/fhir/", "https://demis.rki.de/auth/realms/LAB/protocol/openid-connect/token");
    VALUES.put("https://demis-test.rki.de/notification-api/fhir/", "https://demis-test.rki.de/auth/realms/LAB/protocol/openid-connect/token");
    field = new JComboBox<>();
    field.setModel(new DefaultComboBoxModel<String>(VALUES.keySet().toArray(new String[0])));
    this.add(field, BorderLayout.CENTER);
  }

  @Override
  public String getValue() {
    if (field.getSelectedItem() == null) {
      return "";
    } else {
      return field.getSelectedItem().toString();
    }
  }

  @Override
  public void setValue(String value) {
    field.setSelectedItem(value);
  }

  @Override
  public JComponent getViewComponent() {
    return this;
  }

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    field.addItemListener(itemEvent -> {
      ConfigurationLoader.getInstance()
          .setPropertiesValue(ADAPTER_Properties.IDP_LAB_TOKENENDPOINT, VALUES.get(field.getSelectedItem().toString()));
      changeListener.stateChanged(new ChangeEvent(FhirBasePathEditor.this));
    });

  }

  @Override
  public void checkExpertMode() {
    field.setEnabled(!isExpertEditor());
  }

  @Override
  public void activateForExperts() {
    field.setEnabled(true);
  }
}
