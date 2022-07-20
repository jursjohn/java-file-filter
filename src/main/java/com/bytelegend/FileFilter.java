package com.bytelegend;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileFilter extends SimpleFileVisitor<Path> {
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
        PrintFiles pf = new PrintFiles(extension);
        Files.walkFileTree(directory, pf);
        return pf.filesMatchingExtension();
    }

    public static class PrintFiles
            extends SimpleFileVisitor<Path> {

        private final String extension;
        private final ArrayList<String> files;

        public PrintFiles(String extension) {
            this.extension = extension;
            this.files = new ArrayList<>();
        }

        public ArrayList<String> filesMatchingExtension() {
            return this.files;
        }
        // Print information about
        // each type of file.
        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attr) {
            String[] fileName = file.getFileName().toString().split("\\.");
            if (fileName[1].equals(this.extension)) {
                files.add(file.getFileName().toString());
            }
            return FileVisitResult.CONTINUE;
        }

        // Print each directory visited.
        @Override
        public FileVisitResult postVisitDirectory(Path dir,
                                                  IOException exc) {
//            System.out.format("Directory: %s%n", dir);
            return FileVisitResult.CONTINUE;
        }

        // If there is some error accessing
        // the file, let the user know.
        // If you don't override this method
        // and an error occurs, an IOException
        // is thrown.
        @Override
        public FileVisitResult visitFileFailed(Path file,
                                               IOException exc) {
//            System.err.println(exc);
            return FileVisitResult.CONTINUE;
        }
    }
}
