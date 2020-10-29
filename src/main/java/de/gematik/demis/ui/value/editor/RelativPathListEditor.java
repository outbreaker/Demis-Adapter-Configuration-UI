package de.gematik.demis.ui.value.editor;

import de.gematik.demis.utils.ImageUtils;
import de.gematik.demis.utils.PathUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RelativPathListEditor extends AbstractEditor {

  public static final String DELIMITER = ",";
  private final JList<String> pathList;
  private final DefaultListModel<String> listModel = new DefaultListModel<>();
  private final JScrollPane relativPathListScrollPane;
  private JButton addJb;
  private JButton removeJb;
  private String[] fileExtension;

  public RelativPathListEditor() {
    setLayout(new BorderLayout());
    var messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
    pathList = new JList<>(listModel);
    relativPathListScrollPane = new JScrollPane(pathList);
    this.add(relativPathListScrollPane, BorderLayout.CENTER);
    JPanel buttonPanel = createButtonPanel(messages);
    this.add(buttonPanel, BorderLayout.EAST);
  }

  public void setFileExtension(String[] fileExtension) {
    this.fileExtension = fileExtension;
  }

  private JPanel createButtonPanel(ResourceBundle messages) {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BorderLayout());

    removeJb = new JButton(messages.getString("BUTTON_REMOVE"));
    removeJb.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            var selectedValuesList = pathList.getSelectedValuesList();
            selectedValuesList.forEach(listModel::removeElement);
            fireTabChangedEvent();
            pathList.revalidate();
          }
        });
    buttonPanel.add(removeJb, BorderLayout.NORTH);
    addJb = new JButton(messages.getString("BUTTON_ADD"));
    addJb.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            RelativPathEditor relativPathEditor = new RelativPathEditor(fileExtension);
            relativPathEditor.setPreferredSize(new Dimension(500, 25));
            int okCxl =
                JOptionPane.showConfirmDialog(
                    SwingUtilities.getWindowAncestor(RelativPathListEditor.this),
                    relativPathEditor,
                    messages.getString("INPUT_PATH"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    ImageUtils.loadResizeImage("OPEN_FILE", 35));
            if (okCxl == JOptionPane.OK_OPTION) {
              listModel.addElement(relativPathEditor.getValue());
              fireTabChangedEvent();
              pathList.revalidate();
            }
          }
        });
    buttonPanel.add(addJb, BorderLayout.SOUTH);
    removeJb.setEnabled(!isExpertEditor());
    addJb.setEnabled(!isExpertEditor());
    return buttonPanel;
  }

  @Override
  public String getValue() {
    return Arrays.stream(listModel.toArray())
        .map(Object::toString)
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

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    listenerList.add(ChangeListener.class, changeListener);
  }

  protected void fireTabChangedEvent() {
    ChangeEvent evt = new ChangeEvent(this);
    Object[] listeners = listenerList.getListenerList();
    for (int i = 0; i < listeners.length; i = i + 2) {
      if (listeners[i] == ChangeListener.class) {
        ((ChangeListener) listeners[i + 1]).stateChanged(evt);
      }
    }
  }

  @Override
  public void checkExpertMode() {
    addJb.setEnabled(!isExpertEditor());
    removeJb.setEnabled(!isExpertEditor());
    pathList.setEnabled(!isExpertEditor());
    relativPathListScrollPane.setEnabled(!isExpertEditor());
  }

  @Override
  public void activateForExperts() {
    addJb.setEnabled(true);
    removeJb.setEnabled(true);
    pathList.setEnabled(true);
    relativPathListScrollPane.setEnabled(true);
  }

  public void addPath(Path path) {
    fireTabChangedEvent();
    listModel.addElement(PathUtils.getRelativPath(path));
  }
}
