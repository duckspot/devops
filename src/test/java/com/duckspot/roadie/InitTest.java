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
            "user.bat",
            "tools/setup.bat"
        };
    String testLocation = "testDir";
    Path testDir = Paths.get(testLocation);    
        
    public InitTest() {
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
    public void tearDown() throws IOException {
        FileUtil.deleteDirectory(testDir);
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
        Init.setOutput(printBuilder);
        Init.output(s);
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
        Init.setOutput(printBuilder);
        Init.output(s);
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
            Init.copyResource(name);
            Path file = Paths.get(testLocation+"\\"+name);            
            assert(Files.exists(file));
        }
    }

    /**
     * Test of install method, of class Init.
     */
    @Test
    public void testInstall_String() {
        System.out.println("install");
        String devPath = testLocation;
        Init.install(devPath);
        for (String name: resources) {
            Path file = Paths.get(testLocation+"\\"+name);
            System.out.println("file:"+file);
            assert(Files.exists(file));
        }
    }        
}
