package soni.plane.api.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import soni.plane.gs.draw.WindowManager;
import soni.plane.gs.util.ProjectManager;

import java.util.Arrays;
import java.util.Collections;

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
		return checkOrder() && (getX() >= rect.x) && (getX() < (rect.x + rect.width)) && (getY() >= rect.y) && (getY() < (rect.y + rect.height));
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

		/* if there is window in the way, return false */
		if(!checkOrder()){
			return false;
		}

		/* finally compare the values and return the answer */
		return (getX() >= startX) && (getX() < endX) && (getY() >= startY) && (getY() < endY);
	}

	/* make sure another Window isn't on top of current one */
	private static boolean checkOrder() {
		/* get current context */
		WindowManager ctx = ProjectManager.get().getDraw().getContext();

		/* get list of all WindowManagers and reverse the list
		 * this is done, so that bigger priority entries are read first */
		WindowManager[] l = ProjectManager.get().getDraw().createFullList();
		Collections.reverse(Arrays.asList(l));

		/* loop for all WindowManagers */
		for(WindowManager w : l){
			/* if context is same as this WindowManager, test passed */
			if(ctx.getWindow() == w.getWindow()){
				return true;
			}

			/* check if Window is clipping mouse position */
			if(w.getRectangle() != null && checkClip(w.getRectangle())){
				return false;
			}
		}

		/* it should not be possible to come here, something went wrong */
		return false;
	}

	/* check if mouse cursor is inside another Window */
	private static boolean checkClip(FloatRectangle r) {
		return (getX() >= r.x) && (getX() < r.width) && (getY() >= r.y) && (getY() < r.height);
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
