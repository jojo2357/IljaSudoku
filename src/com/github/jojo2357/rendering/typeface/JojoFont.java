package com.github.jojo2357.rendering.typeface;

import com.github.jojo2357.Main;

public class JojoFont {
    private static boolean inited = false;

    private static final FontCharacter[] fontCharacters = new FontCharacter[Main.boardSize];

    public static void init() {
        if (inited)
            return;
        inited = true;
        loadNumbers();
    }

    public static void loadNumbers() {
        for (int asciiValue = 1; asciiValue <= Main.boardSize; asciiValue++) {
            fontCharacters[asciiValue - 1] = new FontCharacter("" + asciiValue);
        }
    }

    public static FontCharacter getCharacter(int charRepresentation, Colors color) {
        if (charRepresentation <= fontCharacters.length && charRepresentation >= 1)
            if (fontCharacters[charRepresentation - 1] == null)
                throw new IllegalStateException("Probs wrong color. " + charRepresentation + "_" + color.name + " DNE");
            else
                return fontCharacters[charRepresentation - 1];
        throw new IndexOutOfBoundsException("font type for " + charRepresentation + "_" + color.name + " not found");
    }
}
