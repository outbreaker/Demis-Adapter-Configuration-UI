package de.gematik.demis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageUtils {
    private static Logger LOG = LoggerFactory.getLogger(ImageUtils.class.getName());

    private ImageUtils() {
    }

    public static ImageIcon loadResizeImage(String name, int size){
        URL resource = ImageUtils.class.getResource("/icons/" + name + ".png");
        if (resource == null) {
            LOG.warn("No Icon File for \"" + name + ".png\" found!");
            return null;
        }
        else {
            return new ImageIcon(new ImageIcon(resource).getImage().getScaledInstance(size, size, Image.SCALE_AREA_AVERAGING));
        }

    }
}
