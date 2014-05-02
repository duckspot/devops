package com.duckspot.util;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProcessUtilTest {
    
    public ProcessUtilTest() {
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

    /**
     * Test of batchFile method, of class ProcessUtil.
     */
    @Test
    public void testBatchFile() throws Exception {
        System.out.println("batchFile");
        Path batchFile = Paths.get("dev", "test.bat");
        String[] script = { "@echo one", "@echo two" };
        Files.write(batchFile, Arrays.asList(script), Charset.defaultCharset());
        String expResult = "one\ntwo\n";
        String result = ProcessUtil.batchFile(batchFile);
        assertEquals(expResult, result);        
    }
    
}
