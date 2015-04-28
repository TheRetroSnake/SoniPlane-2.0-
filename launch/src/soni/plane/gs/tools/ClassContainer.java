package soni.plane.gs.tools;

import java.net.URL;
import java.net.URLClassLoader;

public class ClassContainer {
    /* main ClassLoader */
    private static ClassLoader cll = new ClassLoader(new URL[0], URLClassLoader.getSystemClassLoader());

    /* get the URLClassLoader */
    public static ClassLoader get(){
        return cll;
    }

    /* get Class with specified name from file*/
    public static Class get(String file, String name) {
        return cll.getClass(file, name);
    }

    public static boolean isLoaded(String location) {
        for(URL u : cll.getURLs()){
            if(u.getFile().equals("/"+ location.replace("\\", "/"))){
                return true;
            }
        }

        return false;
    }
}
