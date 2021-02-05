package com.github.jojo2357.rendering;

import com.github.jojo2357.rendering.typeface.Colors;
import com.github.jojo2357.util.Dimensions;
import com.github.jojo2357.util.Point;
import com.github.jojo2357.util.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ScreenManager {
    public static final Dimensions windowSize = new Dimensions(32 * 6, 32 * 6);
    private static final boolean hide = true;
    private static final Point lastPosition = new Point(0, 0);
    private static final double[] x = new double[1];
    private static final double[] y = new double[1];
    public static long window;
    public static Point screenOffset = new Point();
    private static double rot = 0;
    private static float zoom = 1;
    private static double scrolls = 0;
    private static Texture redX;

    private static boolean inited = false;

    public static void init() {
        if (inited) return;
        inited = true;
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        window = glfwCreateWindow(windowSize.getWidth(), windowSize.getHeight(), "IljaSudoku", NULL, NULL);

        if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically

        glfwSetScrollCallback(window, new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                doMouseWheel(yoffset);
            }
        });

        if (hide) {
            glfwHideWindow(window);
        } else {
            glfwShowWindow(window);
        }
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    private static void doMouseWheel(double vel) {
        scrolls += vel;
    }

    public static void SaveImage(String filename) {
        //First off, we need to access the pixel data of the Display. Without this, we don't know what to save!
        GL11.glReadBuffer(GL11.GL_FRONT);
        int width = windowSize.getWidth();
        int height = windowSize.getHeight();
        int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        //Save the screen image
        File file = new File(filename + ".png"); // The file to save to.
        String format = "png"; // Example: "PNG" or "JPG"
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int i = (x + (width * y)) * bpp;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }

        try {
            ImageIO.write(image, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tick() {
        glfwPollEvents();
    }

    public static void renderTexture(Texture text, Point point, float sizeFactor, Dimensions dimensions) {
        renderTexture(text, point, sizeFactor, 0, dimensions);
    }

    public static void renderTexture(Texture text, Point point, float sizeFactor, double rotation, Dimensions specialDimensions) {
        text.bind();
        //point = point.add(new Point(text.dimensions.getWidth()/2, text.dimensions.getHeight()/2));
        double offset = Math.toDegrees(Math.atan(specialDimensions.getHeight() / (double) specialDimensions.getWidth())) - 45;
        glEnable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f((float) zoom * convertToScreenCoord(myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.sin((Math.PI / 180.0) * (-45 + rotation + offset)) + point.getX()), windowSize.getWidth()), zoom * -(float) convertToScreenCoord(myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.cos((Math.PI / 180.0) * (-45 + rotation + offset)) + point.getY()), windowSize.getHeight()));
        glTexCoord2f(1f, 0);
        glVertex2f((float) zoom * convertToScreenCoord(myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.sin((Math.PI / 180.0) * (45 + rotation - offset)) + point.getX()), windowSize.getWidth()), zoom * -(float) convertToScreenCoord(myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.cos((Math.PI / 180.0) * (45 + rotation - offset)) + point.getY()), windowSize.getHeight()));
        glTexCoord2f(1f, -1f);
        glVertex2f((float) zoom * convertToScreenCoord(myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.sin((Math.PI / 180.0) * (135 + rotation + offset)) + point.getX()), windowSize.getWidth()), zoom * -(float) convertToScreenCoord(myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.cos((Math.PI / 180.0) * (135 + rotation + offset)) + point.getY()), windowSize.getHeight()));
        glTexCoord2f(0, -1f);
        glVertex2f((float) zoom * convertToScreenCoord(myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.sin((Math.PI / 180.0) * (225 + rotation - offset)) + point.getX()), windowSize.getWidth()), zoom * -(float) convertToScreenCoord(myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.cos((Math.PI / 180.0) * (225 + rotation - offset)) + point.getY()), windowSize.getHeight()));
        glEnd();
        glDisable(GL_TEXTURE_2D);
        /*Point a = new Point((float) myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.sin((Math.PI / 180.0) * (-45 + rotation + offset)) + point.getX() - ScreenManager.windowSize.getWidth()) / ScreenManager.windowSize.getWidth(), -(float) myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.cos((Math.PI / 180.0) * (-45 + rotation + offset)) + point.getY()  - ScreenManager.windowSize.getHeight()) / ScreenManager.windowSize.getHeight());
        Point b = new Point((float) myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.sin((Math.PI / 180.0) * (45 + rotation - offset)) + point.getX()   - ScreenManager.windowSize.getWidth()) / ScreenManager.windowSize.getWidth(), -(float) myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.cos((Math.PI / 180.0) * (45 + rotation - offset)) + point.getY() - ScreenManager.windowSize.getHeight()) / ScreenManager.windowSize.getHeight());
        Point c = new Point((float) myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.sin((Math.PI / 180.0) * (135 + rotation + offset)) + point.getX()  - ScreenManager.windowSize.getWidth()) / ScreenManager.windowSize.getWidth(), -(float) myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.cos((Math.PI / 180.0) * (135 + rotation + offset)) + point.getY()- ScreenManager.windowSize.getHeight()) / ScreenManager.windowSize.getHeight());
        Point d = new Point((float) myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.sin((Math.PI / 180.0) * (225 + rotation - offset)) + point.getX()  - ScreenManager.windowSize.getWidth()) / ScreenManager.windowSize.getWidth(), -(float) myRounder(sizeFactor * specialDimensions.getDiagonal() * Math.cos((Math.PI / 180.0) * (225 + rotation - offset)) + point.getY() - ScreenManager.windowSize.getHeight()) / ScreenManager.windowSize.getHeight());
        System.out.println(a + " " + b + " " + c + " " + d);*/
    }

    private static float convertToScreenCoord(float pointIn, float dimension) {
        return (pointIn - dimension) / dimension;
    }

    private static float myRounder(double in) {
        if (Math.abs(in) % 1 < 0.01) return (float) Math.floor(in);
        if (Math.abs(in) % 1 > 0.99) return (float) Math.ceil(in);
        return (float) in;
    }

    public static void finishRender() {
        glfwSwapBuffers(window);
    }

    public static void renderTexture(Texture text, Point point) {
        renderTexture(text, point, 1);
    }

    public static void renderTexture(Texture text, Point point, float sizeFactor) {
        renderTexture(text, point, sizeFactor, 0, new Dimensions(text.getWidth(), text.getHeight()));
    }

    public static void renderTexture(Texture text, Point point, float sizeFactor, float rotation) {
        renderTexture(text, point, sizeFactor, rotation, new Dimensions(text.getWidth(), text.getHeight()));
    }

    public static void renderTextureWithOverlay(Texture image, Point currentSpot, float size, Colors color) {
        GL11.glColor4f(color.R / 255f, color.G / 255f, color.B / 255f, 255);
        renderTexture(image, currentSpot, size);
        GL11.glColor4f(255, 255, 255, 255);
    }
}
