package de.gematik.demis.ui.value.editor;

import de.gematik.demis.utils.ImageUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class RelativPathListEditor extends JPanel implements IValueTypeView {

  public static final String DELIMITER = ",";
  private final JList pathList;
  private final DefaultListModel<String> listModel = new DefaultListModel<>();
  private final JScrollPane relativPathListScrollPane;


  public RelativPathListEditor() {
    setLayout(new BorderLayout());
    var messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
    pathList = new JList(listModel);
    relativPathListScrollPane = new JScrollPane(pathList);
    this.add(relativPathListScrollPane, BorderLayout.CENTER);
    JPanel buttonPanel = createButtonPanel(messages);
    this.add(buttonPanel, BorderLayout.EAST);
  }

  private JPanel createButtonPanel(ResourceBundle messages) {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BorderLayout());

    JButton removeJb = new JButton(messages.getString("BUTTON_REMOVE"));
    removeJb.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        var selectedValuesList = pathList.getSelectedValuesList();
        selectedValuesList.forEach(listModel::removeElement);
        pathList.revalidate();
      }
    });
    buttonPanel.add(removeJb, BorderLayout.NORTH);
    JButton addJb = new JButton(messages.getString("BUTTON_ADD"));
    addJb.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        RelativPathEditor relativPathEditor = new RelativPathEditor();
        relativPathEditor.setPreferredSize(new Dimension(500, 25));
        int okCxl = JOptionPane
            .showConfirmDialog(SwingUtilities.getWindowAncestor(RelativPathListEditor.this),
                relativPathEditor, messages.getString("INPUT_PATH"), JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, ImageUtils.loadResizeImage("Folder-icon", 35));
        if (okCxl == JOptionPane.OK_OPTION) {
          listModel.addElement(relativPathEditor.getValue());
          pathList.revalidate();
        }
      }
    });
    buttonPanel.add(addJb, BorderLayout.SOUTH);
    return buttonPanel;
  }

  @Override
  public String getValue() {
    return Arrays.stream(listModel.toArray()).map(Object::toString)
        .collect(Collectors.joining(DELIMITER));
  }

  @Override
  public void setValue(String value) {
    Arrays.stream(value.split(DELIMITER)).forEach(listModel::addElement);
  }

  @Override
  public JComponent getViewComponent() {
    return this;
  }

}
