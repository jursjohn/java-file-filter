package com.bytelegend;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FileFilter {
    public static void main(String[] args) throws IOException {
        Path projectDir = Paths.get(System.getProperty("user.dir"));
        Path testRootDir = projectDir.resolve("test-root");
        if (!testRootDir.toFile().isDirectory()) {
            throw new IllegalStateException(testRootDir.toAbsolutePath() + " is not a directory!");
        }

        List<String> filteredFileNames = filter(testRootDir, "csv");
        System.out.println(filteredFileNames);
    }

    /**
     * Filter files by extension. Given the directory `directory` and the extension `extension` to
     * return all files with the given extension in the directory (recursively). Hint: you can use
     * the `Files.walkFileTree()` to traverse the directory.
     */
    public static List<String> filter(Path directory, String extension) throws IOException {
        List<String> res = new LinkedList<>();
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith("." + extension)) {
                    res.add(file.toFile().getAbsolutePath());
                }
                return super.visitFile(file, attrs);
            }
        });
        return res;
    }
}
