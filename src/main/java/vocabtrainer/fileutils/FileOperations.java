package vocabtrainer.fileutils;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by RaghuTeja on 3/24/16.
 */
public class FileOperations {
    public static URL readFile(String resourceName) {

        try {
            ClassLoader.getSystemClassLoader().getResource(resourceName);
        } catch (IOError ex) {
            System.out.println("Resource cannot be loaded");
        }

        URL fileName = ClassLoader.getSystemClassLoader().getResource(resourceName);
        return fileName;
    }

    public static void unZipFile(URL zipName) {
        byte[] buffer = new byte[1024];


    }
}
