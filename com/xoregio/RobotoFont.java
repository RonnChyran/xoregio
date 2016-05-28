package com.xoregio;

import java.awt.*;
import java.io.File;

/**
 * Represents the 'Roboto' font
 */
//todo put this in main but im too done to do this at the moment.
public class RobotoFont
{
    /**
     * The static instance of the Roboto font
     */
    public static Font ROBOTO_FONT = RobotoFont.getFont();

    private static Font getFont()
    {
        try
        {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("roboto.ttf"));
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(RobotoFont.ROBOTO_FONT);
            return font;
        }
        catch (Exception e)
        {
            return null;
        }
    }


}
