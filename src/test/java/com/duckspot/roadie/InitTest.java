package com.duckspot.roadie;

import com.duckspot.swing.PrintBuilder;
import com.duckspot.util.FileUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class InitTest {
    
    static String[] resources = {
            "setup.bat",
            "sources.list",
            "user.bat",
            "tools/setup.bat"
        };
    static String testLocation = "testDir";
    static Path testDir = Paths.get(testLocation);
    
    Init instance;
        
    public InitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
        instance = new Init();
        FileUtil.deleteDirectory(testDir);
    }
    
    @After
    public void tearDown() throws IOException {
//        FileUtil.deleteDirectory(testDir);
    }

    @Test
    public void testResources() throws IOException {
        System.out.println("resources exist");        
        for (String name: resources) {
            InputStream is = getClass().getResourceAsStream("/dev/"+name);
            assertTrue(is != null);
            assertTrue(is.available() > 0);
            is.close();
        }
    }        

    /**
     * Test of setOutput method, of class Init.
     */
    @Test
    public void testSetOutput() {
        System.out.println("setOutput");
        PrintBuilder printBuilder = new PrintBuilder();
        String s = "test";
        instance.setOutput(printBuilder);
        instance.output(s);
        assertEquals(s,printBuilder.getText());
    }

    /**
     * Test of output method, of class Init.
     */
    @Test
    public void testOutput() {
        System.out.println("output");
        PrintBuilder printBuilder = new PrintBuilder();
        String s = "test";
        instance.setOutput(printBuilder);
        instance.output(s);
        assertEquals(s,printBuilder.getText());
    }

    /**
     * Test of copyResource method, of class Init.
     */
    @Test
    public void testCopyResource() {
        System.out.println("copyResource");
        String resource = resources[0];
        RoadiePaths.setDevRoot(testLocation);
        for (String name: resources) {
            instance.copyResource(name);
            Path file = RoadiePaths.get(name);
            assert(Files.exists(file));
        }
    }

    /**
     * Test of install method, of class Init.
     */
    @Test
    public void testInstall() throws IOException {
        System.out.println("install");
        String devPath = testLocation;
        RoadiePaths.setDevRoot(devPath);
        instance.install();
        for (String name: resources) {
            Path file = RoadiePaths.get(name);
            System.out.println("file:"+file);
            assert(Files.exists(file));
        }
    }
}