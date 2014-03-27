package com.duckspot.devops.swing;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;

public class LogWindow extends JFrame implements PropertyChangeListener  {
    
    private LogPanel logPanel;
    
    public LogWindow() {
        init();
    }
    
    LogPanel getLogPanel() {
        return logPanel;
    }
    
    private void init() {
     
        setTitle("Roadie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setSize(400,500);
        add(new TitlePanel(), BorderLayout.NORTH);
        
        logPanel = new LogPanel();
        add(logPanel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof PrintBuilder) {            
            logPanel.propertyChange(evt);
        }
    }
}
