package de.gematik.demis.ui;

import de.gematik.demis.entities.ReportingFacility;
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

public class ReportingFacilityView extends JPanel {
    private static Logger LOG = LoggerFactory.getLogger(ReportingFacilityView.class.getName());
    private final HashMap<String, IValueTypeView> values = new HashMap<>();

    public ReportingFacilityView(ReportingFacility reportingFacility) {
        initComponents(reportingFacility);
    }

    private void initComponents(ReportingFacility reportingFacility) {

        setLayout(new GridBagLayout());
        this.setBorder(new TitledBorder("Melder-Einrichtung"));
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;

        addLabel(c, new Label("Bsnr"));
        addEditor(new StringEditor(reportingFacility.getBsnr()), c, "Bsnr");
        c.gridy++;

        addLabel(c, new Label("Name"));
        addEditor(new StringEditor(reportingFacility.getName()), c, "Name");
        c.gridy++;

        addLabel(c, new Label("EinrichtungsArt"));
        addEditor(new StringEditor(reportingFacility.getEinrichtungsArt()), c, "EinrichtungsArt");
        c.gridy++;

        addLabel(c, new Label("AnsprechspartnerVorname"));
        addEditor(new StringEditor(reportingFacility.getAnsprechspartnerVorname()), c, "AnsprechspartnerVorname");
        c.gridy++;

        addLabel(c, new Label("AnsprechspartnerNachname"));
        addEditor(new StringEditor(reportingFacility.getAnsprechspartnerNachname()), c, "AnsprechspartnerNachname");
        c.gridy++;

        addLabel(c, new Label("Anschriftenzeile"));
        addEditor(new StringEditor(reportingFacility.getAnschriftenzeile()), c, "Anschriftenzeile");
        c.gridy++;

        addLabel(c, new Label("Postleitzahl"));
        addEditor(new StringEditor(reportingFacility.getPostleitzahl()), c, "Postleitzahl");
        c.gridy++;

        addLabel(c, new Label("Stadt"));
        addEditor(new StringEditor(reportingFacility.getStadt()), c, "Stadt");
        c.gridy++;

        addLabel(c, new Label("Telefonnummer"));
        addEditor(new StringEditor(reportingFacility.getTelefonnummer()), c, "Telefonnummer");
        c.gridy++;

        addLabel(c, new Label("Faxnummer"));
        addEditor(new StringEditor(reportingFacility.getFaxnummer()), c, "Faxnummer");
        c.gridy++;

        addLabel(c, new Label("Email"));
        addEditor(new StringEditor(reportingFacility.getEmail()), c, "Email");
        c.gridy++;

        addLabel(c, new Label("Webseite"));
        addEditor(new StringEditor(reportingFacility.getWebseite()), c, "Webseite");
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
