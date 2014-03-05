package com.duckspot.devops.cmd;

import java.io.IOException;

/**
 * do
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        if (args.length >= 2 && args[0].equalsIgnoreCase("install")) {
            new Package(args[1]).install();
        } else {
            System.out.println("unrecognized command.");
        }
    }
}
