package de.gematik.demis.ui;

import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JScrollPane;

public abstract class AbstractConfigurationView extends AbstractEditorsView
    implements IConfigurationView {

  private JScrollPane jScrollPane;

  public Component getComponent() {
    if (jScrollPane == null) {
      jScrollPane = createJScrollPane(this);
    }
    return jScrollPane;
  }

  private JScrollPane createJScrollPane(Component comp) {
    // TODO UI Workaround
    JScrollPane jScrollPane = new JScrollPane(comp);
    final boolean[] wheel = {false};
    jScrollPane.addMouseWheelListener(mouseWheelEvent -> wheel[0] = true);
    jScrollPane.addMouseMotionListener(
        new MouseMotionAdapter() {

          @Override
          public void mouseMoved(MouseEvent mouseEvent) {
            if (wheel[0]) {
              MainView.getInstance().getJTabs().setVisible(false);
              MainView.getInstance().getJTabs().setVisible(true);
            }
            wheel[0] = false;
          }
        });
    jScrollPane
        .getVerticalScrollBar()
        .addAdjustmentListener(
            new AdjustmentListener() {
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
}
