package soni.plane.api.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import soni.plane.gs.util.ProjectManager;

public final class Mouse {
	/* enum for mouse buttons available */
	public enum Buttons {
		MIDDLE(Input.Buttons.MIDDLE),
		RIGHT(Input.Buttons.RIGHT),
		LEFT(Input.Buttons.LEFT);

		private final int btn;

		private Buttons(int btn) {
			this.btn = btn;
		}
	}

	/* get mouse X position */
	public static float getX(){
		return Gdx.input.getX() + ProjectManager.get().getDraw().getContext().getRectangle().x;
	}

	/* get mouse Y position */
	public static float getY(){
		return Gdx.input.getY() + ProjectManager.get().getDraw().getContext().getRectangle().y;
	}

	/* check if mouse is inside a rectangle */
	public static boolean compare(FloatRectangle rect){
		return (getX() >= rect.x) && (getX() < (rect.x + rect.width)) && (getY() >= rect.y) && (getY() < (rect.y + rect.height));
	}

	/* check if mouse is inside a rectangle between two points */
	public static boolean compare(float x1, float y1, float x2, float y2){
		/* copy the variables for checks later */
		float startX = x1, endX = x2, startY = y1, endY = y2;

		/* if startX and endX are flipped, fix it */
		if(startX < endX){
			startX = x2;
			endX = x1;
		}
		/* if startY and endY are flipped, fix it */
		if(startY < endY){
			startY = y2;
			endY = y1;
		}

		/* finally compare the values and return the answer */
		return (getX() >= startX) && (getX() < endX) && (getY() >= startY) && (getY() < endY);
	}

	/* if button was pressed */
	public static boolean isPressed(Buttons b){
		return isHeld(b) && Gdx.input.justTouched();
	}

	/* if button is being held */
	public static boolean isHeld(Buttons b) {
		return Gdx.input.isButtonPressed(b.btn);
	}
}
