package com.duckspot.roadie;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RoadiePathsTest {
    
    public RoadiePathsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        RoadiePaths.setDevRoot(getPath());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of assumptions used in code
     */
    @Test
    public void testAssumptions() {
        System.out.println("assumptions");
        String expResult = System.getProperty("user.dir");
        String result = Paths.get("").toAbsolutePath().toString();
        assertEquals(expResult, result);        
    }
    
    private String getPath() {
        String result = System.getenv("DEVOPS");
        if (result == null) {
            result = System.getenv("DEV_PATH");
            if (result == null) {
                result = System.getProperty("user.dir");
            }
        }
        return result;
    }
    
    /**
     * Test of getDrive method, of class DevopsPaths.
     */
    @Test
    public void testGetDrive() {
        System.out.println("getDrive");
        String expResult = System.getProperty("user.dir").substring(0, 1);
        String result = RoadiePaths.getDrive();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class DevopsPaths.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Path result;
        Path expResult;
        boolean threwException = false;
        try {
            result = RoadiePaths.get("foo");
        } catch (Error e) {
            threwException = true;
        }
        assertTrue(threwException);
        expResult = Paths.get(getPath());
        result = RoadiePaths.get("");
        assertEquals(expResult, result);
        expResult = Paths.get(getPath(),"tools");
        result = RoadiePaths.get("tools");
        assertEquals(expResult, result);
        expResult = Paths.get(getPath(), "var", "cache", "devops");
        result = RoadiePaths.get("packages");
        expResult = Paths.get(getPath(),"var","cache","devops","partial");
        result = RoadiePaths.get("packages/partial");
        assertEquals(expResult, result);
        expResult = Paths.get(getPath(), "setup.bat");
        result = RoadiePaths.get("setup.bat");
        assertEquals(expResult, result);
        expResult = Paths.get(getPath(), "user.bat");
        result = RoadiePaths.get("user.bat");
        assertEquals(expResult, result);
        expResult = Paths.get(getPath(), "tools", "setup.bat");
        result = RoadiePaths.get("tools/setup.bat");
        assertEquals(expResult, result);
        expResult = Paths.get(getPath(), "tools", "devops", "sources.list");
        result = RoadiePaths.get("sources.list");
        assertEquals(expResult, result);    
    }

    /**
     * Test of setDevRoot method, of class DevopsPaths.
     */
    @Test
    public void testSetDevRoot() {
        System.out.println("setDevRoot");
        String path = "C:\\";
        RoadiePaths.setDevRoot(path);
        Path result;
        Path expResult;
        expResult = Paths.get(path);
        result = RoadiePaths.get("");
        assertEquals(expResult, result);        
    }
    
}
