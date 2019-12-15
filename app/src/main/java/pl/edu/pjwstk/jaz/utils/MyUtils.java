package pl.edu.pjwstk.jaz.utils;

import net.bytebuddy.utility.RandomString;
import pl.edu.pjwstk.jaz.auction.AuctionEntity;

import java.io.*;
import java.util.Arrays;

public class MyUtils {
    public static String joinPaths(String... args) {
        StringBuilder result = new StringBuilder();

        for (String s : args) {
            if (result.length() != 0)
                if (s.charAt(0) == '/')
                    s = s.substring(1);
            result.append(s);
            if (s.charAt(s.length() - 1) != '/')
                result.append('/');
        }

        return result.toString().substring(0, result.length() - 1);
    }

    final public static String APPLICATION_PATH = "/home/jan/Documents/JAZ/login-app";
    final public static String RESOURCES_PATH = joinPaths(APPLICATION_PATH, "app/src/main/webapp/resources");

    static public boolean fileExists(String path) {
        return new File(path).exists();
    }

    static public void removeFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File doesn't exist - " + path);
            return;
        }
        if (file.delete())
            System.out.println("File deleted successfully - " + file.getName());
        else
            System.out.println("Failed to delete the file - " + file.getName());
    }

    static public String randomName(int length, String extension) {
        if (extension.charAt(0) != '.')
            extension = "." + extension;
        return RandomString.make(length - extension.length()) + extension;
    }

    static public String getFileContents(String path) throws IOException {
        StringBuilder result = new StringBuilder();
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        while ((line = br.readLine()) != null)
            result.append(line);

        return result.toString();
    }

}
