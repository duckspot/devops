package com.duckspot.roadie;

import com.duckspot.util.JsonUtil;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * ToolVersion holds a collection of sources that have the install/remove 
 * information for a tool.
 */
public class ToolVersion {

    private String toolName;
    private String version;
    private boolean installed;
    private List<URI> sources = new ArrayList<>(); // set of sources
    
    public ToolVersion(String toolName, String version) {
        this.toolName = toolName;
        this.version = version;
    }
    
    public void addSource(URI packageURI) {
        sources.add(packageURI);
    }
    
    public Package getPackage() {
        for (URI packageSource: sources) {
            try {                
                JSONObject jo = JsonUtil.getJSON(packageSource);
                if (toolName.equals(jo.getString("tool")) &&
                        version.equals(jo.getString("version"))) {
                    return new Package(packageSource, jo);
                }
            } catch (IOException ex) {
                // ignore
            }
        }
        throw new Error(String.format("can't find %s: %s",toolName,version));
    }
    
    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    public boolean isInstalled() {
        return installed;
    }
    
    public String getToolName() {
        return toolName;
    }
    
    public String getVersion() {
        return version;
    }
}
