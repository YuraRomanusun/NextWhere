package com.puma.nextwhere.model;

import java.io.File;

/**
 * Created by rajesh on 2/6/17.
 */

public class UserImageModel {
    private boolean isFile;
    private File imagePath;

    @Override
    public String toString() {
        return imagePath == null ? "" : imagePath.getAbsolutePath();
    }

    public boolean isFile() {
        return isFile;
    }

    public File getImagePath() {
        return imagePath;
    }

    public UserImageModel(boolean isFile, File imagePath) {
        this.isFile = isFile;
        this.imagePath = imagePath;
    }

}
