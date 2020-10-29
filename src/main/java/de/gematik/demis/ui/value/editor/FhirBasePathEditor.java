package de.gematik.demis.ui.value.editor;

import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.entities.ADAPTER_Properties;
import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.ui.LaboratoryView;
import de.gematik.demis.ui.MainView;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
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
    VALUES.put(
        "https://demis.rki.de/notification-api/fhir/",
        "https://demis.rki.de/auth/realms/LAB/protocol/openid-connect/token");
    VALUES.put(
        "https://demis-test.rki.de/notification-api/fhir/",
        "https://demis-test.rki.de/auth/realms/LAB/protocol/openid-connect/token");
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
    field.addItemListener(
        new ItemListener() {
          private final ResourceBundle messages =
              ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
          private Object oldItem;
          private boolean cancel = false;

          @Override
          public void itemStateChanged(ItemEvent itemEvent) {
            if (cancel) {
              cancel = false;
              return;
            }
            if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
              oldItem = itemEvent.getItem();
            } else if (itemEvent.getStateChange() == ItemEvent.SELECTED
                && !oldItem.equals(itemEvent.getItem())) {
              int i =
                  JOptionPane.showConfirmDialog(
                      MainView.getInstance().getMainComponent(),
                      messages.getString("CHANGE_FHIR_PATH"),
                      messages.getString("CHANGE_FHIR_PATH_TITLE"),
                      JOptionPane.YES_NO_CANCEL_OPTION);
              if (i == JOptionPane.CANCEL_OPTION) {
                cancel = true;
                JComboBox<String> stringJComboBox = (JComboBox<String>) itemEvent.getSource();
                stringJComboBox.setSelectedItem(oldItem);
                return;
              }

              if (i == JOptionPane.YES_OPTION || i == JOptionPane.NO_OPTION) {
                ConfigurationLoader.getInstance().setJsonValue(LABORATORY_JSON.USERNAME, "");
                ConfigurationLoader.getInstance().setJsonValue(LABORATORY_JSON.AUTHCERTALIAS, "");
                ConfigurationLoader.getInstance()
                    .setJsonValue(LABORATORY_JSON.AUTHCERTKEYSTORE, "");
                ConfigurationLoader.getInstance()
                    .setJsonValue(LABORATORY_JSON.AUTHCERTPASSWORD, "");

                ConfigurationLoader.getInstance()
                    .setPropertiesValue(
                        ADAPTER_Properties.IDP_LAB_TOKENENDPOINT,
                        VALUES.get(Objects.requireNonNull(field.getSelectedItem()).toString()));
                changeListener.stateChanged(new ChangeEvent(FhirBasePathEditor.this));
              }

              if (i == JOptionPane.YES_OPTION) {
                Optional<LaboratoryView> laboratoryView =
                    ConfigurationLoader.getInstance().showFirstViewWith(LABORATORY_JSON.USERNAME);
                laboratoryView.ifPresent(LaboratoryView::openLoadCertificate);
              }
            }
          }
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
