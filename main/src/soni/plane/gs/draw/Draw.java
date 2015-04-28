package soni.plane.gs.draw;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import soni.plane.api.exceptions.plugin;
import soni.plane.api.implement.Window;
import soni.plane.api.java.io.File;
import soni.plane.api.tools.Arguments;
import soni.plane.gs.Main;
import soni.plane.gs.tools.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Draw {
    /* list of all WindowManagers */
    private ArrayList<WindowManager> win;
	private static ArrayList<WindowManager> glob;
    /* latest WindowManager context */
    private static WindowManager context;

    /* initialize this list */
    public Draw init() {
        /* create new ArrayList */
        win = new ArrayList<WindowManager>();
		/* if global is not created yet, do so */
		if(glob == null){
			glob = new ArrayList<WindowManager>();

			try {
        		/* create the project menu control object */
				WindowManager t = generateWindowManager("window/ProjectManager");
        		/* add this WindowManager to list, with proper context */
				setContext(t);
				glob.add(t);
				setContext(null);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

		return this;
    }

    /* creates new WindowManager from give JAR path */
    private WindowManager generateWindowManager(String JARPath) throws IOException, URISyntaxException {
		/* loop for all libraries required */
		for(String file : ClassContainer.get().
				getUnloadedConfig(ClassTools.getJARPath(JARPath), "module.spc").getField("libraries").getValue().split("#")){
			/* make a file of the absolute address */
			File f = new File(File.getMainFolder() + file);

			/* make sure file exists and is a file */
			if(f.exists() && f.isFile()){
				/* load the library */
				ClassTools.loadLibrary(f.getFilePath());
			}
		}

        /* make sure the module is loaded */
		ClassTools.loadModule(JARPath);
        /* get default configuration file */
        ConfigFile c = ClassContainer.get().loadConfig(ClassTools.getJARPath(JARPath), "module.spc");

        /* make sure this is correct type of Window */
        if(c.getField("type").getValue().equals(Window.class.getName())) {
			WindowManager w = new WindowManager((Window) ClassTools.getInstance(
					ClassContainer.get(ClassTools.getJARPath(JARPath), c.getField("class").getValue())), c, JARPath);

            /* finally create new WindowManager */
            return w;
        }

        /* incorrect type. Send error */
        plugin.TypeError(Window.class.getName(), c.getField("type").getValue(), this.getClass() +".generateWindowManager("+ JARPath +")");
        return null;
    }

    /* draw Windows */
    public Draw draw(){
        /* run for all WindowManagers */
        for(WindowManager m : createFullList()){
            /* make sure has initialized (if hasn't, skip draw step) */
            if(m.getRectangle() != null) {
	            soni.plane.api.graphics.Font.clearFont();
                /* set window context */
                setContext(m);
				/* set clipping */
            	if(clip(m)) {
					/* draw the window */
					m.draw();
					/* flush the clipped sprites */
					unClip();
				}
			}
        }

        /* finally clear context */
        setContext(null);
	    soni.plane.api.graphics.Font.clearFont();
		return this;
    }

	/* create list of WindowManagers with global and non-global Windows */
	private WindowManager[] createFullList() {
		ArrayList<WindowManager> w = new ArrayList<WindowManager>();

		/* first do global ones */
		for(WindowManager m : glob){
			w.add(m);
		}

		/* and then non-global */
		for(WindowManager m : win){
			w.add(m);
		}

		/* return as an array */
		return w.toArray(new WindowManager[w.size()]);
	}

	/* remove Scissor from stack and flush batch calls */
	private void unClip() {
		Main.getBatch().flush();
		ScissorStack.popScissors();
	}

	/* apply clipping */
	private boolean clip(WindowManager m) {
		Rectangle s = new Rectangle();

		/* calculate Scissor */
		ScissorStack.calculateScissors(Main.getCamera(), Main.getBatch().getTransformMatrix(), m.getRectangle().toRectangle(), s);
		/* set Scissor and check if it was successful */
		return ScissorStack.pushScissors(s);
	}

	/* do Window logic */
    public Draw logic(float delta) {
         /* run for all WindowManagers */
        for(WindowManager m : createFullList()){
	        soni.plane.api.graphics.Font.clearFont();
            /* do window logic */
            setContext(m);
            m.logic();
        }

        /* finally clear context */
        setContext(null);
	    soni.plane.api.graphics.Font.clearFont();

		return this;
    }

	/* add Window
	 * this should NOT be used, but instead addWindow(String)
	 * it does everything for you and leaves less room for error
	 * only use this method if you want to supply a pseudo-Window
	 * and you can provide all necessary data */
	@Deprecated
    public Window addWindow(Window w, ConfigFile c, String location, boolean global){
		/* create WindowManager */
		WindowManager t = new WindowManager(w, c, location);

		WindowManager ctx = getContext();
		setContext(t);
		/* if is global, add to global context instead */
		if(global){
			glob.add(t);
		/* else add to non-global */
		} else {
			win.add(t);
		}
		setContext(ctx);
        return w;
    }

	/* add Window from given path (relative to SoniPlane folder) */
	public Window addWindow(String JARPath, Object[] args){
		try {
			/* create the project menu control object */
			WindowManager t = generateWindowManager(JARPath);
			Arguments.add(t.getWindow(), args);

			WindowManager ctx = getContext();
        	/* add this WindowManager to list, with proper context */
			setContext(t);
			win.add(t);
			setContext(ctx);
			return t.getWindow();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return null;
	}

    /* set latest context */
    public void setContext(WindowManager context){
        Draw.context = context;
    }

    /* get latest context */
    public WindowManager getContext(){
        return context;
    }

	/* called when app is resized */
	public void resize(int w, int h) {
		/* loop for all Windows */
		for(WindowManager m : createFullList()){
			/* set context and resize */
			setContext(m);
			m.resized(w, h);
		}

		/* finally clear context */
		setContext(null);
	}

	/* remove specific Window */
	public void rmvWindow(WindowManager w) {
		w.dispose();
		win.remove(w);
		/* remove arguments */
		Arguments.remove(w.getWindow());
	}
}