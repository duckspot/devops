package com.duckspot.roadie;

import com.duckspot.swing.PrintBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Init is responsible for setting up roadie so it's ready to accept the 
 * 'do install' command to install more packages.
 */

public class Init {

    private static PrintBuilder output;
    private static String installTo;
    
    public static void setOutput(PrintBuilder printBuilder) {
        output = printBuilder;
    }
    
    public static void checkDirectory(Path path) {
        
        if (Files.exists(path)) {
            if (!Files.isDirectory(path)) {
                throw new Error("'"+path+"' must be a directory");
            }
        } else {
            try {
                Files.createDirectories(path);
            } catch (IOException ex) {
                throw new Error("can't create directory '"+path+"'", ex);
            }
        }
    }
    
    public static void copyResource(String resource) {
        assert resource != null;
        Path dest = RoadiePaths.get(resource);
        assert dest != null;
        try {
            checkDirectory(dest.getParent());
            Files.copy(resource.getClass().getResourceAsStream(resource), 
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
    public static void end() {
        if (output != null) {
            output.setComplete(true);
        }
    }
    
    public static void install() {

        output("copy resources ...");
        copyResource("setup.bat");
        copyResource("tools/setup.bat");
        copyResource("tools/do.bat");
        output("\ndone.");
        end();
    }
    
    public static void install(String devPath) {
        output(String.format("installing roadie to '%s'\n", devPath));
        RoadiePaths.setDevRoot(devPath);
        install();
    }
}