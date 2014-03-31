package com.duckspot.roadie;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages a particular configuration on a particular development environment.
 */
public class Config {

    private boolean valid;
    private String username = null;
    private Sources sources;
    private Tools tools;
    
    public Config() {
        sources = new Sources();
        tools = new Tools();
        tools.setConfig(this);
        tools.setSources(sources);
        valid = Files.exists(RoadiePaths.get("roadie.jar")) &&
                Files.exists(RoadiePaths.get("setup.bat")) &&
                Files.exists(RoadiePaths.get("user.bat")) && 
                Files.exists(RoadiePaths.get("tools/setup.bat")) &&
                readUserBat();        
        if (!valid) return;
//        tools.readToolsSetupBat();
        if (!valid) return;
//        sources.readSourcesOnline();
    }
    
    public boolean isValidSetup() {
        return valid;
    }    
    
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    private boolean readUserBat() {  
        try {
            for (String line: 
                    Files.readAllLines(RoadiePaths.get("user.bat"), 
                    Charset.defaultCharset())) {
                line = line.trim();
                if (line.toLowerCase().startsWith("set ")) {
                    int p = line.indexOf('=');                    
                    String name=line.substring(4, p).trim().toLowerCase();
                    String value = "";
                    if (p+1 < line.length()) {
                        value=line.substring(p+1).trim();
                    }
                    if (name.equals("username")) {
                        username = value;
                    }
                }
            }
            return username != null;
        } catch (IOException ex) {
            return false;
        }        
    }
    
    public String getUsername() {
        return username;
    }
}