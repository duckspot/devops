package com.duckspot.roadie;

import com.duckspot.util.FileUtil;
import com.duckspot.util.ListFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Manages one source of tools.  Each source has an 'tools.list' file that 
 * contains information about each Package available from that source, where a
 * package is information about how to install and remove a particular version 
 * of a tool.
 */
public class Source {
    
    private URI uri;
    private String name;
    private String hash;
    private Path cachePath;
    private List<String> lines;
    
    public Source(URI uri, String name) throws MalformedURLException, IOException {
        this.uri = uri;
        this.name = name;
        String temp = "0000000" 
                + Integer.toHexString(0x7fffffff & uri.hashCode());
        hash = temp.substring(temp.length()-8);        
        cachePath = RoadiePaths.get("cache").resolve(hash + "_tools.list");
    }
    
    public URI getURI() {
        return uri;
    }
    
    public String getName() {
        return name;
    }
    
    public String toLine() {
        String line = getURI().toString();
        String name = getName();
        if (name != null && name.length() > 0) {
            line = line + " " + name;
        }
        return line;
    }
    
    public Path getCachePath() {
        return cachePath;
    }
    
    /**
     * Does cache file exist?
     * 
     * @return 
     */
    public boolean isCached() {        
        return Files.exists(cachePath);
    }

    /**
     * Can we read the URL head?
     * 
     * @return
     * @throws IOException 
     */
    public boolean isOnline() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("HEAD");
        int responseCode = connection.getResponseCode();
        return responseCode == 200;
    }

    /**
     * Read tool.list from the URL into memory.
     * 
     * @return
     */
    private void linesFromURL() throws IOException {        
        InputStream is = uri.toURL().openStream();
        lines = new ListFile (is).asList();
        is.close();
    }
    
    /**
     * Read tool.list from the cache into memory.
     * 
     * @return
     */
    private void linesFromCache() throws IOException {        
        lines = new ListFile (cachePath).asList();                
    }
    
    /**
     * Read tool.list from the cache into memory.
     * 
     * @return
     */
    private void linesToCache() throws IOException {
        FileUtil.checkDirectory(cachePath.getParent());
        Files.write(cachePath, lines, Charset.defaultCharset());
    }
    
    /**
     * Read tool.list from the source.
     * 
     * @return true if successful
     */
    public boolean sourceToCache() {
        try {
            linesFromURL();            
        } catch (IOException ex) {
            // this can happen w. server is unreachable or down
//            return false;
            throw new Error("test exception", ex);
        }
        try {
            linesToCache();
        } catch (IOException ex) {
            // TODO: consider disk full
            // TODO: display useful message to user
            System.out.println("unexpected exception: "+ex);
            throw new Error("unexpected exception", ex);
        }
        return true;
    }
    
    public List<String> getLines() {
        if (lines == null && !isCached()) {
            sourceToCache();
        }
        if (lines == null) {
            try {
                linesFromCache();
            } catch (IOException ex) {
                return null;
            }
        }
        return lines;
    }
}
