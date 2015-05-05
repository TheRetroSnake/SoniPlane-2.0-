package soni.plane.gs.draw;

import soni.plane.api.implement.Window;
import soni.plane.api.tools.FloatRectangle;
import soni.plane.gs.tools.ConfigFile;
import soni.plane.gs.util.App;

public class WindowManager {
    /* Window object associated with this manager */
    private final Window win;
    /* configuration associated with this Window */
    private final ConfigFile conf;
    /* whether or not to show borders on this Window */
    private final boolean showBorders;
	/* position for this Window */
    private FloatRectangle pos;
	/* file location for graphics, etc */
	private final String loc;

    /* variables for border size */
    private int SIZE = 4;
    private int UPPER = 30;

    /* variable to check if we yet initialized this Window */
    private boolean initialized = false;

    /* copy the Window object for later access
     * TODO: include section data and other parameters needed */
    public WindowManager(Window w, ConfigFile c, String location) {
        win = w;
        conf = c;
        showBorders = Boolean.parseBoolean(c.getField("show borders").getValue());
		loc = location.replace("/", ".");
    }

	/* here to only serve custom purposes!
	 * Use with care! */
	@Deprecated
	public WindowManager(){
		win = null;
		loc = null;
		conf = null;
		showBorders = false;
	}

    public void logic() {
        /* if we did not yet do, initialize the Window */
        if(!initialized){
	        initialized = true;
	        /* set new position to avoid nullPointerExceptions and show this has initialized */
            pos = new FloatRectangle();
	        /* initialize the window */
            win.init();
			win.resize(App.getSize().width, App.getSize().height);
        }

		/* run normal logic routine */
		win.logic();
    }

    public void draw() {
        /* draw the DrawRequest */
        win.draw();
    }

	/* close event for a Window */
	public void dispose(){
		win.dispose();
	}

    /* reposition window */
    public void reposition(float x, float y) {
        /* first check x to make sure we wont go out of bounds */
        if(x < (showBorders ? SIZE : 0)){
            x = showBorders ? SIZE : 0;

        } else if(x + pos.width > App.getSize().width - (showBorders ? SIZE * 2 : 0)){
            x -= x + pos.width - App.getSize().width - (showBorders ? SIZE * 2 : 0);
        }

        /* save position */
        pos.x = x;

        /* check y to make sure we wont go out of bounds */
        if(y < (showBorders ? UPPER : 0)){
            y = showBorders ? UPPER : 0;

        } else if(y + pos.height > App.getSize().height - (showBorders ? SIZE + UPPER : 0)){
            y -= y + pos.height - App.getSize().height - (showBorders ? SIZE + UPPER : 0);
        }

        /* save position */
        pos.y = y;
    }

    /* resize application TODO: make sure correct sizes are used */
    public void resize(float width, float height) {
        pos.width = width;
        pos.height = height;
    }

	/* when app has been resized */
	public void resized(int width, int height) {
		if(initialized) {
			win.resize(width, height);
		}
	}

    /* get configuration */
    public ConfigFile getConfig(){
        return conf;
    }

    /* get position and size */
    public FloatRectangle getRectangle(){
        return pos;
    }

	/* return location loaded from */
	public String getLocation(){
		return loc;
	}

	/* get Window stored */
	public Window getWindow() {
		return win;
	}
}
