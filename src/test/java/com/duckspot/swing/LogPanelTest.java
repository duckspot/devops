package com.duckspot.swing;

import com.duckspot.swing.LogPanel;
import com.duckspot.swing.PrintBuilder;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LogPanelTest {
    
    public LogPanelTest() {
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
     * Test of propertyChange method, of class LogPanel.
     */
    @Test
    public void testPropertyChange() throws IOException, InterruptedException {
        System.out.println("propertyChange");
        PrintBuilder pb = new PrintBuilder();                
        LogPanel instance = new LogPanel();
        pb.addPropertyChangeListener(instance);
        String text = "abc";
        pb.append(text);
        pb.setComplete(true);
        Thread.sleep(250L);
        assertEquals(text,instance.getLogArea().getText());
        Thread.sleep(250L);
        assertTrue(instance.getCloseButton().isEnabled());
    }
}
