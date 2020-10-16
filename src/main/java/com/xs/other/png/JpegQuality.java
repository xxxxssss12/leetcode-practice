package com.xs.other.png;

import com.twelvemonkeys.imageio.metadata.jpeg.JPEGQuality;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;

public class JpegQuality {

    public static void main(String[] args) throws Exception {
        String format = "/Users/xiongshun/Downloads/r1-%s.jpg";
        String formatResult = "/Users/xiongshun/Downloads/r2-%s.jpg";
        for (int i=1; i<=9; i++) {
            String filePath = String.format(format, i);
            String outFile = String.format(formatResult, i);
            File file = new File(filePath);
            ImageInputStream inputStream = ImageIO.createImageInputStream(file);
            float jpegQuality = JPEGQuality.getJPEGQuality(inputStream);
            if (jpegQuality > 0) {
                Thumbnails.of(file).outputFormat("jpg").outputQuality(jpegQuality).scale(1).toFile(outFile);
            } else {
                Thumbnails.of(file).outputFormat("jpg").outputQuality(1).scale(1).toFile(outFile);
            }
            System.out.println(filePath + ":" + jpegQuality);
        }
    }
}
