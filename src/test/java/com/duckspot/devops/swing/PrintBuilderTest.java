package com.duckspot.devops.swing;

import com.duckspot.devops.swing.PrintBuilder;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PrintBuilderTest {
    
    static class TestPCL implements PropertyChangeListener {
        List<PropertyChangeEvent> events = new ArrayList<>();
        public void propertyChange(PropertyChangeEvent evt) {
            events.add(evt);
        }
    }
    
    public PrintBuilderTest() {
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
     * Test of setComplete method, of class PrintBuilder.
     */
    @Test
    public void testSetComplete() {
        System.out.println("setComplete");
        boolean complete = true;
        PrintBuilder instance = new PrintBuilder();
        TestPCL testPCL = new TestPCL();        
        instance.addPropertyChangeListener(testPCL);
        instance.setComplete(complete);
        assertEquals(1,testPCL.events.size());
        PropertyChangeEvent pce = testPCL.events.remove(0);
        assertTrue(pce.getSource() instanceof PrintBuilder);
        assertEquals("complete",pce.getPropertyName());
        assertEquals(complete, pce.getNewValue());
    }

    /**
     * Test of getText method, of class PrintBuilder.
     */
    @Test
    public void testGetText() throws IOException {
        System.out.println("getText");
        PrintBuilder instance = new PrintBuilder();
        String expResult = "";
        String result = instance.getText();
        assertEquals(expResult, result);
        expResult="abc";
        instance.append("abc\r");
        result = instance.getText();
        assertEquals(expResult, result);
        expResult="1234";        
        instance.append(expResult);
        result = instance.getText();
        assertEquals(expResult, result);        
    }

    /**
     * Test of addPropertyChangeListener method, of class PrintBuilder.
     */
    @Test
    public void testAddPropertyChangeListener() throws IOException {
        System.out.println("addPropertyChangeListener");
        TestPCL listener = new TestPCL();
        PrintBuilder instance = new PrintBuilder();
        instance.addPropertyChangeListener(listener);
        instance.append("abc");
        assertEquals(1,listener.events.size());
        PropertyChangeEvent pce = listener.events.remove(0);
        assertEquals(instance,pce.getSource());
    }

    /**
     * Test of removePropertyChangeListener method, of class PrintBuilder.
     */
    @Test
    public void testRemovePropertyChangeListener() throws IOException {
        System.out.println("removePropertyChangeListener");
        TestPCL listener = new TestPCL();
        PrintBuilder instance = new PrintBuilder();
        instance.addPropertyChangeListener(listener);
        instance.removePropertyChangeListener(listener);
        instance.append("abc");
        assertEquals(0,listener.events.size());        
    }

    /**
     * Test of append method, of class PrintBuilder.
     */
    @Test
    public void testAppend_char() throws Exception {
        System.out.println("append");
        char c = ' ';
        PrintBuilder instance = new PrintBuilder();
        Appendable expResult = instance;
        Appendable result = instance.append(c);
        assertEquals(expResult, result);
        assertEquals(" ", instance.getText());
    }

    /**
     * Test of append method, of class PrintBuilder.
     */
    @Test
    public void testAppend_3args() throws Exception {
        System.out.println("append");
        CharSequence csq = "1234";
        int start = 1;
        int end = 3;
        PrintBuilder instance = new PrintBuilder();
        Appendable expResult = instance;
        Appendable result = instance.append(csq, start, end);
        assertEquals(expResult, result);
        assertEquals(csq.subSequence(start, end), instance.getText());        
    }

    /**
     * Test of append method, of class PrintBuilder.
     */
    @Test
    public void testAppend_CharSequence() throws Exception {
        System.out.println("append");
        CharSequence csq = "1234";
        PrintBuilder instance = new PrintBuilder();
        Appendable expResult = instance;
        Appendable result = instance.append(csq);
        assertEquals(expResult, result);
        csq = "\rabcd";
        result = instance.append(csq);
        assertEquals(expResult, result);
        assertEquals("abcd", instance.getText());
    }

    /**
     * Test of toString method, of class PrintBuilder.
     */
    @Test
    public void testToString() throws IOException {
        System.out.println("toString");
        PrintBuilder instance = new PrintBuilder();
        String csq = "1234";
        String expResult = csq;
        instance.append(csq);
        String result = instance.toString();
        assertEquals(expResult, result);        
    }
    
}
