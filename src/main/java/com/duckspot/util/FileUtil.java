package com.duckspot.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author peter
 */
public class FileUtil {

    private static final Set emptySet = new HashSet();
    
    /**
     * Deletes all the files in the given directory.  Sub-directories are not
     * traversed, and the directory is not deleted.
     *
     * @param dir
     */
    public static void deleteFilesInDirectory(Path dir) throws IOException {

        Files.walkFileTree(dir, emptySet, 1, new SimpleFileVisitor<Path>() {
            
            @Override
            public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException {

                if (attrs.isRegularFile()) {
                    Files.delete(file);
                }
                return CONTINUE;
            }        
        });
    }
    
    /**
     * Deletes a directory and all the files and subdirectories inside it.
     *
     * @param dir
     */
    public static void deleteDirectory(Path dir) throws IOException {

        if (!Files.exists(dir)) {
            return;
        }
        Files.walkFileTree(dir, emptySet, 
                Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
            
            @Override
            public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException {

                if (attrs.isRegularFile()) {
                    Files.delete(file);
                }
                return CONTINUE;
            }
            
            @Override
            public FileVisitResult postVisitDirectory(Path dir,
                                 IOException exc)
                                   throws IOException {
                Files.delete(dir);
                return CONTINUE;
            }
        });
    }
    
    public static void checkDirectory(Path path) {
        
        if (Files.exists(path)) {
            if (!Files.isDirectory(path)) {
                throw new Error("'"+path+"' must be a directory");
            }
        } else {
            try {
                Files.createDirectories(path);
            } catch (IOException ex) {
                throw new Error("can't create directory '"+path+"'", ex);
            }
        }
    }
    
    /**
     * Moves all files from the fromDir to the toDir.  Fails on first file 
     * already exists at toDir.
     *
     * @param fromDir
     * @param toDir
     */
    public static void moveFiles(Path fromDir, final Path toDir) throws IOException {

        checkDirectory(toDir);
        Files.walkFileTree(fromDir, emptySet, 1, new SimpleFileVisitor<Path>() {
            
            @Override
            public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException {

                if (attrs.isRegularFile()) {
                    Files.move(file, toDir.resolve(file.getFileName()));                    
                }
                return CONTINUE;
            }        
        });
    }
}
