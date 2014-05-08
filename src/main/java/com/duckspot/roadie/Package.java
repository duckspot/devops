package com.duckspot.roadie;

import com.duckspot.roadie.download.Download;
import com.duckspot.roadie.download.DownloadGroup;
import com.duckspot.roadie.download.Downloader;
import com.duckspot.util.ProcessUtil;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Responsible for installing and removing a particular version of a tool, given
 * the packageJson object that describes how it's done.
 * 
 */
public class Package implements PropertyChangeListener {
    
    URI packageSource;
    JSONObject packageJson;
    String toolName;
    String version;
    Path cachePath;
    
    /**
     * Creates a package given the JSON data read by ToolVersion from the
     * source.
     * 
     * @param packageJson 
     */
    public Package(URI packageSource, JSONObject packageJson) {
        this.packageSource = packageSource;
        this.packageJson = packageJson;
        toolName = packageJson.getString("tool");
        version = packageJson.getString("version");
        cachePath = RoadiePaths.get("cache").resolve(toolName).resolve(version);
    }
    
    /**
     * Begins the download of all the parts of the package.  Callback when
     * downloads complete triggers install2()
     */
    public void install() {
        Downloader transport = Downloader.getInstance();
        DownloadGroup group = new DownloadGroup();
        group.addPropertyChangeListener(this);
        try {
            JSONObject downloads = packageJson.getJSONObject("downloads");            
            Iterator di = downloads.keys();
            while (di.hasNext()) {
                String name = (String)di.next();
                URI source = packageSource.resolve((String)downloads.getString(name));
                Download item = new Download(cachePath.resolve(name), source);
                group.add(item);
                transport.request(item);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Package.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    /**
     * perform second part of install, after all files downloaded.
     * 
     */
    public void install2() throws IOException {
        System.out.println("Package(69): install2()");
        JSONArray install = packageJson.getJSONArray("install");
        Path installBat = cachePath.resolve("install.bat");
        OutputStream out = Files.newOutputStream(installBat);
        PrintWriter pw = new PrintWriter(out);
        for (int i=0; i<install.length(); i++) {
            pw.println(install.getString(i));
        }        
        pw.close();
        out.close();
        String result = ProcessUtil.batchFile(installBat);
        System.out.println("Package.java(89): result:"+result);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("status")) {
            int status = (int)evt.getNewValue();
            if (status == Download.COMPLETE) {
                try {
                    install2();
                } catch (IOException ex) {
                    Logger.getLogger(Package.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
