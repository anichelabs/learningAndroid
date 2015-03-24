package com.aneeshjoshi.gridimagesearch.models;

import java.io.Serializable;

public class Setting implements Serializable {

    String size;
    int sizePos;
    String color;
    int colorPos;
    String type;
    int typePos;
    String site;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSizePos() {
        return sizePos;
    }

    public void setSizePos(int sizePos) {
        this.sizePos = sizePos;
    }

    public int getColorPos() {
        return colorPos;
    }

    public void setColorPos(int colorPos) {
        this.colorPos = colorPos;
    }

    public int getTypePos() {
        return typePos;
    }

    public void setTypePos(int typePos) {
        this.typePos = typePos;
    }
}
