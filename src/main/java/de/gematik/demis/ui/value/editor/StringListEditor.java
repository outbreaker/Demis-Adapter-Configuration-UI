package de.gematik.demis.ui.value.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StringListEditor extends JPanel implements IValueTypeView {

    public static final String DELIMITER = ",";
    private final JList stringList;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JScrollPane stringListScrollPane;


    public StringListEditor() {
        setLayout(new BorderLayout());
        var messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
        stringList = new JList(listModel);
        stringListScrollPane = new JScrollPane(stringList);
        this.add(stringListScrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = createButtonPanel(messages);
        this.add(buttonPanel, BorderLayout.EAST);
    }

    public StringListEditor(String[] positiveTestergebnisBezeichnungen) {
        this();
        setValue(String.join(",",positiveTestergebnisBezeichnungen));
    }

    private JPanel createButtonPanel(ResourceBundle messages) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        JButton removeJb = new JButton(messages.getString("BUTTON_REMOVE"));
        removeJb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                var selectedValuesList = stringList.getSelectedValuesList();
                selectedValuesList.forEach(listModel::removeElement);
                stringList.revalidate();
            }
        });
        buttonPanel.add(removeJb, BorderLayout.NORTH);
        JButton addJb = new JButton(messages.getString("BUTTON_ADD"));
        addJb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String result = JOptionPane.showInputDialog(messages.getString("INPUT_VALUE"));
                listModel.addElement(result);
                stringList.revalidate();
            }
        });
        buttonPanel.add(addJb, BorderLayout.SOUTH);
        return buttonPanel;
    }

    @Override
    public String getValue() {
        return Arrays.stream(listModel.toArray()).map(Object::toString).collect(Collectors.joining(DELIMITER));
    }

    @Override
    public void setValue(String value) {
        listModel.clear();
        Arrays.stream(value.split(DELIMITER)).forEach(listModel::addElement);
    }

    @Override
    public JComponent getViewComponent() {
        return this;
    }

    @Override
    public void repaint() {
        super.repaint();
        Dimension size = this.getSize();
        double height = size.getHeight() - 8;
        if (height < 40) height = 40;
        double width = size.getWidth() / 2;
        if (width < 300) width = 300;
        size.setSize(width, height);
        if (stringListScrollPane != null)
            stringListScrollPane.setPreferredSize(size);

    }


}
