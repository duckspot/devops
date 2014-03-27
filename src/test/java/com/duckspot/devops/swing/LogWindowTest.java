/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.duckspot.devops.swing;

import com.duckspot.devops.swing.LogPanel;
import com.duckspot.devops.swing.PrintBuilder;
import com.duckspot.devops.swing.LogWindow;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peter
 */
public class LogWindowTest {
    
    public LogWindowTest() {
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
     * Test of getLogPanel method, of class LogWindow.
     */
    @Test
    public void testGetLogPanel() {
        System.out.println("getLogPanel");
        LogWindow instance = new LogWindow();        
        LogPanel result = instance.getLogPanel();
        assertTrue(result instanceof LogPanel);        
    }

    /**
     * Test of propertyChange method, of class LogWindow.
     */
    @Test
    public void testPropertyChange() throws InterruptedException, IOException {
        System.out.println("propertyChange");
        LogWindow instance = new LogWindow();
        PrintBuilder pb = new PrintBuilder();                
        pb.addPropertyChangeListener(instance);
        String text = "abc";
        pb.append(text);
        pb.setComplete(true);
        Thread.sleep(250L);
        assertEquals(text,instance.getLogPanel().getLogArea().getText());        
        assertTrue(instance.getLogPanel().getCloseButton().isEnabled());        
    }
    
}
