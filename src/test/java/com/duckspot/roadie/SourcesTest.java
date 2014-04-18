package com.duckspot.roadie;

import static com.duckspot.roadie.SourceTest.sourceURI;
import com.duckspot.util.FileUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SourcesTest {
    
    static URI sourceURI;
    static String name;
    
    public SourcesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws URISyntaxException, IOException {
        sourceURI = new URI("http://duckspot.com/roadie-tools/package.list");
        name = "duckspot.com/roadie-tools";
        RoadiePaths.setDevRoot("dev");
        Path slPath = RoadiePaths.get("sources.list");
        if (!Files.exists(slPath)) {
            String line = sourceURI+" "+name;
            FileUtil.checkDirectory(slPath.getParent());
            Files.write(slPath, Arrays.asList(new String[]{line}), Charset.defaultCharset());
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() throws IOException {
//        FileUtil.deleteDirectory(RoadiePaths.get(""));
    }

    @Test
    public void testAssumptions() {
        assertTrue(Files.exists(RoadiePaths.get("sources.list")));
    }
    /**
     * Test of checkSources method, of class Sources.
     */
    @Test
    public void testCheckSources() {
        System.out.println("checkSources");
        Sources instance = new Sources();
        instance.checkSources();
        List<Source> list = instance.getOnline();
        assertEquals(1,list.size());
    }

    /**
     * Test of writeSourcesOnline method, of class Sources.
     */
    @Test
    public void testWriteSourcesOnline() {
        System.out.println("writeSourcesOnline");
        Sources instance = new Sources();
        instance.checkSources();
        instance.writeSourcesOnline();
        assertTrue(Files.exists(RoadiePaths.get("sources.online")));
    }

    /**
     * Test of readSourcesOnline method, of class Sources.
     */
    @Test
    public void testReadSourcesOnline() {
        System.out.println("readSourcesOnline");
        Sources instance = new Sources();
        instance.checkSources();
        instance.writeSourcesOnline();
        instance = new Sources();
        instance.readSourcesOnline();
        List<Source> list = instance.getOnline();
        assertEquals(1,list.size());
    }

    /**
     * Test of getOnline method, of class Sources.
     */
    @Test
    public void testGetOnline() {
        System.out.println("getOnline");
        Sources instance = new Sources();
        instance.checkSources();
        List<Source> result = instance.getOnline();        
        assertEquals(1,result.size());
    }
    
}
