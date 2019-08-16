package com.puma.nextwhere.model;

/**
 * Created by rajesh on 31/5/17.
 */

public class MenuActivity_Model {
    private String title;
    private int drawableIcon;
    private int drawableSelectedIcon;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public MenuActivity_Model(String title, int drawableIcon, int drawableSelectedIcon) {
        this.title = title;
        this.drawableIcon = drawableIcon;
        this.drawableSelectedIcon = drawableSelectedIcon;
    }

    public int getDrawableSelectedIcon() {
        return drawableSelectedIcon;
    }

    public String getTitle() {
        return title;
    }

    public int getDrawableIcon() {
        return drawableIcon;
    }

}
