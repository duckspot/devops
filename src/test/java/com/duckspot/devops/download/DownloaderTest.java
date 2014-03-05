/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.duckspot.devops.download;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peter
 */
public class DownloaderTest {
    
    public DownloaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class Downloader.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        Downloader result = Downloader.getInstance();
        assertTrue(result instanceof Downloader);        
    }

    /**
     * Test environment
     */
    @Test
    public void testEnvironment() {
        String devPath = System.getenv("DEV_PATH");
        assertTrue(devPath.substring(1).equals(":\\dev"));
    }
        
    /**
     * Test environment
     */
    @Test
    public void testWritable() throws IOException {
        String devPath = System.getenv("DEV_PATH");
        Path folder = Paths.get(devPath,"var","devops","git");
        Files.createDirectories(folder);
        Path file = folder.resolve("text.txt");
        Files.write(file, "test".getBytes());
    }
    
    /**
     * Test of queue method, of class Downloader.
     */
    @Test
    public void testQueue() throws MalformedURLException, IOException {
        System.out.println("queue");
        URL src = new URL("http://google.com");        
        Path folder = Paths.get(System.getenv("DEV_PATH"),"var","devops","test");
        Files.createDirectories(folder);
        Path dst = folder.resolve("test.html");
        Download download = new Download(dst, src);
        Downloader instance = Downloader.getInstance();
        instance.request(download);
        assertTrue(Files.exists(dst));
    }

    /**
     * Test of waitForDownloads method, of class Downloader.
     */
    @Test
    public void testWaitForDownloads() {
        System.out.println("waitForDownloads");
        Downloader instance = Downloader.getInstance();
        instance.waitForDownloads();
    }
    
}
