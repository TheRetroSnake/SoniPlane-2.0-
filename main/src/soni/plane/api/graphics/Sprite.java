package soni.plane.api.graphics;

import soni.plane.api.tools.FloatRectangle;
import soni.plane.gs.Main;
import soni.plane.gs.util.ProjectManager;

public final class Sprite {
	/* stored Sprite reference */
	private com.badlogic.gdx.graphics.g2d.Sprite spr;

	public Sprite(){
		spr = new com.badlogic.gdx.graphics.g2d.Sprite();
		spr.setFlip(false, true);
	}

	public Sprite(Texture tx){
		spr = new com.badlogic.gdx.graphics.g2d.Sprite(tx.get());
		spr.setFlip(false, true);
	}

	/* draw this Sprite */
	public Sprite draw(){
		/* get Window bounds */
		FloatRectangle f = ProjectManager.get().getDraw().getContext().getRectangle();
		/* get Sprite original positions */
		float x = spr.getX(), y = spr.getY();
		/* add Window x and y to Sprite positions */
		spr.translate(f.x, f.y);
		/* draw Sprite */
		spr.draw(Main.getBatch());
		/* set to Original positions */
		spr.setPosition(x, y);
		return this;
	}

	/* set what way the Sprite is flipped (negate y-flip to make libGDX draw it right way) */
	public Sprite setFlip(boolean x, boolean y){
		spr.setFlip(x, !y);
		return this;
	}

	/* get boundaries of this Sprite */
	public FloatRectangle getBounds(){
		return new FloatRectangle(spr.getBoundingRectangle());
	}

	/* set Color to the Sprite */
	public Sprite setColor(Color c){
		spr.setColor(c.get());
		return this;
	}

	/* get Color from the Sprite */
	public Color getColor(){
		return new Color(spr.getColor().r, spr.getColor().g, spr.getColor().b, spr.getColor().a);
	}

	/* get Sprite rotation */
	public float getRotation(){
		return spr.getRotation();
	}

	/* set rotation for the Sprite */
	public Sprite setRotation(float rotation){
		spr.setRotation(rotation);
		return this;
	}

	/* rotate the Sprite */
	public Sprite rotate(float amount){
		spr.rotate(amount);
		return this;
	}

	/* set Sprite scale */
	public Sprite setScale(float x, float y){
		spr.setScale(x, y);
		return this;
	}

	/* get x-scale from Sprite */
	public float getScaleX(){
		return spr.getScaleX();
	}

	/* get y-scale from Sprite */
	public float getScaleY(){
		return spr.getScaleY();
	}

	/* set alpha for Sprite */
	public Sprite setAlpha(float a){
		spr.setAlpha(a);
		return this;
	}

	/* set boundaries for Sprite */
	public Sprite setBounds(FloatRectangle rect){
		spr.setBounds(rect.x, rect.y, rect.width, rect.height);
		return this;
	}

	/* set position for Sprite */
	public Sprite setPosition(float x, float y){
		spr.setPosition(x, y);
		return this;
	}

	/* move Sprite position */
	public Sprite move(float x, float y){
		spr.translate(x, y);
		return this;
	}

	/* set position center for Sprite */
	public Sprite setPositionCenter(float x, float y){
		spr.setCenter(x, y);
		return this;
	}

	/* set size for Sprite */
	public Sprite setSize(float width, float height){
		spr.setSize(width, height);
		return this;
	}
}