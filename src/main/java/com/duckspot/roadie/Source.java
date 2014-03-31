/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.duckspot.roadie;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author peter
 */
public class Source {
    
    private URL url;
    private String name;
    private String hash;
    
    public Source(URL url, String name) throws MalformedURLException, IOException {
        this.url = url;
        this.name = name;
        hash = String.format("%08h", 0x7fffffff & url.hashCode());        
    }
    
    public URL getURL() {
        return url;
    }
    
    public String getName() {
        return name;
    }
    
    public String toLine() {
        String line = getURL().toString();
        String name = getName();
        if (name != null && name.length() > 0) {
            line = line + " " + name;
        }
        return line;
    }
    
    /**
     * Does cache file exist?
     * 
     * @return 
     */
    public boolean isCached() {
        Path cacheFile = RoadiePaths.get("cache").resolve(hash);
        return Files.exists(cacheFile);
    }

    /**
     * Can we read the URL head?
     * 
     * @return
     * @throws IOException 
     */
    public boolean isOnline() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        int responseCode = connection.getResponseCode();
        return responseCode == 200;
    }

    /**
     * Read tool.list from the source
     * 
     * @return true if successful
     */
    public boolean attemptCache() {
        return false;
    }
    
    /**
     * Read tool.list from the source
     * 
     * @return true if successful
     */
    public boolean refreshCache() {
        return false;
    }
}
