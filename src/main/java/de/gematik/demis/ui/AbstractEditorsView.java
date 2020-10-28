package de.gematik.demis.ui;

import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public abstract class AbstractEditorsView extends JPanel {

  private final HashMap<LABORATORY_JSON, IValueTypeView> valueEditors = new HashMap<>();
  private final EventListenerList listenerList = new EventListenerList();
  private boolean unsaveChanges = false;

  public boolean hasUnsavedChanges() {
    return unsaveChanges;
  }

  public void setSaved() {
    if (unsaveChanges) {
      unsaveChanges = false;
      fireTabChangedEvent();
    }
  }

  protected void setUnsaved() {
    if (!unsaveChanges) {
      unsaveChanges = true;
      fireTabChangedEvent();
    }
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

  public void addChangeListener(ChangeListener changeListener) {
    listenerList.add(ChangeListener.class, changeListener);
  }

  protected void addAndConfigEditor(IValueTypeView editor, LABORATORY_JSON id) {
    editor.setExpertEditor(id.isExpertValue());
    editor.checkExpertMode();
    valueEditors.put(id, editor);
    editor.addChangeListener(
        s -> {
          setUnsaved();
        });
  }

  protected HashMap<LABORATORY_JSON, IValueTypeView> getValueEditors() {
    return valueEditors;
  }
}
