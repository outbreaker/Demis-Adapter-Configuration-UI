package de.gematik.demis;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import de.gematik.demis.ui.MainView;
import java.awt.GraphicsConfiguration;
import javax.swing.SwingUtilities;

class Start {

  static GraphicsConfiguration gc;

  public static void main(String[] args) throws Exception {
//        FlatLightLaf.install();
//        FlatArcDarkIJTheme.install();
    FlatArcIJTheme.install();
    SwingUtilities.invokeLater(() -> {
      MainView.getInstance().show();
    });

  }
}
