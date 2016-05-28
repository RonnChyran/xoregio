package com.xoregio;

import java.awt.*;
import java.io.File;

/**
 * Created by Ronny on 2016-05-27.
 */
//todo put this in main
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
