package com.xs.other.png;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author xs
 * create time:2020-09-15 00:32
 **/
public class HeicTest {
    public static void main(String[] args) throws Exception {
        File[] files = new File("D:\\Pictures\\aibrain\\整理").listFiles();
        for (File file : files) {
            System.out.println("---------------------fileName=" + file.getName());
            try {
                Metadata metadata = ImageMetadataReader.readMetadata(file);
                Collection<ExifSubIFDDirectory> directoriesOfType = metadata.getDirectoriesOfType(ExifSubIFDDirectory.class);

                for (Directory directory : metadata.getDirectories()) {
                    Iterator<Tag> tags = directory.getTags().iterator();
                    while (tags.hasNext()) {
                        Tag tag = tags.next();
                        String tagStr = tag.toString();
                        if (
                                tagStr.toLowerCase().contains("image width")
//                                        || tagStr.toLowerCase().contains("image height")
//                                        ||
//                                tagStr.contains("Detected File Type Name")
                                ) {
                            System.out.println(tag);
                            System.out.println(directory.getClass().getName());
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(file.getName() + "; 不能识别");
            }
        }
    }
}
