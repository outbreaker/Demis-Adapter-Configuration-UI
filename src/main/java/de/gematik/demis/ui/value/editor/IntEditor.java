package de.gematik.demis.ui.value.editor;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntEditor extends JPanel implements IValueTypeView {

  private static Logger LOG = LoggerFactory.getLogger(IntEditor.class.getName());
  private JFormattedTextField field;

  public IntEditor() {
    this.setLayout(new BorderLayout());
    NumberFormat format = NumberFormat.getInstance();
    NumberFormatter formatter = new NumberFormatter(format);
    formatter.setValueClass(Integer.class);
    formatter.setMinimum(0);
    formatter.setMaximum(Integer.MAX_VALUE);
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
  }

  @Override
  public JComponent getViewComponent() {
    return this;
  }

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    field.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent keyEvent) {
        changeListener.stateChanged(new ChangeEvent(IntEditor.this));
      }
    });
  }
}
