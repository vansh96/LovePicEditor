package com.example.vaksys_android.lovepiceditor.Extras.viewmodel;

public class Font {


    private int color;

    private String typeface;

    private float size;

    public Font() {
    }
    public void increaseSize(float diff) {
        size = size + diff;
    }

    public void decreaseSize(float diff) {
        if (size - diff >= Limits.MIN_FONT_SIZE) {
            size = size - diff;
        }
    }
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTypeface() {
        return typeface;
    }

    public void setTypeface(String typeface) {
        this.typeface = typeface;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    private interface Limits {
        float MIN_FONT_SIZE = 0.01F;
    }
}