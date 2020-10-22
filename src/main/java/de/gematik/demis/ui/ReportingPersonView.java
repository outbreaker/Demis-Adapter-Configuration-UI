package de.gematik.demis.ui;

import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.entities.ReportingPerson;
import de.gematik.demis.entities.VALUE_TYPE;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import de.gematik.demis.ui.value.editor.StringEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;

public class ReportingPersonView extends JPanel {
    private static Logger LOG = LoggerFactory.getLogger(ReportingPersonView.class.getName());
    private final HashMap<LABORATORY_JSON, IValueTypeView> values = new HashMap<>();
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

        addLabel(c, new Label(LABORATORY_JSON.VORNAME.getDisplayName()));
        addEditor(new StringEditor(reportingPerson.getVorname()), c, LABORATORY_JSON.VORNAME);
        c.gridy++;

        addLabel(c, new Label(LABORATORY_JSON.NACHNAME.getDisplayName()));
        addEditor(new StringEditor(reportingPerson.getNachname()), c, LABORATORY_JSON.NACHNAME);
        c.gridy++;

        addLabel(c, new Label(LABORATORY_JSON.ANSCHRIFTENZEILE.getDisplayName()));
        addEditor(new StringEditor(reportingPerson.getAnschriftenzeile()), c, LABORATORY_JSON.ANSCHRIFTENZEILE);
        c.gridy++;

        addLabel(c, new Label(LABORATORY_JSON.POSTLEITZAHL.getDisplayName()));
        addEditor(new StringEditor(reportingPerson.getPostleitzahl()), c, LABORATORY_JSON.POSTLEITZAHL);
        c.gridy++;

        addLabel(c, new Label(LABORATORY_JSON.STADT.getDisplayName()));
        addEditor(new StringEditor(reportingPerson.getStadt()), c, LABORATORY_JSON.STADT);
        c.gridy++;

        addLabel(c, new Label(LABORATORY_JSON.TELEFONNUMMER.getDisplayName()));
        addEditor(new StringEditor(reportingPerson.getTelefonnummer()), c, LABORATORY_JSON.TELEFONNUMMER);
        c.gridy++;

        addLabel(c, new Label(LABORATORY_JSON.ERREICHBARKEIT.getDisplayName()));
        addEditor(new StringEditor(reportingPerson.getErreichbarkeit()), c, LABORATORY_JSON.ERREICHBARKEIT);
        c.gridy++;

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

    public ReportingPerson getReportingPerson() {
        reportingPerson.setVorname(values.get(LABORATORY_JSON.VORNAME).getValue());
        reportingPerson.setNachname(values.get(LABORATORY_JSON.NACHNAME).getValue());
        reportingPerson.setErreichbarkeit(values.get(LABORATORY_JSON.ERREICHBARKEIT).getValue());
        reportingPerson.setTelefonnummer(values.get(LABORATORY_JSON.TELEFONNUMMER).getValue());
        reportingPerson.setStadt(values.get(LABORATORY_JSON.STADT).getValue());
        reportingPerson.setPostleitzahl(values.get(LABORATORY_JSON.POSTLEITZAHL).getValue());
        reportingPerson.setAnschriftenzeile(values.get(LABORATORY_JSON.ANSCHRIFTENZEILE).getValue());
        return reportingPerson;
    }
}
