package com.bytelegend;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

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
    private static class FileFilterVisitor extends SimpleFileVisitor<Path> {
        private final String extension;
        private final List<String> result;
        public FileFilterVisitor(String extension, List<String> result) {
            this.extension = extension;
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);
            String name = file.getFileName().toString().toLowerCase();
            if (name.endsWith(extension)) {
                result.add(file.toString());
            }
            return FileVisitResult.CONTINUE;
        }
    }

    /**
     * Filter files by extension. Given the directory `directory` and the extension `extension` to
     * return all files with the given extension in the directory (recursively). Hint: you can use
     * the `Files.walkFileTree()` to traverse the directory.
     */
    public static List<String> filter(Path directory, String extension) throws IOException {
        if (extension == null || extension.trim().length() < 1) throw new IOException("Extension cannot be empty!");
        String _extension = extension.trim().toLowerCase();
        if (!_extension.startsWith(".")) {
            _extension = "." + _extension;
        }
        List<String> result = new ArrayList<>();
        Files.walkFileTree(directory, new FileFilterVisitor(_extension, result));
        return result;
    }
}
