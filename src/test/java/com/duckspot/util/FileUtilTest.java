package com.duckspot.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileUtilTest {
    
    static final Path dir1 = Paths.get("testDir");
    static final Path file1 = dir1.resolve("file1");
    static final Path dir2 = dir1.resolve("dir2");
    static final Path file2 = dir2.resolve("file2");
    
    public FileUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
        FileUtil.deleteDirectory(dir1);
    }
    
    @After
    public void tearDown() throws IOException {
        FileUtil.deleteDirectory(dir1);
    }

    private void create() throws IOException {
        Files.createDirectory(dir1);
        Files.createDirectory(dir2);
        Files.write(file1, "file1".getBytes());
        Files.write(file2, "file2".getBytes());
    }
    
    /**
     * Test of deleteFilesInDirectory method, of class FileUtil.
     */
    @Test
    public void testDeleteFilesInDirectory() throws IOException {
        System.out.println("deleteFilesInDirectory");
        create();
        FileUtil.deleteFilesInDirectory(dir1);
        assertTrue(Files.exists(dir1));
        assertTrue(Files.exists(dir2));
        assertFalse(Files.exists(file1));
        assertTrue(Files.exists(file2));
        FileUtil.deleteDirectory(dir1);
    }

    /**
     * Test of deleteDirectory method, of class FileUtil.
     */
    @Test
    public void testDeleteDirectory() throws Exception {
        System.out.println("deleteDirectory");
        create();
        assertTrue(Files.exists(dir1));
        assertTrue(Files.exists(dir2));
        assertTrue(Files.exists(file1));
        assertTrue(Files.exists(file2));
        FileUtil.deleteDirectory(dir1);
        assertFalse(Files.exists(file2));
        assertFalse(Files.exists(dir2));
        assertFalse(Files.exists(file1));
        assertFalse(Files.exists(dir1));
        FileUtil.deleteDirectory(dir1);
    }    

    /**
     * Test of moveFiles method, of class FileUtil.
     */
    @Test
    public void testMoveFiles() throws Exception {
        System.out.println("moveFiles");
        create();
        Path fromDir = dir2;
        Path toDir = dir1;
        FileUtil.moveFiles(fromDir, toDir);
        assertTrue(Files.exists(dir1));
        assertTrue(Files.exists(dir2));
        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));
        Path file2in1 = dir1.resolve("file2");
        Path file1in2 = dir2.resolve("file1");
        assertFalse(Files.exists(file1in2));
        assertTrue(Files.exists(file2in1));
        FileUtil.deleteDirectory(dir1);
    }

    /**
     * Test of checkDirectory method, of class FileUtil.
     */
    @Test
    public void testCheckDirectory() {
        System.out.println("checkDirectory");
        Path path = dir1;
        FileUtil.checkDirectory(path);
        assertTrue(Files.exists(path));
        assertTrue(Files.isDirectory(path));
    }
}
