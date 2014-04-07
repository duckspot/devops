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
    
    public ToolVersion getVersion(String version) {
        ToolVersion result = toolVersions.get(version);
        if (result == null) {
            result = new ToolVersion(name, version);
            toolVersions.put(name, result);
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
    
    public void install() {
        selected().install();
    }
    
    public void remove() {
        selected().remove();
    }
    
    void addVersionSource(String version, URI toolSource) {        
    }        
}