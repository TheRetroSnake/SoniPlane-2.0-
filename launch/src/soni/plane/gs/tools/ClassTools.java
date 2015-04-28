package soni.plane.gs.tools;

import soni.plane.gs.Loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassTools {
    /* load tool with given name */
    public static void loadModule(String tool) {
        try {
            loadJAR(getJARPath(tool));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

	/* load library with given name */
	public static void loadLibrary(String tool) {
		try {
			loadJAR(tool);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

    /* loads lang in JAR to memory for use */
    private static void loadJAR(String location) throws IOException, ClassNotFoundException, URISyntaxException {
		Loader.display("loading Classes from " + location);
		System.out.print("loading Classes from '" + location +"'");

        /* list of class names */
        ArrayList<String> classNames = new ArrayList<String>();
        /* list of configuration files */
        ArrayList<String> configs = new ArrayList<String>();

        /* get the JAR to this instance */
        ZipInputStream zip = new ZipInputStream(new FileInputStream(location));

        /* get each file in the zip */
        for(ZipEntry entry = zip.getNextEntry();entry != null;entry = zip.getNextEntry()){

            /* if this is a class */
            if(entry.getName().endsWith(".class") && !entry.isDirectory()) {

                // This ZipEntry represents a class. Now, what class does it represent?
                StringBuilder className = new StringBuilder();
                for(String part : entry.getName().split("/")) {
                    if(className.length() != 0) {
                        className.append(".");
                    }

                    className.append(part);
                    if(part.endsWith(".class")) {
                        className.setLength(className.length() - ".class".length());
                    }
                }

                /* add final result to string array */
                classNames.add(className.toString());
            /* if its a configuration file */
            } else if(entry.getName().endsWith(".spc")){
                configs.add(entry.getName());
            }
        }

        /* add url to make sure the classes work */
        ClassContainer.get().addURL(location);

        /* load all the classes */
        for(String cls : classNames){
            /* load next class */
            ClassContainer.get().loadClass(location, cls);
        }

        /* load all the configurations */
        for(String cfg : configs){
            /* load next class */
            ClassContainer.get().loadConfig(location, cfg);
        }

		Loader.latest("loading Classes from "+ location +" ...Done!");
		System.out.println(" ...Done!");
    }

    /* get method inside class */
    public static Method getMethod(Class<?> cls, String main, Class[] params) throws NoSuchMethodException {
        return cls.getMethod(main, params);
    }

    /* get new instance */
    public static Object getInstance(Class<?> cls){
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /* get real path to target JAR */
    public static String getJARPath(String JAR){
        return Loader.getDataFolder() +"modules/"+ JAR +".jar";
    }

    /* load SoniPlane and execute */
    public static void loadSoniPlane() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /* set up correct natives file path for LWJGL */
                    System.setProperty("org.lwjgl.librarypath", new File(Loader.getDataFolder() +"lib/natives").getAbsolutePath());
					System.setProperty("java.library.path", new File(Loader.getDataFolder() +"lib/natives").getAbsolutePath());
                    /* load necessary JARs to memory */
					loadJAR(Loader.getDataFolder() +"lib/gdx.jar");
                    loadJAR(Loader.getDataFolder() +"lib/gdx-backend-lwjgl.jar");
					loadJAR(Loader.getDataFolder() +"lib/gdx-freetype.jar");
                    /* load SoniPlane JAR to memory */
                    loadJAR(Loader.getDataFolder() +"sp.jar");
                    /* get correct Class */
                    Class<?> cls = ClassContainer.get(Loader.getDataFolder() +"sp.jar", "soni.plane.gs.Main");

                    /* start a new timer to hide the main window right after program launches */
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Loader.JFrame().setVisible(false);
                        }
                    }, 300);

                    /* get method and invoke it */
                    getMethod(cls, "main", new Class[]{ String[].class } ).invoke(cls.newInstance(), new Object[]{ Loader.getStoredArguments() });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                if(!Loader.getErrors().equals("")){
                    /* return visibility */
                    Loader.JFrame().setVisible(true);
                    /* inform about an error */
                    Loader.display("Something went wrong. Do you want to send error report?");
                    Loader.display("Press Esc or close application for no, any other button for yes.");

                    /* attempt to fix window not displaying TODO: find a better method */
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Loader.JFrame().setVisible(true);
                        }
                    }, 600);

                    /* TODO: do error report part! */
                    /* for now just restart SoniPlane*/
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Loader.JFrame().setVisible(true);
                            new Thread(new Loader(), "SoniPlane").start();
                        }
                    }, 10000);
                } else {
                    System.out.println("No errors! Shutting down.");
                    /* close application layer */
                    Loader.JFrame().dispose();
                }
            }
        }).start();
    }
}