package com.xs.other.png;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 * @author xiongshun
 * create-time: 2020-09-15 15:37
 */
public class RawTest {
    public static void main(String[] args) throws IOException {
        // You need to know width/height of the image
        int width = 500;
        int height = 500;

        int samplesPerPixel = 4;
        int[] bandOffsets = { 2, 1, 0, 3}; // BGRA order

        byte[] bgraPixelData = FileUtils.readFileToByteArray(new File("D:/Pictures/aibrain/real1.raw"));
//                new byte[width * height * samplesPerPixel];

        DataBuffer buffer = new DataBufferByte(bgraPixelData, bgraPixelData.length);
        WritableRaster raster = Raster.createInterleavedRaster(buffer, width, height, samplesPerPixel * width, samplesPerPixel, bandOffsets, null);

        ColorModel colorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);

        BufferedImage image = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);

        System.out.println("image: " + image); // Should print: image: BufferedImage@<hash>: type = 0 ...

        ImageIO.write(image, "PNG", new File("D:/Pictures/aibrain/raw-result.png"));
    }
}
