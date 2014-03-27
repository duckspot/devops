package com.duckspot.roadie;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Defines Paths used by devops package.
 * @author peter
 */
public class RoadiePaths {

    private static String devopsPath;
    private static String devopsDrive;
    
    static {
        if (devopsPath == null) {
            devopsPath = System.getenv("DEVOPS");
            if (devopsPath == null) {
                devopsPath = System.getenv("DEV_PATH");
                if (devopsPath == null) {
                    devopsPath = Paths.get("").toAbsolutePath().toString();
                }
            }       
        }
    }
    
    public static void setDevRoot(String path) {
        devopsPath = path;
        devopsDrive = null;
    }
    
    public static String getDrive() {        
        if (devopsDrive == null && devopsPath != null 
                && devopsPath.length() > 1 && devopsPath.charAt(1) == ':') {
            devopsDrive = devopsPath.substring(0, 1);
        }
        return devopsDrive;
    }
    
    public static Path get(String s) {
        switch (s) {
            // FOLDERS:
            case "": // root
                return Paths.get(devopsPath);
            case "tools": // organized by package
                return Paths.get(devopsPath, "tools");
            case "packages": // organized by package
                return Paths.get(devopsPath, "var", "cache", "devops");
            case "packages/partial": // organized by package then moved to packages
                return Paths.get(devopsPath, "var", "cache", "devops", "partial");

            // FILES:
            case "setup.bat":
                return Paths.get(devopsPath, "setup.bat");
            case "user.bat":
                return Paths.get(devopsPath, "user.bat");
            case "tools/setup.bat":
                return Paths.get(devopsPath, "tools", "setup.bat");
            case "sources.list":
                return Paths.get(devopsPath, "tools", "devops", "sources.list");

            // FAIL
            default:
                throw new Error("unrecognized file location: '"+s+"'");
        }
    }
}
