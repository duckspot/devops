package com.duckspot.roadie.download;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

/**
 * Download represents a download job to be performed by Downloader.  
 * A Download job has a srcURL and dstPath that describe where the file is 
 * downloaded from, and stored to.  It also has a 'status' - one of NEW, QUEUED,
 * STARTED, COMPLETE, FAILED.
 */
public class Download {
    
    public static final int NEW = 1;
    public static final int QUEUED = 1;
    public static final int STARTED = 2;
    public static final int COMPLETE = 3;
    public static final int FAILED = 4;
    
    private int status = 0;
    private Path dstPath;
    private URL srcURL;
    
    public Download(File destFile, URL srcURL) {
        this.dstPath = destFile.toPath();
        this.srcURL = srcURL;
    }

    public Download(Path dstPath, URL srcURL) {
        this.dstPath = dstPath;
        this.srcURL = srcURL;
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
        this.status = status;
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
        this.dstPath = destFile.toPath();
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
        this.dstPath = dstPath;
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
        this.srcURL = srcURL;
    }
}