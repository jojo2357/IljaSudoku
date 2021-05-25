package com.github.jojo2357.rendering.typeface;

import com.github.jojo2357.rendering.ScreenManager;
import com.github.jojo2357.util.Point;

public class TextRenderer {
    public static void render(int renderChar, Point renderStart, Colors color) {
        if (renderChar != -1) {
            ScreenManager.renderTextureWithOverlay(JojoFont.getCharacter(renderChar, Colors.WHITE).image, renderStart, 2f, color);
        }
    }
}
