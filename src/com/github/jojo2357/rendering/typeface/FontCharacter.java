package com.github.jojo2357.rendering.typeface;

import com.github.jojo2357.util.Texture;

public class FontCharacter {
    public final Texture image;

    public FontCharacter(String strRepresentation) {
        this.image = new Texture("fontassets/" + strRepresentation);
    }
}
