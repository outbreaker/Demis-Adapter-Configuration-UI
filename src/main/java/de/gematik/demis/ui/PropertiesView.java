package de.gematik.demis.ui;

import de.gematik.demis.entities.APP_Properties;
import de.gematik.demis.entities.VALUE_TYPE;
import de.gematik.demis.ui.value.editor.ValueTypeEditorFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;

public class PropertiesView extends JPanel {
    public PropertiesView() {
        initComponents();
    }

    private void initComponents() {
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                PropertiesView.this.invalidate();
                PropertiesView.this.revalidate();
                PropertiesView.this.repaint();
                System.out.println("repaint");
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
//        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(
//                new javax.swing.border.EmptyBorder(0, 0, 0, 0), "Properties"
//                , javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM
//                , new java.awt.Font("Dia\u006cog", java.awt.Font.BOLD, 12)
//                , java.awt.Color.red), getBorder()));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
//        ((GridBagLayout) getLayout()).columnWidths = new int[]{0, 0, 0};
//        ((GridBagLayout) getLayout()).rowHeights = new int[]{0, 0, 0, 0};
//        ((GridBagLayout) getLayout()).columnWeights = new double[]{0.0, 0.0, 1.0E-4};
//        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};

        c.gridy = 0;

        Arrays.stream(APP_Properties.values()).forEach(e -> {
        c.weighty = 0.1;
                    c.fill = GridBagConstraints.BOTH;
                    c.gridx = 0;
                    c.insets = new Insets(0, 10, 0, 10);  //top padding
                    c.anchor = GridBagConstraints.LAST_LINE_START;
                    c.weightx = 0;
                    this.add(new Label(e.getKey()), c);

                    c.gridx = 1;
                    c.weightx = 1.0;
                    if (e.getType() == VALUE_TYPE.STRING_LIST) {
                        c.weighty = 0.5;
                        c.fill = GridBagConstraints.BOTH;
                    }
                    this.add(ValueTypeEditorFactory.createEditor(e.getType()).getViewComponent(), c);

                    c.gridy++;

                }
        );
        this.repaint();
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
