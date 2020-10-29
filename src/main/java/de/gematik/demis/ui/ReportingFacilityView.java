package de.gematik.demis.ui;

import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.entities.ReportingFacility;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import de.gematik.demis.ui.value.editor.StringEditor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportingFacilityView extends AbstractEditorsView {

  private static final Logger LOG = LoggerFactory.getLogger(ReportingFacilityView.class.getName());
  private final ReportingFacility reportingFacility;

  public ReportingFacilityView(ReportingFacility reportingFacility) {
    this.reportingFacility = reportingFacility;
    initComponents();
  }

  private void initComponents() {

    setLayout(new GridBagLayout());
    this.setBorder(new TitledBorder(LABORATORY_JSON.MELDER_EINRICHTUNG.getDisplayName()));
    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;

    addLabel(c, LABORATORY_JSON.BSNR);
    addEditor(new StringEditor(reportingFacility.getBsnr()), c, LABORATORY_JSON.BSNR);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.NAME);
    addEditor(new StringEditor(reportingFacility.getName()), c, LABORATORY_JSON.NAME);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.EINRICHTUNGS_ART);
    addEditor(
        new StringEditor(reportingFacility.getEinrichtungsArt()),
        c,
        LABORATORY_JSON.EINRICHTUNGS_ART);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.ANSPRECHSPARTNER_VORNAME);
    addEditor(
        new StringEditor(reportingFacility.getAnsprechspartnerVorname()),
        c,
        LABORATORY_JSON.ANSPRECHSPARTNER_VORNAME);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.ANSPRECHSPARTNER_NACHNAME);
    addEditor(
        new StringEditor(reportingFacility.getAnsprechspartnerNachname()),
        c,
        LABORATORY_JSON.ANSPRECHSPARTNER_NACHNAME);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.ANSCHRIFTENZEILE);
    addEditor(
        new StringEditor(reportingFacility.getAnschriftenzeile()),
        c,
        LABORATORY_JSON.ANSCHRIFTENZEILE);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.POSTLEITZAHL);
    addEditor(
        new StringEditor(reportingFacility.getPostleitzahl()), c, LABORATORY_JSON.POSTLEITZAHL);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.STADT);
    addEditor(new StringEditor(reportingFacility.getStadt()), c, LABORATORY_JSON.STADT);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.TELEFONNUMMER);
    addEditor(
        new StringEditor(reportingFacility.getTelefonnummer()), c, LABORATORY_JSON.TELEFONNUMMER);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.FAXNUMMER);
    addEditor(new StringEditor(reportingFacility.getFaxnummer()), c, LABORATORY_JSON.FAXNUMMER);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.EMAIL);
    addEditor(new StringEditor(reportingFacility.getEmail()), c, LABORATORY_JSON.EMAIL);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.WEBSEITE);
    addEditor(new StringEditor(reportingFacility.getWebseite()), c, LABORATORY_JSON.WEBSEITE);
    c.gridy++;

    this.repaint();
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

  public ReportingFacility getReportingFacility() {
    reportingFacility.setTelefonnummer(
        getValueEditors().get(LABORATORY_JSON.TELEFONNUMMER).getValue());
    reportingFacility.setStadt(getValueEditors().get(LABORATORY_JSON.STADT).getValue());
    reportingFacility.setPostleitzahl(
        getValueEditors().get(LABORATORY_JSON.POSTLEITZAHL).getValue());
    reportingFacility.setAnschriftenzeile(
        getValueEditors().get(LABORATORY_JSON.ANSCHRIFTENZEILE).getValue());
    reportingFacility.setBsnr(getValueEditors().get(LABORATORY_JSON.BSNR).getValue());
    reportingFacility.setWebseite(getValueEditors().get(LABORATORY_JSON.WEBSEITE).getValue());
    reportingFacility.setEmail(getValueEditors().get(LABORATORY_JSON.EMAIL).getValue());
    reportingFacility.setFaxnummer(getValueEditors().get(LABORATORY_JSON.FAXNUMMER).getValue());
    reportingFacility.setAnsprechspartnerVorname(
        getValueEditors().get(LABORATORY_JSON.ANSPRECHSPARTNER_VORNAME).getValue());
    reportingFacility.setAnsprechspartnerNachname(
        getValueEditors().get(LABORATORY_JSON.ANSPRECHSPARTNER_NACHNAME).getValue());
    reportingFacility.setEinrichtungsArt(
        getValueEditors().get(LABORATORY_JSON.EINRICHTUNGS_ART).getValue());
    reportingFacility.setName(getValueEditors().get(LABORATORY_JSON.NAME).getValue());
    return reportingFacility;
  }

  public void checkExpertMode() {
    getValueEditors().values().forEach(IValueTypeView::checkExpertMode);
  }

  public void activateForExperts() {
    getValueEditors().values().forEach(IValueTypeView::activateForExperts);
  }

  public void setJsonValue(LABORATORY_JSON property, String value) {
    if (getValueEditors().containsKey(property)) getValueEditors().get(property).setValue(value);
  }

  public boolean contains(LABORATORY_JSON property) {
    if (getValueEditors().containsKey(property)) return true;
    return false;
  }
}
