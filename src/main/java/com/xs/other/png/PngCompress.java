package com.xs.other.png;
import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;
import com.idrsolutions.image.png.PngCompressor;
import com.idrsolutions.image.png.PngEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class PngCompress {

    public static void main(String[] args) throws IOException {
//        File originFile = new File("/Users/xiongshun/Workspaces/IdeaProjects/leetcode-practice/src/com8bit.png");
        compressTastic();
//        PngCompressor.compress(originFile, new File("/Users/xiongshun/Workspaces/IdeaProjects/leetcode-practice/src/com8bit2.png"));
//        compressNormal(originFile, new File("/Users/xiongshun/Workspaces/IdeaProjects/leetcode-practice/src/normal.png"));
    }
    private static void compressTastic() {
        try (FileInputStream input = new FileInputStream("/Users/xiongshun/Workspaces/IdeaProjects/leetcode-practice/src/touming.png")) {
//            byte[] originImgByte = null;
//            try (ByteArrayOutputStream bos = new ByteArrayOutputStream(1024)) {
//                byte[] b = new byte[1000];
//                int n;
//                while ((n = input.read(b)) != -1) {
//                    bos.write(b, 0, n);
//                }
//                originImgByte = bos.toByteArray();
//            }
            PngOptimizer optimizer = new PngOptimizer("debug");
            optimizer.setCompressor("zopfli", 32);
            PngImage img = new PngImage(input);
            for (int i=1; i<10; i++) {
                long start = System.currentTimeMillis();
                try (FileOutputStream out = new FileOutputStream(new File("/Users/xiongshun/Workspaces/IdeaProjects/leetcode-practice/src/"+i+"-compressLevel.png"))) {
                    PngImage resultImg = optimizer.optimize(img, false, i);
                    resultImg.writeDataOutputStream(out);
                    System.out.println("compress level" + i + " over, 耗时:" + (System.currentTimeMillis() - start) + "ms");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void compressNormal(File originFile, File targetFile) {
        try (FileInputStream input = new FileInputStream(originFile);
             FileOutputStream output = new FileOutputStream(targetFile)) {
            BufferedImage image = ImageIO.read(input);
            PngEncoder encoder = new PngEncoder();
            encoder.setCompressed(false);
            encoder.write(image, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
