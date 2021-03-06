package com.xs.other.png;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * @author xs
 * create time:2020-09-15 00:26
 **/
public class TifTest {
    public static void main(String[] args) throws Exception {
        File file = new File("d:/Pictures/test/real.tif");
//        ImageIO.write(tif, "png", new File("d:/Pictures/tif.png"));
        File target = new File("d:/Pictures/test/tif.png");
        try {
            try (InputStream is = new FileInputStream(file)) {
                try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(is)) {
                    Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInputStream);
                    if (iterator == null || !iterator.hasNext()) {
                        throw new RuntimeException("Image file format not supported by ImageIO: " + file.getAbsolutePath());
                    }


                    // We are just looking for the first reader compatible:
                    ImageReader reader = iterator.next();
                    reader.setInput(imageInputStream);

                    int numPage = reader.getNumImages(true);

                    // it uses to put new png files, close to original example n0_.tiff will be in /png/n0_0.png
                    IntStream.range(0, numPage).forEach(v -> {
                        try {
                            final BufferedImage tiff = reader.read(v);
                            ImageIO.write(tiff, "png", target);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
