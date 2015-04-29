package soni.plane.gs;


import soni.plane.gs.tools.ClassTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Loader implements Runnable {
    /* folder to save files to TODO: Check if this works on all OS */
    private static final String folder = System.getenv("APPDATA") +"/.SoniPlane/";
    private static boolean render = false;
    private static Launch l;

    /* list of texts to display on screen */
    public static ArrayList<String> texts = new ArrayList<String>();
    /* version number */
    private static final String version = "2.0";
    /* web address to download files from */
    private static final String updateAdr = "http://discocentral.digibase.ca/SoniPlane2/update/";
    /* gets start of OS name. Win, Mac, Linus, SunOS or FreeBSD (some others exist, but fuck them, nobody uses anyway, right? RIGHT?) */
    private static final String OS = System.getProperty("os.name").split(" ")[0].replace("dows", "");
    /* stored arguments */
    private static String[] storeArg;
    /* store any errors */
    private static String error = "";

    public static void main(String[] args) {
        /* remember arguments to pass on later */
        storeArg = args;

         /* set error stream to custom one */
        System.setErr(new PrintStream(new DisplayStream(System.err), true));
        /* create new JFrame */
        l = new Launch();
        /* create logic Thread */
        new Thread(new Loader(), "SoniPlane").start();
    }

    /* update the application */
    private static void update() {

    }

    /* check if we need to update and if SoniPlane works correctly */
    private static void checkUpdates() {

    }

    @Override
    public void run() {
        /* if the storage folder exists */
        if(!new File(folder).exists()){
                /* create the folder and update */
            new File(folder).mkdir();
            update();

        } else {
            /* check if we need to update */
            checkUpdates();
        }

        /* load SoniPlane */
        display("Loading SoniPlane");
        ClassTools.loadCore();
    }

    /* display text */
    public static void display(String text) {
        texts.add(texts.size(), text);
		l.repaint();
    }

    /* replace latest text */
    public static void latest(String text) {
        texts.set(texts.size() - 1, text);
		l.repaint();
    }

    /* return main JFrame */
    public static JFrame JFrame() {
        return l;
    }

    /* the Jframe controller class */
    private static class Launch extends JFrame {
        public Launch(){
            /* set visible */
            setVisible(true);

            /* set title and resizable */
            setTitle("Loading...");
            setResizable(true);

            /* new JPanel */
            add(new Surface());

            /* set closing operation */
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pack();

            /* set size */
            setSize(800, 600);

            /* set listeners */
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    super.windowOpened(e);
                    /* allow rendering */
                    render = true;
                    /* render the screen */
                    l.repaint();
                    /* set normal title */
                    setTitle("Launching SoniPlane");
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    /* exit normally */
                    exit(0);
                }
            });
        }
    }

    /* the JPanel controller class */
    private static class Surface extends JPanel {
        @Override
        public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);

            /* if we can render, render the app */
            if (render) {
                /* set color to black for text */
                g.setColor(Color.BLACK);

                if(texts.size() > 0) {   // make sure the text length is greater than 0, so don't crash
                    /* Get array of strings of what we want to render */
                    String[] t = texts.toArray(new String[texts.size()]);
                    /* set initial y-offset */
                    int y = 28;

                    /* main loop for drawing */
                    for (int i = t.length - 1;i >= 0;i --) {
                        /* draw next line to application height - y-offset */
                        g.drawString(t[i], 4, l.getHeight() - y);
                        /* increase y-offset by text height */
                        y += 12;
                    }
                }
            }
        }
    }

    /* exit program */
    public static void exit(int error){
        /* call dispose on the JFrame */
        l.dispose();
        /* exit */
        System.exit(error);
    }

    private static class DisplayStream extends FilterOutputStream {
        /* Strings to remember error messages for later use */
        String flush = "";

        /* set up error stream */
        public DisplayStream(PrintStream err) {
            super(err);
        }

        /* write the bytes to temporary String */
        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            flush += new String(b, off, len);
        }

        /* write the Strings to display */
        @Override
        public void flush() {
            /* if the String ends with end marker, display it */
            if(flush.endsWith("\r\n")){
                /* display error */
                display(flush.replace("\t", "        "));
				System.out.println(flush.replace("\r\n", ""));

                /* remember the error */
                error += flush;
                /* clear the temporary variable */
                flush = "";
                /* make sure everything displays */
                l.repaint();
            }
        }
    }

    /* get data folder location */
    public static String getDataFolder() {
        return folder;
    }

    /* return starting arguments for this program */
    public static String[] getStoredArguments() {
        return storeArg;
    }

    /* get errors this instance */
    public static String getErrors() {
        return error;
    }
}
