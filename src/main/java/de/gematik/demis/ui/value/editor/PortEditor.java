package de.gematik.demis.ui.value.editor;

import de.gematik.demis.exceptions.ParseRuntimeException;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortEditor extends AbstractEditor {

  private static Logger LOG = LoggerFactory.getLogger(PortEditor.class.getName());
  private final JFormattedTextField field;

  public PortEditor() {
    this.setLayout(new BorderLayout());
    NumberFormat format = NumberFormat.getInstance();
    format.setGroupingUsed(false);
    NumberFormatter formatter = new NumberFormatter(format);
    formatter.setValueClass(Integer.class);
    formatter.setMinimum(0);
    formatter.setMaximum(65535);
    formatter.setAllowsInvalid(false);
    formatter.setCommitsOnValidEdit(true);
    field = new JFormattedTextField(formatter);
    this.add(field, BorderLayout.CENTER);
  }

  @Override
  public String getValue() {
    if (field.getValue() == null) {
      return "";
    } else {
      return String.valueOf(field.getValue());
    }
  }

  @Override
  public void setValue(String value) {
    field.setValue(Integer.valueOf(value));
    try {
      field.commitEdit();
    } catch (ParseException e) {
      throw new ParseRuntimeException(e);
    }
  }

  @Override
  public JComponent getViewComponent() {
    return this;
  }

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    field.addKeyListener(
        new KeyAdapter() {
          @Override
          public void keyTyped(KeyEvent keyEvent) {
            changeListener.stateChanged(new ChangeEvent(PortEditor.this));
          }
        });
  }

  @Override
  public void checkExpertMode() {
    field.setEnabled(!isExpertEditor());
  }

  @Override
  public void activateForExperts() {
    field.setEnabled(true);
  }
}
