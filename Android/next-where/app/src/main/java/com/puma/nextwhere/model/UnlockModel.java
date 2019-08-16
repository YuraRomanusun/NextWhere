package com.puma.nextwhere.model;

/**
 * Created by rtiwari on 10/9/2017.
 */

public class UnlockModel {
    private int statusImage;
    private String content;
    private int rightImage;
    private boolean isViewSelected;

    public boolean isViewSelected() {
        return isViewSelected;
    }

    public void setViewSelected(boolean viewSelected) {
        isViewSelected = viewSelected;
    }

    public int getStatusImage() {
        return statusImage;
    }

    public void setStatusImage(int statusImage) {
        this.statusImage = statusImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRightImage() {
        return rightImage;
    }

    public void setRightImage(int rightImage) {
        this.rightImage = rightImage;
    }
}
