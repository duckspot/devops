package com.duckspot.roadie;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class Tool {
 
    private Map<String,ToolVersion> toolVersions = new HashMap<>();    
    private ToolVersion selected;
    private String name;
            
    public Tool(String name) {
        this.name = name;
    }
    
    public String[] listVersions() {
        return toolVersions.keySet().toArray(new String[0]);
    }
    
    public ToolVersion getVersion(String version) {
        ToolVersion result = toolVersions.get(version);
        if (result == null) {
            result = new ToolVersion(name, version);
            toolVersions.put(version, result);
        }
        return result;
    }
    
    public ToolVersion select(String version) { 
        selected = getVersion(version);
        return selected;
    }
        
    public ToolVersion selected() {         
        return selected;
    }
        
    public void setInstalled(boolean installed) {
        if (selected != null) {
            selected.setInstalled(installed);
        }
    }
}