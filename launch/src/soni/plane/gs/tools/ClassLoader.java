package soni.plane.gs.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.jar.JarFile;

public class ClassLoader extends URLClassLoader {
    /* create instance of this ClassLoader */
    public ClassLoader(URL[] urls, java.lang.ClassLoader parent) {
        super(urls, parent);
    }

    /* add following JAR to class lookup table */
    public void addURL(String url) throws MalformedURLException {
        super.addURL(new File(url).toURI().toURL());
    }

    /* load class to memory with specified name */
    public Class<?> loadClass(String file, String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    /* returns a class with specified name if loaded */
    public Class<?> getClass(String file, String name) {
		/* if file to load class from is loaded */
		if (ClassContainer.isLoaded(file)) {
			/* try to find the specific class */
			Class cls = this.findLoadedClass(name);

			/* if class is not null, return it */
			if (cls != null) {
				return cls;
			}
		}

        /* else return nothing */
		System.err.println(ClassContainer.class.getName() + " Warning, Class with name '" + name + "' not found!");
		return null;
	}

	/* loads configuration files from JAR files */
    public ConfigFile loadConfig(String file, String name) throws URISyntaxException, IOException {
        for(URL u : super.getURLs()){
            /* if we are looking at correct file */
            if(u.getFile().equals("/"+ file.replace("\\", "/"))) {
                /* get JarFile from the URL (this is done to prevent loading files from unloaded JARs) */
                JarFile j = new JarFile(new File(u.toURI()));
                /* if contains specified file */
                if (j.getEntry(name) != null) {

                    /* get the correct inputStream */
                    InputStream is = j.getInputStream(j.getEntry(name));
                    /* create ConfigFile with following parameters */
                    ConfigFile c = new ConfigFile(file, name, ConfigFile.READ, readZipContents(is));
                    /* close InputStream */
                    is.close();
                    return c;
                }
				System.err.println(ClassContainer.class.getName()+" Warning, JAR entry with name '"+name+"' for file '"+file+"' not found!");
				return null;
            }
        }

		System.err.println(ClassContainer.class.getName() + " Warning, URL for file '" + file + "' not found!");
        return null;
    }

	/* loads configuration files from JAR files */
	public ConfigFile getUnloadedConfig(String file, String name) throws URISyntaxException, IOException {
        /* get JarFile from the URL (this is done to prevent loading files from unloaded JARs) */
		JarFile j = new JarFile(new File(file));
        /* if contains specified file */
		if (j.getEntry(name) != null) {

            /* get the correct inputStream */
			InputStream is = j.getInputStream(j.getEntry(name));
            /* create ConfigFile with following parameters */
			ConfigFile c = new ConfigFile(file, name, ConfigFile.READ, readZipContents(is));
            /* close InputStream */
			is.close();
			return c;
		}

		System.err.println(ClassContainer.class.getName() + " Warning, JAR entry with name '" + name + "' for file '" + file + "' not found!");
		return null;
	}

    /* used to quickly read contents of inputStream supplied by JarFile */
    private static String readZipContents(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    String data = s.hasNext() ? s.next() : "";
	    s.close();
        return data;
    }
}
