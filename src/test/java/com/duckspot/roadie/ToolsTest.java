package com.duckspot.roadie;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ToolsTest {
    
    static URI sourceURI;
    static String sourceName;
    
    public ToolsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws URISyntaxException, IOException {
        sourceURI = new URI("http://duckspot.com/roadie-tools/package.list");
        sourceName = "duckspot.com/roadie-tools";
        RoadiePaths.setDevRoot("dev");
        Init init = new Init();        
        if (!init.isInstalled()) {
            init.install();
        }        
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
     * Test of clear method, of class Tools.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        Tools instance = new Tools();
        instance.clear();
        String[] result = instance.listTools();
        assertEquals(0, result.length);
    }

    /**
     * Test of listTools method, of class Tools.
     */
    @Test
    public void testListTools() {
        System.out.println("listTools");
        Tools instance = new Tools();
        String[] result = instance.listTools();
        assertEquals(0, result.length);
    }

    /**
     * Test of getTool method, of class Tools.
     */
    @Test
    public void testGetTool() {
        System.out.println("getTool");
        String name = "jdk";
        Tools instance = new Tools();
        Tool result = instance.getTool(name);
        assertTrue(result instanceof Tool);        
    }

    /**
     * Test of addToolFromSetupBat method, of class Tools.
     */
    @Test
    public void testAddToolFromSetupBat() {
        System.out.println("addToolFromSetupBat");
        String line = "rem tools: jdk 1.7.0_51";
        Tools instance = new Tools();
        instance.addToolFromSetupBat(line);
        Tool tool = instance.getTool("jdk");
        assertTrue(tool instanceof Tool);
    }

    /**
     * Test of readToolsSetupBat method, of class Tools.
     */
    @Test
    public void testReadToolsSetupBat() throws Exception {
        System.out.println("readToolsSetupBat");
        Tools instance = new Tools();
        instance.readToolsSetupBat();
    }

    /**
     * Test of toolsFromSource method, of class Tools.
     */
    @Test
    public void testToolsFromSource() throws IOException {
        System.out.println("toolsFromSource");
        Source source = new Source(sourceURI, sourceName);
        Tools instance = new Tools();
        instance.toolsFromSource(source);
        String[] tools = instance.listTools();
        assertEquals(1, tools.length);
        Tool tool = instance.getTool(tools[0]);
        String[] versions = tool.listVersions();
        String[] expResult = { "1.8.5.2.msysgit.0" };
        assertArrayEquals(expResult, versions);
    }
}
