package com.duckspot.devops.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * do
 *
 */
public class App 
{
    static final String fmt = "https://raw.github.com/duckspot/devops-env/"
            + "master/packages/%s.json";
    
    static void install( String packageName ) {
        try {
            URL url = new URL(String.format(fmt, packageName ));
            JSONTokener jt = new JSONTokener(url.openStream());
            JSONObject jo = new JSONObject(jt);
            System.out.println(jo);
        } catch (MalformedURLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main( String[] args )
    {
        if (args.length >= 2 && args[0].equalsIgnoreCase("install")) {
            install(args[1]);
        }
    }
}
