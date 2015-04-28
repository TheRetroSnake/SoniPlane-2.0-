package soni.plane.api.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import soni.plane.api.tools.FloatRectangle;
import soni.plane.gs.Loader;
import soni.plane.gs.Main;
import soni.plane.gs.util.ProjectManager;

import java.io.File;
import java.io.FilenameFilter;

public final class Font {
	/* context for latest FontEntry */
	private static soni.plane.gs.draw.Font.FontEntry ctx = null;
	/* list of applicable font characters */
	private static final String FONT_CHARACTERS =
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

	/* java.awt.Font @MagicConstant copied values */
	public static final int PLAIN =		java.awt.Font.PLAIN;
	public static final int BOLD =		java.awt.Font.BOLD;
	public static final int ITALIC =	java.awt.Font.ITALIC;

	/* sets font color */
	public static void setColor(Color c){
		if(ctx != null) {
			ctx.setColor(c);

		} else {
			throw new IllegalStateException(Font.class.getName() +": No font set! Fix your code, bud!");
		}
	}

	/* sets font scale */
	public static void setScale(float x, float y){
		if(ctx != null) {
			ctx.getFont().setScale(x, y);

		} else {
			throw new IllegalStateException(Font.class.getName() +": No font set! Fix your code, bud!");
		}
	}

	/* draws String normally */
	public static void draw(String str, float x, float y){
		if(ctx != null) {
			ctx.getFont().setColor(ctx.getColor().get());
			ctx.getFont().draw(Main.getBatch(), str, getRectangle().x + x, getRectangle().y + y);

		} else {
			throw new IllegalStateException(Font.class.getName() +": No font set! Fix your code, bud!");
		}
	}

	/* draws String with color */
	public static void draw(String str, float x, float y, Color c){
		if(ctx != null) {
			ctx.getFont().setColor(c.get());
			ctx.getFont().draw(Main.getBatch(), str, getRectangle().x + x, getRectangle().y + y);

		} else {
			throw new IllegalStateException(Font.class.getName() +": No font set! Fix your code, bud!");
		}
	}

	public static void drawCentered(String str, FloatRectangle bounds){
		if(ctx != null) {
			ctx.getFont().setColor(ctx.getColor().get());
			ctx.getFont().draw(Main.getBatch(), str, getCenter(getRectangle().x + bounds.x, bounds.width, getWidth(str)),
					getCenter(getRectangle().y + bounds.y, bounds.height, getHeight(str)));

		} else {
			throw new IllegalStateException(Font.class.getName() +": No font set! Fix your code, bud!");
		}
	}

	public static float getHeight(String str){
		if(ctx == null) {
			throw new IllegalStateException(Font.class.getName() +": No font set! Fix your code, bud!");
		}

		return ctx.getFont().getBounds(str).height;
	}

	public static float getWidth(String str){
		if(ctx == null) {
			throw new IllegalStateException(Font.class.getName() +": No font set! Fix your code, bud!");
		}

		return ctx.getFont().getBounds(str).width;
	}

	private static float getCenter(float fp, float fs, float fh) {
		return fp - (fh / 2) + (fs / 2);
	}

	/* returns names of all available loaded fonts */
	public static String[] getFontList(){
		return soni.plane.gs.draw.Font.getAllFonts();
	}

	/* save font with name as current font context */
	public static boolean useFont(String name) {
		/* make sure the name is not null */
		if(name != null) {
			/* get result from searching fonts from another class */
			soni.plane.gs.draw.Font.FontEntry f = soni.plane.gs.draw.Font.get(name);

			/* if said font was not null, set as context */
			if (f != null) {
				setContext(f);
				return true;
			}
		}

		/* else don't and return */
		return false;
	}

	/* create a new Font with these parameters */
	public static String createFont(String name, int size, Color c){
		/* check if Font can already be found */
		if(soni.plane.gs.draw.Font.get(name +" "+ size +"px") == null) {
			/* create a new Font with same parameters */
			soni.plane.gs.draw.Font.newFont(getBitmapFont(
					Loader.getDataFolder() + "res/fonts/" + name + ".ttf", size), name + " " + size + "px", c);
			return name + " " + size + "px";
		}

		/* else return a failure */
		return null;
	}

	/* generate BitmapFont from filename */
	private static BitmapFont getBitmapFont(String file, int size) {
		FreeTypeFontGenerator.FreeTypeFontParameter f = new FreeTypeFontGenerator.FreeTypeFontParameter();
		f.magFilter = Texture.TextureFilter.Nearest;
		f.minFilter = Texture.TextureFilter.Nearest;
		f.characters = FONT_CHARACTERS;
		f.size = size;
		f.flip = true;

		return new FreeTypeFontGenerator(Gdx.files.absolute(file)).generateFont(f);
	}

	/* gets style as string representation */
	private static String getStyleString(int style) {
		switch (style){
			case PLAIN:
				return "PLAIN";

			case BOLD:
				return "BOLD";

			case ITALIC:
				return "ITALIC";

			case BOLD | ITALIC:
				return "BOLD & ITALIC";
		}

		return "ILLEGAL";
	}


	/* find all installed fonts */
	public static String[] getInstalledFonts(){
		/* get list of all files with .ttf extension (TrueTypeFont files) */
		File[] list = new File(Loader.getDataFolder() + "res/fonts/").listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ttf");
			}
		});
		/* make empty String array of the amount of .ttf files */
		String[] ret = new String[list.length];

		/* loop for the entire list */
		for(int i = 0;i < list.length;i ++){
			/* copy the name from File instance */
			ret[i] = list[i].getName().replace(".ttf", "");
		}

		return ret;
	}

	/* sets context for FontEntry */
	public static void setContext(soni.plane.gs.draw.Font.FontEntry context) {
		ctx = context;
	}

	/* use font context null */
	public static void clearFont() {
		setContext(null);
	}
	
	/* gets FloatRectangle from current project and current Window context */
	private static FloatRectangle getRectangle() {
		return ProjectManager.get().getDraw().getContext().getRectangle();
	}
}
