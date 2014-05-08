package com.duckspot.roadie.download;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

/**
 * Download represents a download job to be performed by Downloader.  
 * A Download job has a srcURL and dstPath that describe where the file is 
 * downloaded from, and stored to.  It also has a 'status' - one of NEW, QUEUED,
 * STARTED, COMPLETE, FAILED.
 */
public class Download {
    
    /* the order of these matters, as DownloadGroup uses it to watch progress
       through the status states, advancing it's state but never moving it's
       state backwards.
    */
    public static final int NEW = 0;
    public static final int QUEUED = 1;
    public static final int STARTED = 2;
    public static final int COMPLETE = 3;
    public static final int FAILED = 4;
    
    private int status = 0;
    private Path dstPath;
    private URL srcURL;
    private int bytes = 0;
    
    private PropertyChangeSupport mPcs =
        new PropertyChangeSupport(this);
    
    public Download(File destFile, URL srcURL) {
        this.dstPath = destFile.toPath();
        this.srcURL = srcURL;
    }

    public Download(Path dstPath, URL srcURL) {
        this.dstPath = dstPath;
        this.srcURL = srcURL;
    }

    public Download(Path dstPath, URI srcURI) throws MalformedURLException {
        this.dstPath = dstPath;
        this.srcURL = srcURI.toURL();
    }
    
    public void
    addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }
    
    public void
    removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }
    
    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }
        
    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        int oldStatus = this.status;
        this.status = status;
        mPcs.firePropertyChange("status", oldStatus, status);
    }

    /**
     * @return the destFile
     */
    public File getDestFile() {
        return dstPath.toFile();
    }

    /**
     * @param destFile the destFile to set
     */
    public void setDestFile(File destFile) {        
        setDstPath(destFile.toPath());
    }

    /**
     * @return the dstPath
     */
    public Path getDstPath() {
        return dstPath;
    }

    /**
     * @param dstPath the dstPath to set
     */
    public void setDstPath(Path dstPath) {
        Path oldDstPath = this.dstPath;        
        this.dstPath = dstPath;
        mPcs.firePropertyChange("dstPath", oldDstPath, dstPath);
    }

    /**
     * @return the srcURL
     */
    public URL getSrcURL() {
        return srcURL;
    }

    /**
     * @param srcURL the srcURL to set
     */
    public void setSrcURL(URL srcURL) {
        URL oldSrcURL = this.srcURL;
        this.srcURL = srcURL;
        mPcs.firePropertyChange("srcURL", oldSrcURL, srcURL);
    }

    /**
     * @return the bytes
     */
    public int getBytes() {
        return bytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setBytes(int bytes) {
        int oldBytes = this.bytes;
        this.bytes = bytes;
        mPcs.firePropertyChange("bytes", oldBytes, bytes);
    }
}