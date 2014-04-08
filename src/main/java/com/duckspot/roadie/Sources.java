package com.duckspot.roadie;

import com.duckspot.util.ListFile;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sources {
    
    private List<Source> online = new ArrayList<>();

    private Source sourceFromLine(String line) throws IOException, URISyntaxException {
        int space = line.indexOf(' ');
        String uriString = line;
        String name = line;
        if (space >= 0) {
            uriString = line.substring(0, space);
            name = line.substring(space).trim();
        }
        URI sourceURI = new URI(uriString);
        return new Source(sourceURI, name);
    }
    
    /**
     * Check all the sources in sources.list to see which ones are online.
     * Read tool list from any source where we have no cached tool list.
     */
    public void checkSources() {
        try {
            ListFile list = new ListFile(RoadiePaths.get("sources.list"));
            online.clear();
            try {
                while (true) {
                    String line = list.nextLine();
                    try {
                        Source source = sourceFromLine(line);
                        if (source.isCached()) {
                            if (source.isOnline()) {
                                online.add(source);
                            }
                        } else {
                            if (source.sourceToCache()) {
                                online.add(source);
                            }
                        }
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(Sources.class.getName())
                                .log(Level.SEVERE,String.format(
                                        "sources.list(%d) invalid URI",
                                        list.getLineNumber()), ex);
                    }
                }
            } catch (NoSuchElementException ex) {
                // exception ends loop
            }
        } catch (IOException ex) {
            Logger.getLogger(Sources.class.getName())
                    .log(Level.SEVERE,"Error reading sources.list", ex);
        }
    }
    
    public void writeSourcesOnline() {
        try {
            BufferedWriter out = Files.newBufferedWriter(
                    RoadiePaths.get("sources.online"), 
                    Charset.defaultCharset());
            try {
                for (Source source: online) {                
                    out.write(source.toLine());
                }
            } finally {
                out.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Sources.class.getName())
                    .log(Level.SEVERE,"Error writing sources.list", ex);
        }
    }
    
    public void readSourcesOnline() {
        try {
            ListFile list = new ListFile(RoadiePaths.get("sources.online"));
            try {
                while (true) {
                    String line = list.nextLine();                
                    try {
                        online.add(sourceFromLine(line));
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(Sources.class.getName())
                                .log(Level.SEVERE,String.format(
                                        "sources.online(%d) invalid URI",
                                        list.getLineNumber()), ex);
                    }                
                }
            } catch (NoSuchElementException ex) {
                // exception ends loop
            }
        }
        catch (NoSuchFileException ex) {
            // ignore
        }
        catch (FileNotFoundException ex) {
            // ignore
        }
        catch (IOException ex) {
            Logger.getLogger(Sources.class.getName())
                    .log(Level.SEVERE,"Error reading sources.list", ex);
        }
    }
    
    public List<Source> getOnline() {
        return online;
    }
}
