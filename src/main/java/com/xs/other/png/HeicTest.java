package com.xs.other.png;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;

/**
 * @author xs
 * create time:2020-09-15 00:32
 **/
public class HeicTest {
    public static void main(String[] args) throws Exception {
        File file = new File("d:/Pictures/test/real.dng");
        File target = new File("d:/Pictures/test/dng.png");
        Thumbnails.of(file).scale(1).outputFormat("png").toFile("d:/Pictures/test/dng.png");
    }
}
