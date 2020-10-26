package de.gematik.demis.ui;

import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.entities.ReportingFacility;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import de.gematik.demis.ui.value.editor.StringEditor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportingFacilityView extends JPanel {

  private static Logger LOG = LoggerFactory.getLogger(ReportingFacilityView.class.getName());
  private final HashMap<LABORATORY_JSON, IValueTypeView> values = new HashMap<>();
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

    addLabel(c, new Label(LABORATORY_JSON.BSNR.getDisplayName()));
    addEditor(new StringEditor(reportingFacility.getBsnr()), c, LABORATORY_JSON.BSNR);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.NAME.getDisplayName()));
    addEditor(new StringEditor(reportingFacility.getName()), c, LABORATORY_JSON.NAME);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.EINRICHTUNGS_ART.getDisplayName()));
    addEditor(
        new StringEditor(reportingFacility.getEinrichtungsArt()),
        c,
        LABORATORY_JSON.EINRICHTUNGS_ART);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.ANSPRECHSPARTNER_VORNAME.getDisplayName()));
    addEditor(
        new StringEditor(reportingFacility.getAnsprechspartnerVorname()),
        c,
        LABORATORY_JSON.ANSPRECHSPARTNER_VORNAME);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.ANSPRECHSPARTNER_NACHNAME.getDisplayName()));
    addEditor(
        new StringEditor(reportingFacility.getAnsprechspartnerNachname()),
        c,
        LABORATORY_JSON.ANSPRECHSPARTNER_NACHNAME);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.ANSCHRIFTENZEILE.getDisplayName()));
    addEditor(
        new StringEditor(reportingFacility.getAnschriftenzeile()),
        c,
        LABORATORY_JSON.ANSCHRIFTENZEILE);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.POSTLEITZAHL.getDisplayName()));
    addEditor(
        new StringEditor(reportingFacility.getPostleitzahl()), c, LABORATORY_JSON.POSTLEITZAHL);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.STADT.getDisplayName()));
    addEditor(new StringEditor(reportingFacility.getStadt()), c, LABORATORY_JSON.STADT);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.TELEFONNUMMER.getDisplayName()));
    addEditor(
        new StringEditor(reportingFacility.getTelefonnummer()), c, LABORATORY_JSON.TELEFONNUMMER);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.FAXNUMMER.getDisplayName()));
    addEditor(new StringEditor(reportingFacility.getFaxnummer()), c, LABORATORY_JSON.FAXNUMMER);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.EMAIL.getDisplayName()));
    addEditor(new StringEditor(reportingFacility.getEmail()), c, LABORATORY_JSON.EMAIL);
    c.gridy++;

    addLabel(c, new Label(LABORATORY_JSON.WEBSEITE.getDisplayName()));
    addEditor(new StringEditor(reportingFacility.getWebseite()), c, LABORATORY_JSON.WEBSEITE);
    c.gridy++;

    this.repaint();
  }

  private void addEditor(IValueTypeView editor, GridBagConstraints c, LABORATORY_JSON id) {
    c.gridx = 1;
    c.weightx = 1.0;
    editor.setExpertEditor(id.isExpertValue());
    editor.checkExpertMode();
    this.add(editor.getViewComponent(), c);
    values.put(id, editor);
  }

  private void addLabel(GridBagConstraints c, Label label) {
    c.weighty = 0.1;
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.insets = new Insets(0, 10, 0, 10); // top padding
    c.anchor = GridBagConstraints.LAST_LINE_START;
    c.weightx = 0;
    this.add(label, c);
  }

  public ReportingFacility getReportingFacility() {
    reportingFacility.setTelefonnummer(values.get(LABORATORY_JSON.TELEFONNUMMER).getValue());
    reportingFacility.setStadt(values.get(LABORATORY_JSON.STADT).getValue());
    reportingFacility.setPostleitzahl(values.get(LABORATORY_JSON.POSTLEITZAHL).getValue());
    reportingFacility.setAnschriftenzeile(values.get(LABORATORY_JSON.ANSCHRIFTENZEILE).getValue());
    reportingFacility.setBsnr(values.get(LABORATORY_JSON.BSNR).getValue());
    reportingFacility.setWebseite(values.get(LABORATORY_JSON.WEBSEITE).getValue());
    reportingFacility.setEmail(values.get(LABORATORY_JSON.EMAIL).getValue());
    reportingFacility.setFaxnummer(values.get(LABORATORY_JSON.FAXNUMMER).getValue());
    reportingFacility.setAnsprechspartnerVorname(
        values.get(LABORATORY_JSON.ANSPRECHSPARTNER_VORNAME).getValue());
    reportingFacility.setAnsprechspartnerNachname(
        values.get(LABORATORY_JSON.ANSPRECHSPARTNER_NACHNAME).getValue());
    reportingFacility.setEinrichtungsArt(values.get(LABORATORY_JSON.EINRICHTUNGS_ART).getValue());
    reportingFacility.setName(values.get(LABORATORY_JSON.NAME).getValue());
    return reportingFacility;
  }

  public void checkExpertMode(){
    values.values().forEach(IValueTypeView::checkExpertMode);
  };

  public void activateForExperts(){
    values.values().forEach(IValueTypeView::activateForExperts);
  }
}
