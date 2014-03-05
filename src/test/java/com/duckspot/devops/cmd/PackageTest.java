package com.duckspot.devops.cmd;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PackageTest {
    
    public PackageTest() {
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
     * Test of packageURL method, of class Package.
     */
    @Test
    public void testPackageURL() throws MalformedURLException {
        System.out.println("packageURL");
        String packageName = "git";
        URL expResult = new URL("https://raw.github.com/duckspot/devops"
                + "/master/src/main/resources/packages/git.json");
        URL result = Package.packageURL(packageName);
        assertEquals(expResult, result);
    }

    /**
     * Test of getJSON method, of class Package.
     */
    @Test
    public void testGetJSON() throws Exception {
        System.out.println("getJSON");
        URL url = new URL("https://raw.github.com/duckspot/devops"
                + "/master/src/main/resources/packages/git.json");
        JSONObject result = Package.getJSON(url);
        assertEquals("git", result.getString("package"));
    }

    /**
     * Test of getConfig method, of class Package.
     */
    @Test
    public void testGetConfig() throws Exception {
        System.out.println("getConfig");
        String packageName = "git";
        Package instance = new Package(packageName);
        String expResult = packageName;
        JSONObject result = instance.getConfig();
        assertEquals(expResult, result.getString("package"));
    }

    /**
     * Test of getDownloads method, of class Package.
     */
    @Test
    public void testGetDownloads() throws Exception {
        System.out.println("getDownloads");
        String packageName = "git";
        Package instance = new Package(packageName);
        String[] expResult = {"https://github.com/duckspot/devops/releases"
                + "/download/v2014-03-03/Git.zip"};
        String[] result = instance.getDownloads();
        assertArrayEquals(expResult, result);        
    }

    /**
     * Test of getDevPath method, of class Package.
     */
    @Test
    public void testGetDevPath() {
        System.out.println("getDevPath");
        String packageName = "git";
        Package instance = new Package(packageName);
        String expResult = "H:\\dev";
        String result = instance.getDevPath();
        assertEquals(expResult, result);        
    }

    /**
     * Test of getLocalName method, of class Package.
     */
    @Test
    public void testGetLocalName() {
        System.out.println("getLocalName");
        String download = "https://github.com/duckspot/devops/releases"
                + "/download/v2014-03-03/Git.zip";
        String packageName = "git";
        Package instance = new Package(packageName);
        String expResult = "H:\\dev\\var\\devops\\git\\Git.zip";
        String result = instance.getLocalName(download);
        assertEquals(expResult, result);
    }

    /**
     * Test of install method, of class Package.
     */
    @Test
    public void testInstall() throws IOException {
        System.out.println("install");
        String packageName = "git";
        Package instance = new Package(packageName);
        instance.install();
        assertTrue(Files.exists(Paths.get("H:\\dev\\var\\devops\\git\\Git.zip")));
        assertTrue(Files.exists(Paths.get("H:\\dev\\var\\devops\\finish.bat")));
    }
}
