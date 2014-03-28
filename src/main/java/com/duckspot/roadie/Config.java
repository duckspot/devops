/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.duckspot.roadie;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author peter
 */
public class Config {

    private boolean validSetup;
    private Map<String,String> userVar = new HashMap<>();
    private Map<String,Tool> tools = new HashMap<>();
    
    private void readUserBat() {  
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
                    userVar.put(name, value);
                }
            }
            validSetup = userVar.containsKey("username");
        } catch (IOException ex) {
            validSetup = false;
        }        
    }
    
    private void readToolsSetupBat() {  
        try {
            for (String line: 
                    Files.readAllLines(RoadiePaths.get("tools/setup.bat"), 
                    Charset.defaultCharset())) {
                line = line.trim();
                if (line.toLowerCase().startsWith("rem tools: ")) {
                    String[] parts = line.substring(11).split(" ");
                    if (parts.length >= 1) {
                        String name = parts[0];
                        Tool tool = new Tool(name);
                        if (parts.length >= 1) {
                            String version = parts[1];
                            tool.select(version);
                        }
                        tools.put(name, tool);
                    }
                }
            }
        } catch (IOException ex) {
            validSetup = false;
        }        
    }
    
    public Config() {
        validSetup = Files.exists(RoadiePaths.get("roadie.jar")) &&
                Files.exists(RoadiePaths.get("setup.bat")) &&
                Files.exists(RoadiePaths.get("user.bat")) && 
                Files.exists(RoadiePaths.get("tools/setup.bat"));
        if (!validSetup) {
            return;
        }
        readUserBat();
        readToolsSetupBat();
    }
    
    public boolean isValidSetup() {
        return validSetup;
    }    
}
