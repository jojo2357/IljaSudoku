package com.github.jojo2357.rendering.typeface;

public class JojoFont {
    private static boolean inited = false;

    private static final FontCharacter[] fontCharacters = new FontCharacter[6];

    public static void init() {
        if (inited)
            return;
        inited = true;
        loadNumbers();
    }

    public static void loadNumbers() {
        for (char asciiValue = 1; asciiValue <= 6; asciiValue++) {
            fontCharacters[asciiValue - 1] = new FontCharacter((char)(asciiValue + 48));
        }
    }

    public static FontCharacter getCharacter(char charRepresentation, Colors color) {
        if (charRepresentation <= '6' && charRepresentation >= '1')
            if (fontCharacters[charRepresentation - 49] == null)
                throw new IllegalStateException("Probs wrong color. " + charRepresentation + "_" + color.name + " DNE");
            else
                return fontCharacters[charRepresentation - 49];
        throw new IndexOutOfBoundsException("font type for " + charRepresentation + "_" + color.name + " not found");
    }
}
