package de.gematik.demis.ui.value.editor;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BooleanEditor extends AbstractEditor {

  private static Logger LOG = LoggerFactory.getLogger(BooleanEditor.class.getName());
  private final JCheckBox booleanCheckBox = new JCheckBox();

  public BooleanEditor() {
    this.setLayout(new BorderLayout());
    add(booleanCheckBox, BorderLayout.CENTER);
  }

  @Override
  public String getValue() {
    return String.valueOf(booleanCheckBox.isSelected()).toLowerCase();
  }

  @Override
  public void setValue(String value) {
    try {
      booleanCheckBox.setSelected(Boolean.parseBoolean(value));
    } catch (Exception e) {
      LOG.error("Value \"" + value + "\" could not interpreted as boolean value.");
      booleanCheckBox.setSelected(false);
    }
  }

  @Override
  public JComponent getViewComponent() {
    return this;
  }

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    booleanCheckBox.addActionListener(
        l -> changeListener.stateChanged(new ChangeEvent(BooleanEditor.this)));
    booleanCheckBox.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            changeListener.stateChanged(new ChangeEvent(BooleanEditor.this));
          }
        });
  }

  @Override
  public void checkExpertMode() {
    booleanCheckBox.setEnabled(!isExpertEditor());
  }

  @Override
  public void activateForExperts() {
    booleanCheckBox.setEnabled(true);
  }
}
