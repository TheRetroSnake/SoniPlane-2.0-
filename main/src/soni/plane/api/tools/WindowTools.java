package soni.plane.api.tools;

import soni.plane.gs.draw.WindowManager;
import soni.plane.gs.util.ProjectManager;

public final class WindowTools {
	/* make new Window from file */
	public static void addWindow(String fileName, Object[] args){
		ProjectManager.get().getDraw().addWindow(fileName, args);
	}

	/* remove this window from existence */
	public static void exit(){
		ProjectManager.get().getDraw().rmvWindow(getContext());
	}

    /* move window */
    public static void move(float x, float y){
        getContext().reposition(x, y);
    }

    /* resize window */
    public static void resize(float width, float height){
        getContext().resize(width, height);
    }

    /* get X position */
    public static float getX(){
        return getContext().getRectangle().x;
    }

    /* get Y position */
    public static float getY(){
        return getContext().getRectangle().y;
    }

    /* get Width position */
    public static float getWidth(){
        return getContext().getRectangle().width;
    }

    /* get Height position */
    public static float getHeight(){
        return getContext().getRectangle().height;
    }

	/* get bounds as FloatRectangle */
	public static FloatRectangle getBounds(){
		return getContext().getRectangle().copy();
	}

	/* gets current context */
	private static WindowManager getContext(){
		return ProjectManager.get().getDraw().getContext();
	}
}
