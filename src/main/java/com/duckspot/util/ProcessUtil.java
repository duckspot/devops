package com.duckspot.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessUtil {
    
    static String batchFile(Path batchFile) throws IOException {
        StringBuilder result = new StringBuilder();
        ProcessBuilder pb = new ProcessBuilder(batchFile.toString());
        pb.redirectErrorStream(true);
        Process p = pb.start();
        InputStream in = p.getInputStream();
        Scanner s = new Scanner(in);
        try {
            p.waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcessUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (s.hasNextLine()) {
            result.append(s.nextLine()+"\n");
        }
        s.close();
        in.close();
        return result.toString();
    }
}
