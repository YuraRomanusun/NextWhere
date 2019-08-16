package com.puma.nextwhere.imagecompressor;

import android.graphics.Bitmap;
import android.net.Uri;


import com.puma.nextwhere.NextwhereApplication;

import java.io.File;
import java.util.UUID;

public class Compressor {
    private static volatile Compressor INSTANCE;
    //max width and height values of the compressed image is taken as 612x816
    private float maxWidth = 612.0f;
    private float maxHeight = 816.0f;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;
    private int quality = 80;
    private String destinationDirectoryPath;
    private String fileNamePrefix;
    private String fileName;


    private Compressor() {
        destinationDirectoryPath = NextwhereApplication.getInstance().getContext().getCacheDir().getPath()
                + File.pathSeparator + FileUtil.FILES_PATH;
    }

    public static Compressor getDefault() {
        if (INSTANCE == null) {
            synchronized (Compressor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Compressor();
                }
            }
        }
        return INSTANCE;
    }

    public File compressToFile(File file) {
        return ImageUtil.compressImage(NextwhereApplication.getInstance().getContext(),
                Uri.fromFile(file), maxWidth, maxHeight,
                compressFormat, bitmapConfig, quality, destinationDirectoryPath,
                fileNamePrefix, UUID.randomUUID().toString());
    }

    public Bitmap compressToBitmap(File file) {
        return ImageUtil.getScaledBitmap(NextwhereApplication.getInstance().getContext(),
                Uri.fromFile(file), maxWidth, maxHeight, bitmapConfig);
    }


    public static class Builder {
        private Compressor compressor;

        public Builder() {
            compressor = new Compressor();
        }

        public Builder setMaxWidth(float maxWidth) {
            compressor.maxWidth = maxWidth;
            return this;
        }

        public Builder setMaxHeight(float maxHeight) {
            compressor.maxHeight = maxHeight;
            return this;
        }

        public Builder setCompressFormat(Bitmap.CompressFormat compressFormat) {
            compressor.compressFormat = compressFormat;
            return this;
        }

        public Builder setBitmapConfig(Bitmap.Config bitmapConfig) {
            compressor.bitmapConfig = bitmapConfig;
            return this;
        }

        public Builder setQuality(int quality) {
            compressor.quality = quality;
            return this;
        }

        public Builder setDestinationDirectoryPath(String destinationDirectoryPath) {
            compressor.destinationDirectoryPath = destinationDirectoryPath;
            return this;
        }

        public Builder setFileNamePrefix(String prefix) {
            compressor.fileNamePrefix = prefix;
            return this;
        }

        public Builder setFileName(String fileName) {
            compressor.fileName = fileName;
            return this;
        }

        public Compressor build() {
            return compressor;
        }
    }
}
