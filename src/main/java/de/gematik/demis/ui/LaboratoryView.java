package de.gematik.demis.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.awt.Label;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaboratoryView extends JPanel {

  private static Logger LOG = LoggerFactory.getLogger(LaboratoryView.class.getName());
  private final HashMap<LABORATORY_JSON, IValueTypeView> values = new HashMap<>();
  private final Path path;
  private Laboratory laboratory;
  private IdentityProviderView identityProviderView;
  private ReportingPersonView reportingPersonView;
  private ReportingFacilityView reportingFacilityView;


  public LaboratoryView(Path path) {
    this.path = path;
    initComponents(path.toFile());
  }

  private void initComponents(File file) {
    LOG.debug("Laboratory File to load: " + file.getAbsolutePath());
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      laboratory = objectMapper.readValue(file, Laboratory.class);
    } catch (IOException e) {
      LOG.error("Failed to load Laboratory JSON: '" + file.getAbsolutePath() + "'", e);
      throw new RuntimeException("Failed to load Laboratory JSON: '" + file.getAbsolutePath() + "'",
          e);
    }

    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.gridy = 0;
    addLabel(c, new Label(LABORATORY_JSON.IDENTIFIKATOR.getDisplayName()));
    addEditor(new StringEditor(laboratory.getIdentifikator()), c, LABORATORY_JSON.IDENTIFIKATOR);
    c.gridy++;
    addLabel(c, new Label(LABORATORY_JSON.POSITIVE_TESTERGEBNIS_BEZEICHNUNGEN.getDisplayName()));
    addEditor(new StringListEditor(laboratory.getPositiveTestergebnisBezeichnungen()), c,
        LABORATORY_JSON.POSITIVE_TESTERGEBNIS_BEZEICHNUNGEN);
    c.gridy++;
    c.gridx = 0;
    c.gridwidth = 2;
    c.weighty = 0.5;
    c.fill = GridBagConstraints.BOTH;

    reportingPersonView = new ReportingPersonView(laboratory.getMelderPerson());
    this.add(reportingPersonView, c);
    c.gridy++;
    reportingFacilityView = new ReportingFacilityView(laboratory.getMelderEinrichtung());
    this.add(reportingFacilityView, c);

    c.gridy++;
    identityProviderView = new IdentityProviderView(laboratory.getIdp());
    this.add(identityProviderView, c);

    this.repaint();
  }

  private void addEditor(IValueTypeView editor, GridBagConstraints c, LABORATORY_JSON id) {
    c.gridx = 1;
    c.weightx = 1.0;
    this.add(editor.getViewComponent(), c);
    values.put(id, editor);
  }

  private void addLabel(GridBagConstraints c, Label label) {
    c.weighty = 0.1;
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.insets = new Insets(0, 10, 0, 10);  //top padding
    c.anchor = GridBagConstraints.LAST_LINE_START;
    c.weightx = 0;
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

  public Laboratory getLaboratory() {
    laboratory.setIdentifikator(values.get(LABORATORY_JSON.IDENTIFIKATOR).getValue());
    laboratory.setPositiveTestergebnisBezeichnungen(
        values.get(LABORATORY_JSON.POSITIVE_TESTERGEBNIS_BEZEICHNUNGEN).getValue().split(","));
    laboratory.setIdp(identityProviderView.getIdentityProvider());
    laboratory.setMelderEinrichtung(reportingFacilityView.getReportingFacility());
    laboratory.setMelderPerson(reportingPersonView.getReportingPerson());

    return laboratory;
  }
}
