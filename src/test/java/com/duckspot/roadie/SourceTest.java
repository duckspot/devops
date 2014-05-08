package com.duckspot.roadie;

import com.duckspot.util.FileUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SourceTest {
    
    static URI sourceURI;
    static String name;
    
    public SourceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws URISyntaxException {
        sourceURI = new URI("http://duckspot.com/roadie-tools/test/package.list");
        name = "duckspot.com/roadie-tools/test";
        RoadiePaths.setDevRoot("dev");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
        FileUtil.deleteDirectory(Paths.get("dev"));
    }
    
    @After
    public void tearDown() {        
    }

    @Test
    public void testAssumptions() throws MalformedURLException, IOException {
        int number = 0x7fff;
        String expResult = "00007fff";
        String temp = "0000000" + Integer.toHexString(number);
        String result = temp.substring(temp.length()-8);
        assertEquals(expResult, result);
        number = 0;
        expResult = "00000000";
        temp = "0000000" + Integer.toHexString(number);
        result = temp.substring(temp.length()-8);
        assertEquals(expResult, result);
        number = 0x7fffffff;
        expResult = "7fffffff";
        temp = "0000000" + Integer.toHexString(number);
        result = temp.substring(temp.length()-8);
        assertEquals(expResult, result);
        URL sourceURL = sourceURI.toURL();
        InputStream is = sourceURL.openStream();        
        is.close();
    }
    /**
     * Test of getURI method, of class Source.
     */
    @Test
    public void testGetURI() throws IOException {
        System.out.println("getURI");        
        Source instance = new Source(sourceURI, name);
        URI expResult = sourceURI;
        URI result = instance.getURI();
        assertEquals(expResult, result);        
    }

    /**
     * Test of getName method, of class Source.
     */
    @Test
    public void testGetName() throws IOException {
        System.out.println("getName");
        Source instance = new Source(sourceURI, name);
        String expResult = name;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of toLine method, of class Source.
     */
    @Test
    public void testToLine() throws IOException {
        System.out.println("toLine");
        Source instance = new Source(sourceURI, name);
        String expResult = sourceURI+" "+name;
        String result = instance.toLine();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCachePath() throws IOException {
        System.out.println("isCached");
        Source instance = new Source(sourceURI, name);        
        Path expResult = RoadiePaths.get("cache").resolve("4214b076_tools.list");
        Path result = instance.getCachePath();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isCached method, of class Source.
     */
    @Test
    public void testIsCached() throws IOException {
        System.out.println("isCached");
        Source instance = new Source(sourceURI, name);
        boolean expResult = false;
        boolean result = instance.isCached();
        assertEquals(expResult, result);
        instance.sourceToCache();
        expResult = true;
        result = instance.isCached();
        assertEquals(expResult, result);        
    }

    /**
     * Test of isOnline method, of class Source.
     */
    @Test
    public void testIsOnline() throws IOException {
        System.out.println("isOnline");
        Source instance = new Source(sourceURI, name);
        boolean expResult = true;
        boolean result = instance.isOnline();
        assertEquals(expResult, result);
    }

    /**
     * Test of SourceToCache method, of class Source.
     */
    @Test
    public void testSourceToCache() throws IOException {
        System.out.println("SourceToCache");        
        Source instance = new Source(sourceURI, name);
        assertFalse(instance.isCached());
        boolean expResult = true;
        boolean result = instance.sourceToCache();
        assertEquals(expResult, result);        
        assertTrue(instance.isCached());
    }

    /**
     * Test of getLines method, of class Source.
     */
    @Test
    public void testGetLines() throws IOException {
        System.out.println("getLines");
        System.out.println("sourceURI:"+sourceURI);
        Source instance = new Source(sourceURI, name);
        String[] lines = {"test 1.0 test/test_install.json"};
        List<String> expResult = Arrays.asList(lines);
        List<String> result = instance.getLines();
        assertEquals(expResult, result);        
    }
    
}
