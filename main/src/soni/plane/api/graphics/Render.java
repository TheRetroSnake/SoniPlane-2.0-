package soni.plane.api.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import soni.plane.api.tools.FloatRectangle;
import soni.plane.gs.Loader;
import soni.plane.gs.Main;
import soni.plane.gs.util.ProjectManager;

public final class Render {
	/* single Pixel Texture used to draw lines and rectangles */
	private static Texture single = getSingle();
    private static void foo(){
       
    }

	/* draw a Rectangle */
	public static void drawRect(FloatRectangle f, Color c){
		/* make new sprite */
		Sprite s = new Sprite(single);
		/* set bounds */
		s.setBounds(f.x, f.y, f.width, f.height);
		/* set its color */
		s.setColor(c.get());
		/* draw */
		s.draw(Main.getBatch());
	}

	/* draw line from position 1 to position 2 with set thickness and color
	 * NOTE: For lines which are dividable by 90, please use drawRect(FloatRectangle, Color) instead! */
	public static void drawLine(float startX, float startY, float endX, float endY, float width, Color c){
		/* calculate variables used */
		float w = endX - startX, h = endY - startY;
		/* new sprite */
		Sprite s = new Sprite(single);
		/* set sprite color */
		s.setColor(c.get());
		/* set correct starting position */
		s.setPosition(getWindow().x + startX, getWindow().y + startY);

		/* get correct angle to draw in */
		float angle = (float) Math.toDegrees(Math.atan2(h, w));

		/* set correct size too */
		s.setSize(getLength(angle, h, w), width);
		/* and rotation */
		s.setRotation(angle);
		/* finally draw the Sprite */
		s.draw(Main.getBatch());
	}

	/* pseudo-method to catch the correct setting for line length */
	private static float getLength(float angle, float v1, float v2) {
		/* calculate the quarter we are in */
		switch ((int)(angle / 45f) >> 1){
			case 0:
				return v2;

			case 1:
				return v1;

			case -1:
				return -v2;

			case -2:
				return -v1;
		}

		/* something went terribly wrong here */
		System.err.println("Illegal angle: "+ angle +" case: "+ ((int)(angle / 45f) >> 1));
		return -1;
	}

	/* method to get the FloatRectangle bounds of current Window faster */
	private static FloatRectangle getWindow() {
		return ProjectManager.get().getDraw().getContext().getRectangle();
	}

	private static Texture getSingle() {
		Texture t = new Texture(Gdx.files.absolute(Loader.getDataFolder() + "res/graphics/single pixel.png"));
		t.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.MipMapLinearLinear);
		return t;
	}
}