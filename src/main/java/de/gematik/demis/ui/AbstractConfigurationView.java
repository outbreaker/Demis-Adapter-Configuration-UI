package de.gematik.demis.ui;

import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public abstract class AbstractConfigurationView extends JPanel implements IConfigurationView {

  private EventListenerList listenerList = new EventListenerList();
  private JScrollPane jScrollPane;
  private boolean unsaveChanges = false;


  public Component getComponent() {
    if (jScrollPane == null) {
      jScrollPane = createJScrollPane(this);
    }
    return jScrollPane;
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

  private JScrollPane createJScrollPane(Component comp) {
    //TODO UI Workaround
    JScrollPane jScrollPane = new JScrollPane(comp);
    final boolean[] wheel = {false};
    jScrollPane.addMouseWheelListener(mouseWheelEvent -> wheel[0] = true);
    jScrollPane.addMouseMotionListener(new MouseMotionAdapter() {

      @Override
      public void mouseMoved(MouseEvent mouseEvent) {
        if (wheel[0]) {
          MainView.getInstance().getJTabs().setVisible(false);
          MainView.getInstance().getJTabs().setVisible(true);
        }
        wheel[0] = false;
      }
    });
    jScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getAdjustmentType() == AdjustmentEvent.TRACK) {
          e.getAdjustable().setUnitIncrement(500);
          e.getAdjustable().setBlockIncrement(500);
          comp.repaint();
          comp.revalidate();
          MainView.getInstance().getMainComponent().repaint();
        }
      }
    });

    return jScrollPane;
  }


  @Override
  public boolean hasUnsavedChanges() {
    return unsaveChanges;
  }

  @Override
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

}
