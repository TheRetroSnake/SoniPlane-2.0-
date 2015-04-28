package soni.plane.gs.util;

import com.badlogic.gdx.Gdx;
import org.lwjgl.opengl.Display;
import soni.plane.gs.Main;

import java.awt.*;

public class App {
    /* gets application size in a Rectangle */
    public static Rectangle getSize(){
        return new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
