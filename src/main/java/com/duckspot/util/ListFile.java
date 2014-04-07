package com.duckspot.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * ListFile is responsible for reading lines from an InputStream and stripping
 * out everything after the first # on each line, and stripping out
 * blank lines.
 */
public class ListFile {
   
    private Scanner src;    
    private InputStream is;
    private int lineNumber;
    
    public ListFile(Path path) throws IOException {
        is = Files.newInputStream(path);
        src = new Scanner(is);
    }
    
    public ListFile(InputStream is) {
        src = new Scanner(is);
    }
    
    private String nextOrEmptyLine() throws NoSuchElementException, IOException {
        String s;
        try {
            s = src.nextLine();
        } catch (NoSuchElementException ex) {
            if (is != null) {
                is.close();
                is = null;
            }
            throw ex;
        }
        int hash = s.indexOf('#');
        if (hash < 0) {
            return s.trim();
        } else {
            return s.substring(0, hash).trim();
        }
    }

    /**
     * returns the next non-empty line (after removing comments), or
     * 
     * @return next non-empty line
     * @throws NoSuchElementException when end of file is reached
     */
    public String nextLine() throws NoSuchElementException, IOException {
        String s = nextOrEmptyLine();
        while (s.length() == 0) {
            s = nextOrEmptyLine();
        }
        lineNumber++;
        return s;
    }
    
    public List<String> asList() throws IOException {
        List<String> result = new ArrayList<String>();
        try {
            while (true) {
                result.add(nextLine());
            }
        } catch (NoSuchElementException ex) {
            return result;
        }
    }
    
    public int getLineNumber() {
        return lineNumber;
    }
}