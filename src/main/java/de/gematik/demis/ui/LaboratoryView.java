package de.gematik.demis.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gematik.demis.control.LaboratoryFactory;
import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.entities.Laboratory;
import de.gematik.demis.entities.VALUE_TYPE;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import de.gematik.demis.ui.value.editor.StringEditor;
import de.gematik.demis.ui.value.editor.StringListEditor;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaboratoryView extends AbstractConfigurationView {

  private static final Logger LOG = LoggerFactory.getLogger(LaboratoryView.class.getName());
  private final ResourceBundle messages =
      ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
  private Path path;
  private Laboratory laboratory;
  private IdentityProviderView identityProviderView;
  private ReportingPersonView reportingPersonView;
  private ReportingFacilityView reportingFacilityView;

  public LaboratoryView() {
    this(null);
    setUnsaved();
  }

  public LaboratoryView(Path path) {
    this.path = path;
    initComponents();
  }

  private void initComponents() {
    if (path != null) {
      loadFromFile();
    } else {
      laboratory = LaboratoryFactory.createDefaultLaboratory();
    }
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.gridy = 0;
    addLabel(c, LABORATORY_JSON.IDENTIFIKATOR);
    addEditor(new StringEditor(laboratory.getIdentifikator()), c, LABORATORY_JSON.IDENTIFIKATOR);
    c.gridy++;
    addLabel(c, LABORATORY_JSON.POSITIVE_TESTERGEBNIS_BEZEICHNUNGEN);
    addEditor(
        new StringListEditor(laboratory.getPositiveTestergebnisBezeichnungen()),
        c,
        LABORATORY_JSON.POSITIVE_TESTERGEBNIS_BEZEICHNUNGEN);
    c.gridy++;
    c.gridx = 0;
    c.gridwidth = 2;
    c.weighty = 0.5;
    c.fill = GridBagConstraints.BOTH;
    c.insets = new Insets(20, 0, 0, 0); // top padding

    reportingPersonView = new ReportingPersonView(laboratory.getMelderPerson());
    this.add(reportingPersonView, c);
    reportingPersonView.addChangeListener(
        s -> {
          setUnsaved();
        });

    c.gridy++;
    reportingFacilityView = new ReportingFacilityView(laboratory.getMelderEinrichtung());
    this.add(reportingFacilityView, c);
    reportingFacilityView.addChangeListener(
        s -> {
          setUnsaved();
        });

    c.gridy++;
    identityProviderView = new IdentityProviderView(laboratory.getIdp());
    this.add(identityProviderView, c);
    identityProviderView.addChangeListener(
        s -> {
          setUnsaved();
        });

    this.repaint();
  }

  private void loadFromFile() {
    LOG.debug("Laboratory File to load: " + path.toFile().getAbsolutePath());
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      laboratory = objectMapper.readValue(path.toFile(), Laboratory.class);
    } catch (IOException e) {
      LOG.error("Failed to load Laboratory JSON: '" + path.toFile().getAbsolutePath() + "'", e);
      throw new RuntimeException(
          "Failed to load Laboratory JSON: '" + path.toFile().getAbsolutePath() + "'", e);
    }
  }

  private void addEditor(IValueTypeView editor, GridBagConstraints c, LABORATORY_JSON id) {
    c.gridx = 1;
    c.weightx = 1.0;
    this.add(editor.getViewComponent(), c);
    addAndConfigEditor(editor, id);
  }

  private void addLabel(GridBagConstraints c, LABORATORY_JSON value) {
    c.weighty = 0.1;
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.insets = new Insets(0, 10, 0, 10); // top padding
    c.anchor = GridBagConstraints.LAST_LINE_START;
    c.weightx = 0;
    JLabel label = new JLabel(value.getDisplayName());
    label.setToolTipText(value.getToolTip());
    this.add(label, c);
  }

  private Component createComponent(VALUE_TYPE valueType) {
    switch (valueType) {
      case BOOLEAN:
        return new JCheckBox();
      default:
        return new JLabel(valueType.toString());
    }
  }

  public Path getPath() {
    return path;
  }

  public void setPath(Path path) {
    this.path = path;
  }

  public Laboratory getLaboratory() {
    laboratory.setIdentifikator(getValueEditors().get(LABORATORY_JSON.IDENTIFIKATOR).getValue());
    laboratory.setPositiveTestergebnisBezeichnungen(
        getValueEditors()
            .get(LABORATORY_JSON.POSITIVE_TESTERGEBNIS_BEZEICHNUNGEN)
            .getValue()
            .split(","));
    laboratory.setIdp(identityProviderView.getIdentityProvider());
    laboratory.setMelderEinrichtung(reportingFacilityView.getReportingFacility());
    laboratory.setMelderPerson(reportingPersonView.getReportingPerson());

    return laboratory;
  }

  public void checkExpertMode() {
    getValueEditors().values().forEach(IValueTypeView::checkExpertMode);
    identityProviderView.checkExpertMode();
    reportingPersonView.checkExpertMode();
    reportingFacilityView.checkExpertMode();
  }

  public void activateForExperts() {
    getValueEditors().values().forEach(IValueTypeView::activateForExperts);
    identityProviderView.activateForExperts();
    reportingPersonView.activateForExperts();
    reportingFacilityView.activateForExperts();
  }

  @Override
  public String getName() {
    return path == null ? messages.getString("NEW_LAB") + " *" : path.toFile().getName();
  }

  public void setJsonValue(LABORATORY_JSON property, String value) {
    if (getValueEditors().containsKey(property)) getValueEditors().get(property).setValue(value);
    identityProviderView.setJsonValue(property, value);
    reportingPersonView.setJsonValue(property, value);
    reportingFacilityView.setJsonValue(property, value);
    setUnsaved();
  }

  public boolean contains(LABORATORY_JSON property) {
    if (getValueEditors().containsKey(property)) return true;
    if (identityProviderView.contains(property)) return true;
    if (reportingPersonView.contains(property)) return true;
    if (reportingFacilityView.contains(property)) return true;
    return false;
  }

  public void openLoadCertificate() {
    identityProviderView.openLoadCertificate();
  }
}
