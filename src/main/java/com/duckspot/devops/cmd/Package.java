package com.duckspot.devops.cmd;

import com.duckspot.devops.download.Download;
import com.duckspot.devops.download.Downloader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author peter
 */
public class Package {
    
    static final String fmt = "https://raw.github.com/duckspot/devops/master"
            + "/src/main/resources/packages/%s.json";
    
    static URL packageURL( String packageName ) {
        try {
            return new URL(String.format(fmt, packageName ));
        } catch (MalformedURLException ex) {
            throw new Error("unexpected exception", ex);
        }
    }
    
    static JSONObject getJSON( URL url ) throws IOException {
        return new JSONObject(new JSONTokener(url.openStream()));
    }
    
    String name;
    JSONObject config;
    
    public Package(String name) {
        this.name = name;
    }
    
    public JSONObject getConfig() throws IOException {
        if (config == null) {
            config = getJSON(packageURL(name));
        }
        return config;
    }
    
    public String[] getStringArray(String key) throws IOException {
        JSONArray ja = getConfig().getJSONArray(key);
        String[] result = new String[ja.length()];
        for (int i=0; i<ja.length(); i++) {
            result[i] = ja.getString(i);            
        }
        return result;
    }
    
    public String[] getDownloads() throws IOException {
        return getStringArray("downloads");
    }
    
    public String[] getScript() throws IOException {
        return getStringArray("script");
    }
    
    protected String getDevPath() {
        return System.getenv("DEV_PATH");
    }
    
    private final static Pattern afterLastSlash = Pattern.compile("[^/]*$");
    protected String getLocalName(String download) {        
        String s = download.replaceFirst("#.*$", ""); // take off #...
        s = s.replaceFirst("\\?.*$", "");             // take off ?...
        Matcher m = afterLastSlash.matcher(s);
        if (m.find()) {
            s = m.group();
        }
        return getDevPath()+"\\var\\devops\\"+name+"\\"+s;
    }
    
    /**
     * Download all downloads needed by this package, and append lines to 
     * install file.
     */
    void install(PrintWriter finishScript) {
        try {
            for (String download : getDownloads()) {
                File dst = new File(getLocalName(download));
                URL src = new URL(download);
                Downloader.getInstance().request(new Download(dst, src));                
            }
            for (String scriptLine : getScript()) {
                finishScript.println(scriptLine);
            }
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void install() throws IOException {
        Path varDevopsFolder = Paths.get(getDevPath(), "var", "devops");
        Files.createDirectories(varDevopsFolder);
        Path finishScriptPath = varDevopsFolder.resolve("finish.bat");        
        PrintWriter finishScriptWriter = new PrintWriter(
                Files.newOutputStream(finishScriptPath));
        install(finishScriptWriter);
    }
}
