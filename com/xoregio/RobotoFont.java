package com.xoregio;

import java.awt.*;
import java.io.File;

/**
 * Represents the 'Roboto' font
 */
//todo put this in main but im too done to do this at the moment.
public class RobotoFont {
    public static Font ROBOTO_FONT;

    static {
        try {

            RobotoFont.ROBOTO_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("roboto.ttf"));
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(RobotoFont.ROBOTO_FONT);
        } catch (Exception e) {

        }
    }
}