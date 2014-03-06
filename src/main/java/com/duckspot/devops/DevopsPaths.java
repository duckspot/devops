package com.duckspot.devops;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Defines Paths used by devops package.
 * @author peter
 */
public class DevopsPaths {

    public static Path get(String s) {
        String devPath = System.getenv("DEV_PATH");
        switch (s) {
            // FOLDERS:
            case "tools": // organized by package
                return Paths.get(devPath, "tools");
            case "packages": // organized by package
                return Paths.get(devPath, "var", "cache", "devops");
            case "packages/partial": // organized by package then moved to packages
                return Paths.get(devPath, "var", "cache", "devops", "partial");

            // FILES:
            case "user.bat":
                return Paths.get(devPath, "user.bat");
            case "tools/setup.bat":
                return Paths.get(devPath, "tools", "setup.bat");
            case "sources.list":
                return Paths.get(devPath, "tools", "devops", "sources.list");

            // FAIL
            default:
                throw new Error("unrecognized file location: '"+s+"'");
        }
    }
}
