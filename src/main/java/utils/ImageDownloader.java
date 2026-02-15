package utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class ImageDownloader {

    public static void downloadImage(String imageUrl, String fileName) {

        try {
            if (imageUrl == null || imageUrl.isEmpty()) {
                LoggerUtil.log("No image URL provided");
                return;
            }

            String timestamp = String.valueOf(System.currentTimeMillis());

            URL url = new URL(imageUrl);
            InputStream is = url.openStream();

            File file = new File("images/" + fileName + "_" + timestamp + ".jpg");
            file.getParentFile().mkdirs();

            FileUtils.copyInputStreamToFile(is, file);

            LoggerUtil.log("Image saved: " + file.getAbsolutePath());

        } catch (Exception e) {
            LoggerUtil.log("Failed to download image");
        }
    }
}
