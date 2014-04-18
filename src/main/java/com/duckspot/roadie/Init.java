package com.duckspot.roadie;

import com.duckspot.util.ListFile;
import com.duckspot.swing.PrintBuilder;
import com.duckspot.util.FileUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.NoSuchElementException;

/**
 * Init is responsible for setting up roadie so it's ready to accept the 
 * 'do install' command to install more packages.
 */

public class Init {

    private static PrintBuilder output;
    
    public static void setOutput(PrintBuilder printBuilder) {
        output = printBuilder;
    }
    
    public static void copyResource(String resource) {
        assert resource != null;
        Path dest = RoadiePaths.get(resource);
        assert dest != null;
        try {
            FileUtil.checkDirectory(dest.getParent());            
            Files.copy(resource.getClass().getResourceAsStream("/dev/"+resource), 
                    dest, REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new Error("can't overwrite '"+dest+"'", ex);
        }
    }
    
    public static void output(String s) {
        try {
            if (output != null) {
                output.append(s);
            } else {
                System.out.print(s);
            }
        } catch (IOException ex) {
            throw new Error("unexpected exception", ex);
        }
    }
    
    public boolean isInstalled() throws IOException {
        ListFile list = new ListFile(
                getClass().getResourceAsStream("/dev/file.list"));
        try {
            while (true) {
                if (!Files.exists(RoadiePaths.get(list.nextLine()))) {
                    return false;
                }
            }
        } catch (NoSuchElementException ex) {
            return true;
        }        
    }
    
    public void install() throws IOException {
        
        output("copy resources ...");
        ListFile list = new ListFile(
                getClass().getResourceAsStream("/dev/file.list"));
        try {
            while (true) {
                copyResource(list.nextLine());
            }
        } catch (NoSuchElementException ex) {
            System.out.println("ex:"+ex);
        }
        output("\ndone.");
    }
}