package soni.plane.api.tools;

import soni.plane.gs.util.App;

public final class AppTools {
    /* get width of the application */
    public static float getWidth(){
        return App.getSize().width;
    }

    /* get height of the application */
    public static float getHeight(){
        return App.getSize().height;
    }
}