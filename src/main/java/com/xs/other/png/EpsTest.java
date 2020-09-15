package com.xs.other.png;

import org.ghost4j.document.PDFDocument;
import org.ghost4j.document.PSDocument;
import org.ghost4j.renderer.SimpleRenderer;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author xs
 * create time:2020-09-15 00:52
 **/
public class EpsTest {
    public static void main(String[] args) throws Exception {
        PSDocument document = new PSDocument();
        document.load(new File("d:/Pictures/test/real.eps"));
        // create renderer
        SimpleRenderer renderer = new SimpleRenderer();

        // set resolution (in DPI)
        renderer.setResolution(300);

        // render
        List<Image> images = renderer.render(document);

        // write images to files to disk as PNG
        try {
            for (int i = 0; i < images.size(); i++) {
                RenderedImage image = (RenderedImage) images.get(i);
                System.out.println(image.getHeight() +":" + image.getWidth());
                System.out.println(image.getMinX() + ":" + image.getMinY());
                System.out.println(image.getMinTileX() + ":" + image.getMinTileY());
                ImageIO.write(image, "png", new File("d:/Pictures/test/eps.png"));
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }
}
