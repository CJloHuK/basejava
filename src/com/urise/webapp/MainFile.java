package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
//        String filePath = ".\\.gitignore";
//
//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }

        File dir = new File("./src/com/urise/webapp/test");
        //System.out.println(dir.isDirectory());
        if (dir.isDirectory()) {
            printFiles(dir, "");
        }

//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void printFiles(File dir, String text) {
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                System.out.println(text + file.getName());
                if(file.isDirectory()) {
                    printFiles(file, text + file.getName() + "/");
                }
            }
        }
    }
}
