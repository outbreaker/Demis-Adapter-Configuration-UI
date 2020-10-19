package de.gematik.demis.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gematik.demis.entities.Laboratory;
import de.gematik.demis.entities.VALUE_TYPE;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import de.gematik.demis.ui.value.editor.StringEditor;
import de.gematik.demis.ui.value.editor.StringListEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class LaboratoryView extends JPanel {
    private static Logger LOG = LoggerFactory.getLogger(LaboratoryView.class.getName());
    private final HashMap<String, IValueTypeView> values = new HashMap<>();

    public LaboratoryView(Path path) {
        initComponents(path.toFile());
    }

    private void initComponents(File file) {
        LOG.debug("Laboratory File to load: " + file.getAbsolutePath());
        Laboratory laboratory;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            laboratory = objectMapper.readValue(file, Laboratory.class);
        } catch (IOException e) {
            LOG.error("Failed to load Laboratory JSON: '" + file.getAbsolutePath() + "'", e);
            throw new RuntimeException("Failed to load Laboratory JSON: '" + file.getAbsolutePath() + "'", e);
        }

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                LaboratoryView.this.invalidate();
                LaboratoryView.this.revalidate();
                LaboratoryView.this.repaint();
            }

            @Override
            public void componentMoved(ComponentEvent componentEvent) {

            }

            @Override
            public void componentShown(ComponentEvent componentEvent) {

            }

            @Override
            public void componentHidden(ComponentEvent componentEvent) {

            }
        });
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        addLabel(c, new Label("identifikator"));
        addEditor(new StringEditor(laboratory.getIdentifikator()), c, "identifikator");
        c.gridy++;
        addLabel(c, new Label("positiveTestergebnisBezeichnungen"));
        addEditor(new StringListEditor(laboratory.getPositiveTestergebnisBezeichnungen()), c, "positiveTestergebnisBezeichnungen");
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.BOTH;

        this.add(new ReportingPersonView(laboratory.getMelderPerson()), c);
        c.gridy++;
        this.add(new ReportingFacilityView(laboratory.getMelderEinrichtung()), c);

        c.gridy++;
        this.add(new IdentityProviderView(laboratory.getIdp()), c);


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

    private Component createComponent(VALUE_TYPE valueType) {
        switch (valueType) {
            case BOOLEAN:
                return new JCheckBox();
            default:
                return new JLabel(valueType.toString());
        }
    }

}
