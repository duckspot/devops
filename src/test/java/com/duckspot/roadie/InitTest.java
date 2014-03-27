package com.duckspot.roadie;

import com.duckspot.roadie.Init;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class InitTest {
    
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
    public void tearDown() {
    }

    @Test
    public void testResources() throws IOException {
        System.out.println("resources exist");
        String[] resources = {
            "/dev/setup.bat",
            "/dev/user.bat",
            "/dev/tools/setup.bat"
        };
        for (String name: resources) {
            Init i = new Init();
            InputStream is = getClass().getResourceAsStream(name);
            assertTrue(is != null);
            assertTrue(is.available() > 0);
            is.close();
        }
    }        

    /**
     * Test of checkDirectory method, of class Init.
     */
//    @Test
//    public void testCheckDirectory() {
//        System.out.println("checkDirectory");
//        Path path = null;
//        Init.checkDirectory(path);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of copyResource method, of class Init.
     */
//    @Test
//    public void testCopyResource() {
//        System.out.println("copyResource");
//        String resource = "";
//        Init.copyResource(resource);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of output method, of class Init.
     */
//    @Test
//    public void testOutput() {
//        System.out.println("output");
//        String s = "";
//        Init.output(s);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of end method, of class Init.
     */
//    @Test
//    public void testEnd() {
//        System.out.println("end");
//        Init.end();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of install method, of class Init.
     */
//    @Test
//    public void testInstall_0args() {
//        System.out.println("install");
//        Init.install();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of install method, of class Init.
     */
//    @Test
//    public void testInstall_String() {
//        System.out.println("install");
//        String devPath = "";
//        Init.install(devPath);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
