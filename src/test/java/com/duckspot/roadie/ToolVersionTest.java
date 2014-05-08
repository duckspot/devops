package com.duckspot.roadie;

import java.net.URI;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ToolVersionTest {
    
    static String toolName = "test";
    static String toolVersion = "1.0";
    static URI packageURI;
    
    public ToolVersionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws URISyntaxException {
        packageURI = new URI("http://duckspot.com/roadie-tools/test/test/test_install.json");
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
     * Test of addSource method, of class ToolVersion.
     */
    @Test
    public void testAddSource() {
        System.out.println("addSource");        
        ToolVersion instance = new ToolVersion(toolName, toolVersion);
        instance.addSource(packageURI);
    }

    /**
     * Test of getPackage method, of class ToolVersion.
     */
    @Test
    public void testGetPackage() {
        System.out.println("getPackage");
        ToolVersion instance = new ToolVersion(toolName, toolVersion);
        instance.addSource(packageURI);        
        Package result = instance.getPackage();
        assertTrue(result != null);        
    }

    /**
     * Test of setInstalled method, of class ToolVersion.
     */
    @Test
    public void testSetInstalled() {
        System.out.println("setInstalled");
        boolean installed = true;
        ToolVersion instance = new ToolVersion(toolName, toolVersion);
        instance.setInstalled(installed);
        boolean expResult = installed;
        boolean result = instance.isInstalled();
        assertEquals(expResult, result);
        installed = false;
        instance.setInstalled(installed);
        expResult = installed;
        result = instance.isInstalled();
        assertEquals(expResult, result);
    }

    /**
     * Test of isInstalled method, of class ToolVersion.
     */
    @Test
    public void testIsInstalled() {
        System.out.println("isInstalled");
        ToolVersion instance = new ToolVersion(toolName, toolVersion);
        boolean expResult = false;
        boolean result = instance.isInstalled();
        assertEquals(expResult, result);        
    }

    /**
     * Test of getToolName method, of class ToolVersion.
     */
    @Test
    public void testGetToolName() {
        System.out.println("getToolName");
        ToolVersion instance = new ToolVersion(toolName, toolVersion);
        String expResult = toolName;
        String result = instance.getToolName();
        assertEquals(expResult, result);        
    }

    /**
     * Test of getVersion method, of class ToolVersion.
     */
    @Test
    public void testGetVersion() {
        System.out.println("getVersion");
        ToolVersion instance = new ToolVersion(toolName, toolVersion);
        String expResult = toolVersion;
        String result = instance.getVersion();
        assertEquals(expResult, result);       
    }
    
}
