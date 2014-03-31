package com.duckspot.roadie;

import com.duckspot.util.ListFile;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Sources {
    
    private List<Source> online = new ArrayList<>();

    private Source sourceFromLine(String line) throws MalformedURLException, IOException {
        int space = line.indexOf(' ');
        String urlString = line;
        String name = line;
        if (space >= 0) {
            urlString = line.substring(0, space);
            name = line.substring(space).trim();
        }
        return new Source(new URL(urlString), name);
    }
    
    /**
     * Check all the sources in sources.list to see which ones are online.
     * Read tool list from any source where we have no cached tool list.
     */
    public void checkSources() throws IOException {
        ListFile list = new ListFile(RoadiePaths.get("sources.list"));
        try {
            while (true) {
                Source source = sourceFromLine(list.nextLine());
                if (source.isCached()) {
                    if (source.isOnline()) {
                        online.add(source);
                    }
                } else {
                    if (source.attemptCache()) {
                        online.add(source);
                    }
                }
            }
        } catch (NoSuchElementException ex) {
            // exception ends loop
        }
    }
    
    public void writeSourcesOnline() throws IOException {
        BufferedWriter out = Files.newBufferedWriter(
                RoadiePaths.get("sources.online"), Charset.defaultCharset());
        try {
            for (Source source: online) {                
                out.write(source.toLine());
            }
        } finally {
            out.close();
        }
    }
    
    public void readSourcesOnline() throws IOException {
        ListFile list = new ListFile(RoadiePaths.get("sources.online"));
        try {
            while (true) {
                Source source = sourceFromLine(list.nextLine());
                online.add(source);                
            }
        } catch (NoSuchElementException ex) {
            // exception ends loop
        }
    }
    
    /**
     * Reread tool lists from online sources where our tool list may be out of
     * date.
     */
    public void refreshTools() {
        for (Source source: online) {
            source.refreshCache();
        }
    }    
}
