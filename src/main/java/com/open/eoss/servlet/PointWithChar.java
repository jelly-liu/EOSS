package com.open.eoss.servlet;

public class PointWithChar {
    private String character;
    private int x;
    private int y;
    private int fontSize;

    public PointWithChar(String character, int x, int y, int fontSize) {
        this.character = character;
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
    }

    public String getCharacter() {
        return character;
    }

    public PointWithChar setCharacter(String character) {
        this.character = character;
        return this;
    }

    public int getX() {
        return x;
    }

    public PointWithChar setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public PointWithChar setY(int y) {
        this.y = y;
        return this;
    }

    public int getFontSize() {
        return fontSize;
    }

    public PointWithChar setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }
}
