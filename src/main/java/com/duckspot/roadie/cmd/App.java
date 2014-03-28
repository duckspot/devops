package com.duckspot.roadie.cmd;

import com.duckspot.swing.PrintBuilder;
import com.duckspot.roadie.swing.InstallForm;
import com.duckspot.roadie.Init;
import com.duckspot.roadie.Tool;
import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    static PrintBuilder printBuilder = new PrintBuilder();
    
    public static void foo( String[] args )
    {
        
    }
    
    private static Scanner stdin;
    
    private static boolean installCorrect() {
        return  System.getenv("DEV_DRIVE_LETTER") != null &&
                System.getenv("DEV_PATH") != null &&
                System.getenv("DEV_TOOLS") != null;        
    }
    
    private static String nextLine() {
        if (stdin == null) {
            stdin = new Scanner(System.in);
        }
        return stdin.nextLine().trim();
    }    
    
    public static boolean askInstall() {
        System.out.print("Do you want to perform "
                + "initial installation of devops now? N: ");
        return nextLine().toLowerCase().startsWith("y");
    }

    public static String askLocation() {
        System.out.println("Where shall I install devops? ");
        return nextLine();
    }
    
    public static void guiInstall() {
        
        /* Create and display the install form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InstallForm().setVisible(true);
            }
        });                
    }
    
    public static void main( String[] args ) throws IOException
    {
        if (args.length == 0) {
            InstallForm.main(args);
            return;
        }
        
        System.out.println("roadie");
        if (!installCorrect()) {
            if (askInstall()) {
                Init.install(askLocation());
            }
            return;
        }
        
        switch (args[0].toLowerCase()) {
            case "init":
                System.out.println("init");
                if (args.length == 1) {
                    Init.install();
                } else {
                    Init.install(args[1]);
                }

            case "install":
                if (args.length != 2) {
                    throw new Error("install command requires one argument: package-name");
                }
                System.out.printf("install %s", args[1]);
                new Tool(args[1]).install();
                break;

            default:
                System.out.println("unrecognized command.");
        }            
    }
}
