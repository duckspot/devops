package com.duckspot.roadie;

import com.duckspot.util.FileUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PackageTest {
    
    static String toolName = "test";
    static String version = "1.0";
    static URI packageURI;    
    static ToolVersion toolVersion;
    static Path cachePath;
    
    public PackageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws URISyntaxException {
        RoadiePaths.setDevRoot("dev");
        packageURI = new URI("http://duckspot.com/roadie-tools/test/test/test_install.json");
        toolVersion = new ToolVersion(toolName, version);
        toolVersion.addSource(packageURI);
        cachePath = RoadiePaths.get("cache").resolve(toolName).resolve(version);        
    }
    
    @AfterClass
    public static void tearDownClass() {        
    }
    
    @Before
    public void setUp() throws IOException {
        FileUtil.deleteDirectory(RoadiePaths.get(""));        
    }
    
    @After
    public void tearDown() throws IOException {
//        FileUtil.deleteDirectory(RoadiePaths.get(""));
    }

    /**
     * Test of download method, of class Package.
     */
    @Test
    public void testInstall() {
        System.out.println("download");
        Package instance = toolVersion.getPackage();
        Path testZip = cachePath.resolve("test.zip");
        System.out.println("PackageTest(69): testZip: "+testZip);
        assertFalse(Files.exists(testZip));
        instance.install();
        assertTrue(Files.exists(testZip));
    }
}
