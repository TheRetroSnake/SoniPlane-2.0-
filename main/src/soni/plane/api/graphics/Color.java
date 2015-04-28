package soni.plane.api.graphics;

import soni.plane.gs.Loader;
import soni.plane.gs.tools.ConfigFile;
import soni.plane.gs.util.FileUtil;
import soni.plane.gs.util.ProjectManager;

public final class Color {
    private final com.badlogic.gdx.graphics.Color c;

	public Color(float r, float g, float b) {
        c = new com.badlogic.gdx.graphics.Color(r, g, b, 1f);
    }

    public Color(float r, float g, float b, float a) {
        c = new com.badlogic.gdx.graphics.Color(r, g, b, a);
    }

	public Color(String file){
		/* set file name first */
		String f = Loader.getDataFolder() +"res/graphics/"+ ProjectManager.get().getDraw().getContext().getLocation() +"/"+ file;
		/* create config */
		ConfigFile cfg = new ConfigFile(f, ConfigFile.READ, FileUtil.read(f));
		c = new com.badlogic.gdx.graphics.Color(readColor(cfg, "r"), readColor(cfg, "g"), readColor(cfg, "b"), readColor(cfg, "a"));
	}

	private float readColor(ConfigFile cfg, String color) {
		return Integer.parseInt(cfg.getField(color).getValue()) / 255f;
	}

	public com.badlogic.gdx.graphics.Color get(){
        return c;
    }

	public float getR(){
		return c.r;
	}

	public float getG(){
		return c.g;
	}

	public float getB(){
		return c.b;
	}

	public float getA(){
		return c.a;
	}

	public void setR(float r){
		c.r = r;
	}

	public void setG(float g){
		c.g = g;
	}

	public void setB(float b){
		c.b = b;
	}

	public void setA(float a){
		c.a = a;
	}

	public String toString(){
		return c.toString();
	}
}