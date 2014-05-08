package com.duckspot.roadie;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ToolTest {
    
    static String toolName = "test";
    static String toolVersion = "1.0";
    
    public ToolTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws URISyntaxException, IOException {        
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
     * Test of getVersion method, of class Tool.
     */
    @Test
    public void testAssumptions() {        
    }
    
    @Test
    public void testGetVersion() {
        System.out.println("getVersion");        
        Tool instance = new Tool(toolName);
        ToolVersion result = instance.getVersion(toolVersion);
        assertTrue(result instanceof ToolVersion);
        assertEquals(toolName, result.getToolName());
        assertEquals(toolVersion, result.getVersion());
    }

    /**
     * Test of select method, of class Tool.
     */
    @Test
    public void testSelect() {
        System.out.println("select");
        Tool instance = new Tool(toolName);
        ToolVersion result = instance.select(toolVersion);
        assertNotEquals(null, result);
        assertEquals(toolName, result.getToolName());
        assertEquals(toolVersion, result.getVersion());
    }

    /**
     * Test of selected method, of class Tool.
     */
    @Test
    public void testSelected() {
        System.out.println("selected");        
        Tool instance = new Tool(toolName);
        instance.select(toolVersion);
        ToolVersion result = instance.selected();
        assertNotEquals(null, result);
        assertEquals(toolName, result.getToolName());
        assertEquals(toolVersion, result.getVersion());
    }

    /**
     * Test of setInstalled method, of class Tool.
     */
    @Test
    public void testSetInstalled() {
        System.out.println("setInstalled");
        boolean installed = false;
        Tool instance = new Tool(toolName);
        instance.select(toolVersion);
        instance.setInstalled(installed);
        ToolVersion result = instance.selected();
        assertNotEquals(null, result);
        assertEquals(installed, result.isInstalled());
        installed = true;
        instance.setInstalled(installed);
        result = instance.selected();
        assertNotEquals(null, result);
        assertEquals(installed, result.isInstalled());        
    }
    
    /**
     * Test of listVersions method, of class Tool.
     */
    @Test
    public void testListVersions() {
        System.out.println("listVersions");
        Tool instance = new Tool(toolName);
        instance.getVersion(toolVersion);
        String[] expResult = { toolVersion };
        String[] result = instance.listVersions();
        assertArrayEquals(expResult, result);
    }
}
