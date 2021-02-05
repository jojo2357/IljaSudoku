package com.github.jojo2357.rendering.typeface;

import com.github.jojo2357.rendering.ScreenManager;
import com.github.jojo2357.util.Point;

public class TextRenderer {
    public static void render(String charSequenceToPrint, Point renderStart, Colors color) {
        charSequenceToPrint = charSequenceToPrint.toUpperCase();
        Point currentSpot = renderStart.copy();
        charSequenceToPrint += ' ';
        for (int i = 0; i < charSequenceToPrint.length(); i++) {
            char renderChar = charSequenceToPrint.charAt(i);
            if (renderChar != ' ') {
                ScreenManager.renderTextureWithOverlay(JojoFont.getCharacter(renderChar, Colors.WHITE).image, currentSpot, 2f, color);
            }
            currentSpot.stepX(18);
        }
    }
}
