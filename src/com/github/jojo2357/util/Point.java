package com.github.jojo2357.util;

public class Point {
    private float x;
    private float y;

    public Point() {
        this(0, 0);
    }

    public Point(int x, int y) {
        this((float) x, (float) y);
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y) {
        this((float) x, (float) y);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Point)) return false;
        return ((Point) other).x == this.x && ((Point) other).y == this.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + " ," + this.y + ")";
    }

    public void stepX(int stepAmt) {
        this.x += stepAmt;
    }

    public Point copy() {
        return new Point(this.x, this.y);
    }

    public Point multiply(float factor) {
        this.x *= factor;
        this.y *= factor;
        return this;
    }
}
