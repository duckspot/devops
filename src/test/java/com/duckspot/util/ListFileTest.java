package com.duckspot.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ListFileTest {
    
    public ListFileTest() {
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
     * Test of nextLine method, of class ListFile.
     */
    @Test
    public void testNextLine() throws IOException {
        System.out.println("nextLine");  
        byte[] buf = "# comment\none\ntwo\n".getBytes();
        InputStream is = new ByteArrayInputStream(buf);
        ListFile instance = new ListFile(is);
        String expResult = "one";
        String result = instance.nextLine();
        assertEquals(expResult, result);
        expResult = "two";
        result = instance.nextLine();
        assertEquals(expResult, result);
        try {
            result = instance.nextLine();
            fail("didn't throw exception at end of file");
        } catch (NoSuchElementException ex) {
            // success
        }
        
    }    
}
