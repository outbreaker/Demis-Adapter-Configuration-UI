package de.gematik.demis.ui;

import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.entities.ReportingPerson;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import de.gematik.demis.ui.value.editor.StringEditor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportingPersonView extends AbstractEditorsView {

  private static Logger LOG = LoggerFactory.getLogger(ReportingPersonView.class.getName());
  private final ReportingPerson reportingPerson;

  public ReportingPersonView(ReportingPerson reportingPerson) {
    this.reportingPerson = reportingPerson;
    initComponents();
  }

  private void initComponents() {
    setLayout(new GridBagLayout());
    this.setBorder(new TitledBorder(LABORATORY_JSON.MELDER_PERSON.getDisplayName()));
    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;

    addLabel(c, LABORATORY_JSON.VORNAME);
    addEditor(new StringEditor(reportingPerson.getVorname()), c, LABORATORY_JSON.VORNAME);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.NACHNAME);
    addEditor(new StringEditor(reportingPerson.getNachname()), c, LABORATORY_JSON.NACHNAME);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.ANSCHRIFTENZEILE);
    addEditor(
        new StringEditor(reportingPerson.getAnschriftenzeile()),
        c,
        LABORATORY_JSON.ANSCHRIFTENZEILE);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.POSTLEITZAHL);
    addEditor(new StringEditor(reportingPerson.getPostleitzahl()), c, LABORATORY_JSON.POSTLEITZAHL);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.STADT);
    addEditor(new StringEditor(reportingPerson.getStadt()), c, LABORATORY_JSON.STADT);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.TELEFONNUMMER);
    addEditor(
        new StringEditor(reportingPerson.getTelefonnummer()), c, LABORATORY_JSON.TELEFONNUMMER);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.ERREICHBARKEIT);
    addEditor(
        new StringEditor(reportingPerson.getErreichbarkeit()), c, LABORATORY_JSON.ERREICHBARKEIT);
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

  public ReportingPerson getReportingPerson() {
    reportingPerson.setVorname(getValueEditors().get(LABORATORY_JSON.VORNAME).getValue());
    reportingPerson.setNachname(getValueEditors().get(LABORATORY_JSON.NACHNAME).getValue());
    reportingPerson.setErreichbarkeit(
        getValueEditors().get(LABORATORY_JSON.ERREICHBARKEIT).getValue());
    reportingPerson.setTelefonnummer(
        getValueEditors().get(LABORATORY_JSON.TELEFONNUMMER).getValue());
    reportingPerson.setStadt(getValueEditors().get(LABORATORY_JSON.STADT).getValue());
    reportingPerson.setPostleitzahl(getValueEditors().get(LABORATORY_JSON.POSTLEITZAHL).getValue());
    reportingPerson.setAnschriftenzeile(
        getValueEditors().get(LABORATORY_JSON.ANSCHRIFTENZEILE).getValue());
    return reportingPerson;
  }

  public void checkExpertMode() {
    getValueEditors().values().forEach(IValueTypeView::checkExpertMode);
  }
  ;

  public void activateForExperts() {
    getValueEditors().values().forEach(IValueTypeView::activateForExperts);
  }

  public void setJsonValue(LABORATORY_JSON property, String value) {
    if (getValueEditors().containsKey(property)) getValueEditors().get(property).setValue(value);
  }

  public boolean contains(LABORATORY_JSON property) {
    return getValueEditors().containsKey(property);
  }
}
