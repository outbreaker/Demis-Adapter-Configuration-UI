package de.gematik.demis;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import de.gematik.demis.ui.MainView;

import java.awt.*;

class Start {
    static GraphicsConfiguration gc;

    public static void main(String[] args) throws Exception {
//        FlatLightLaf.install();
//        FlatArcDarkIJTheme.install();
        FlatArcIJTheme.install();
        MainView.getInstance().show();
    }
}
