package com.github.jojo2357.util;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {
    public final int id;
    public final int width;
    public final int heigth;
    public final Dimensions dimensions;

    public Texture(String filename) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer heigth = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);
        ByteBuffer data = stbi_load("./src/assets/images/" + filename + ".png", width, heigth, comp, 4);

        this.width = width.get();
        this.heigth = heigth.get();
        this.id = glGenTextures(); // generate texture name

        glBindTexture(GL_TEXTURE_2D, this.id);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.heigth, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

        if (data == null)
            throw new NullPointerException("Image at " + "./src/assets/images/" + filename + ".png" + " may not exist");
        stbi_image_free(data);

        dimensions = new Dimensions(this.width, this.heigth);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.heigth;
    }

    public int getId() {
        return this.id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, this.id);
    }
}