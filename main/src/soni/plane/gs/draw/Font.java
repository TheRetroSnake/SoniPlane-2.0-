package soni.plane.gs.draw;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import soni.plane.api.graphics.Color;

import java.util.ArrayList;

public class Font {
	/* list of loaded fonts */
	private static ArrayList<FontEntry> fonts = new ArrayList<FontEntry>();

	/* get a font from the list */
	public static FontEntry get(String name){
		for(FontEntry f : fonts){
			/* if name equals, then return */
			if(f.getName().equals(name)){
				return f;
			}
		}

		return null;
	}

	/* generate a new Font */
	public static FontEntry newFont(BitmapFont font, String id, Color color) {
		FontEntry f = new FontEntry(id, font, color);

		/* add font and return */
		fonts.add(f);
		return f;
	}

	/* get names of all fonts */
	public static String[] getAllFonts(){
		ArrayList<String> a = new ArrayList<String>();

		/* loop for all fonts */
		for(FontEntry f : fonts){
			a.add(f.getName());
		}

		/* generate a String array */
		return a.toArray(new String[a.size()]);
	}

	public static class FontEntry {
		private final String name;
		private final BitmapFont bp;
		private Color c;

		public FontEntry(String name, BitmapFont bp, Color color){
			this.name = name;
			this.bp = bp;
			c = color;
		}

		public String getName() {
			return name;
		}

		public BitmapFont getFont() {
			return bp;
		}

		public Color getColor(){
			return c;
		}

		public void setColor(Color color) {
			c = color;
		}
	}
}
