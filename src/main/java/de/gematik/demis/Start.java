package de.gematik.demis;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import de.gematik.demis.ui.MainView;

import javax.swing.*;
import java.awt.*;

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
