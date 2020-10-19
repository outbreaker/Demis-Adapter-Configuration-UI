package de.gematik.demis.ui;

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
    private final HashMap<String, IValueTypeView> values = new HashMap<>();

    public ReportingPersonView(ReportingPerson reportingPerson) {
        initComponents(reportingPerson);
    }

    private void initComponents(ReportingPerson reportingPerson) {
        setLayout(new GridBagLayout());
        this.setBorder(new TitledBorder("Melder-Person"));
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;

        addLabel(c, new Label("Vorname"));
        addEditor(new StringEditor(reportingPerson.getVorname()), c, "Vorname");
        c.gridy++;

        addLabel(c, new Label("Nachname"));
        addEditor(new StringEditor(reportingPerson.getNachname()), c, "Nachname");
        c.gridy++;

        addLabel(c, new Label("Anschriftenzeile"));
        addEditor(new StringEditor(reportingPerson.getAnschriftenzeile()), c, "Anschriftenzeile");
        c.gridy++;

        addLabel(c, new Label("Postleitzahl"));
        addEditor(new StringEditor(reportingPerson.getPostleitzahl()), c, "Postleitzahl");
        c.gridy++;

        addLabel(c, new Label("Stadt"));
        addEditor(new StringEditor(reportingPerson.getStadt()), c, "Stadt");
        c.gridy++;

        addLabel(c, new Label("Telefonnummer"));
        addEditor(new StringEditor(reportingPerson.getTelefonnummer()), c, "Telefonnummer");
        c.gridy++;

        addLabel(c, new Label("Erreichbarkeit"));
        addEditor(new StringEditor(reportingPerson.getErreichbarkeit()), c, "Erreichbarkeit");
        c.gridy++;

        this.repaint();
    }

    private void addEditor(IValueTypeView editor, GridBagConstraints c, String id) {
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

}
