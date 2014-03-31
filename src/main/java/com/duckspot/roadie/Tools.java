package com.duckspot.roadie;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manages sets of installed and online tools.
 * 
 * @author peter
 */
public class Tools {
    
    private Config config;
    private Sources sources;
    
    List<String> categories;      
    Map<String,List<String>> toolsInCategory; // 
    Map<String,Tool> tools; // tools by name
    
    public void setConfig(Config config) {
        this.config = config;
    }
    
    public void setSources(Sources sources) {
        this.sources = sources;
    }
    
    public void addToolFromSetupBat(String line) {
        String[] parts = line.substring(11).split(" ");
        if (parts.length >= 1) {
            String name = parts[0];
            Tool tool = new Tool(name);
            if (parts.length >= 1) {
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
}
