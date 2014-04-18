package com.duckspot.roadie;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages sets of installed and online tools.
 * 
 * @author peter
 */
public class Tools {
    
    Map<String,Tool> tools = new HashMap<>(); // tools by name
    
    public void clear() {
        tools.clear();
    }
    
    public String[] listTools() {
        String[] result = tools.keySet().toArray(new String[0]);
        Arrays.sort(result);
        return result;
    }
    
    public Tool getTool(String name) {
        if (tools.containsKey(name)) {
            return tools.get(name);
        } else {
            Tool result = new Tool(name);
            tools.put(name, result);
            return result;
        }
    }
    
    public void addToolFromSetupBat(String line) {
        String[] parts = line.substring(11).split(" ");
        if (parts.length >= 1) {
            String name = parts[0];
            Tool tool = getTool(name);
            if (parts.length >= 2) {
                String version = parts[1];
                tool.select(version);
            }
            tool.setInstalled(true);                        
            tools.put(name, tool);
        }
    }
    
    public void readToolsSetupBat() throws IOException {
        for (String line: 
                Files.readAllLines(RoadiePaths.get("tools/setup.bat"), 
                Charset.defaultCharset())) {
            line = line.trim();
            if (line.toLowerCase().startsWith("rem tools: ")) {
                addToolFromSetupBat(line);
            }
        }
    }
    
    public void toolsFromSource(Source source) {
        int lineCounter = 0;
        for (String line: source.getLines()) {
            lineCounter++;
            String[] parts = line.split(" ");
            if (parts.length < 3) {
                Logger.getLogger(Sources.class.getName()).log(Level.SEVERE, 
                        String.format("%s (%d) must have three components", 
                                source.getURI(), lineCounter));
            } else {
                String name = parts[0];
                String version = parts[1];
                URI toolSource = source.getURI().resolve(parts[2]);
                getTool(name).getVersion(version).addSource(toolSource);
            }
        }
    }
}
