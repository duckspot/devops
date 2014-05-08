package com.duckspot.roadie.download;

import com.duckspot.util.FileUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Downloader takes download jobs and performs them in the background.
 */
public class Downloader implements Runnable {

    private static boolean BACKGROUND = false;
    
    private static Downloader instance;
    
    public static Downloader getInstance() {
        if (instance == null) {
            instance = new Downloader();
        }
        return instance;
    }
    
    private int maxThreads = 4;
    private int nThreads = 0;
    
    private List<Download> queue = new ArrayList();
    private List<Download> inProcess = new ArrayList();
    
    private Downloader() {        
    }
    
    public void download(Download download) {
        
        byte buffer[] = new byte[10240]; 
        /* chose to allocate and free buffer for each file as it makes code
         * simplier - and thereby more reliable - 
         * at an insigificant performance cost
         */
        InputStream in = null;
        OutputStream out = null;
        int totalBytes = 0;
                
        download.setStatus(Download.STARTED);        
        inProcess.add(download);
        try {
            in = download.getSrcURL().openStream();

            FileUtil.checkDirectory(download.getDstPath().getParent());
            out = Files.newOutputStream(download.getDstPath());
            int nBytes;
            while (true) {
                nBytes = in.read(buffer);
                if (nBytes <= 0) {
                    break;
                }
                
                out.write(buffer, 0, nBytes);
                totalBytes += nBytes;
                download.setBytes(totalBytes);                
            } while (nBytes > 0);
        } catch (IOException ex) {
            Logger.getLogger(com.duckspot.roadie.Package.class.getName())
                    .log(Level.SEVERE, "download failed", ex);
            download.setStatus(Download.FAILED);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    throw new Error("unexpected exception", ex);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    throw new Error("unexpected exception", ex);
                }
            }
        }
        inProcess.remove(download);
        download.setStatus(Download.COMPLETE);
    }
    
    public synchronized void request(Download download) {
        
        if (BACKGROUND) {
            queue.add(download);
            download.setStatus(Download.QUEUED);        
            if (!maxThreadsStarted()) {
                startThread();
            }
        } else {
            download(download);
        }
    }

    protected boolean maxThreadsStarted() {
        return nThreads >= maxThreads;
    }
    
    protected synchronized void endThread() {
        nThreads--;
        this.notifyAll();
    }
    
    protected synchronized void startThread() {
        nThreads++;
        new Thread(this).start();
    }
    
    private synchronized Download next() {
        if (queue.size() > 0) {
            return queue.remove(0);
        }
        return null;
    }
    
    @Override
    public void run() {
        
        Download download = next();
        
        System.out.println("download:"+download.getSrcURL());
        
        while (download != null) {
            
            download(download);
            download = next();
        }
        endThread();
    }
    
    public synchronized void waitForDownloads() {
        while (nThreads > 0) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                // ignore
            }
        }
    }
}