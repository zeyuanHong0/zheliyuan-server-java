package org.zly.blogbackjava.util;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static String getFileName(MultipartFile file){
        String uniqueSuffix = System.currentTimeMillis() + "-" + Math.round(Math.random() * 1e9);
        String ext = getFileExtension(file.getOriginalFilename());
        return uniqueSuffix + ext;
    }

    public static String getFileExtension(String filename){
        if (filename == null) {
            return "";
        }

        int lastSeparator = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex <= lastSeparator + 1) {
            return "";
        }

        return filename.substring(dotIndex);
    }
}
