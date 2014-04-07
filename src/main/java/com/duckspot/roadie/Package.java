package com.duckspot.roadie;

import com.duckspot.roadie.download.Download;
import com.duckspot.roadie.download.Downloader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 * Responsible for installing and removing a particular downloaded package.
 * 
 */
public class Package {
    
    JSONObject packageJson;
    String toolName;
    String version;
    Path cachePath;
    
    /**
     * 
     * @param packageJson 
     */
    public Package(JSONObject packageJson) {        
        this.packageJson = packageJson;
        toolName = packageJson.getString("tool");
        version = packageJson.getString("version");
        cachePath = RoadiePaths.get("cache").resolve(toolName).resolve(version);
    }
    
    public void download() {
        try {
            JSONObject downloads = packageJson.getJSONObject("downloads");
            Iterator di = downloads.keys();
            while (di.hasNext()) {
                String name = (String)di.next();
                URL source = new URL((String)downloads.getString(name));
                Downloader.getInstance().download(
                        new Download(cachePath.resolve(name), source));
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Package.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void remove() {
    }
}
