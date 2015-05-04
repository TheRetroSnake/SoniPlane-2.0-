package soni.plane.api.tools;

/* implemented KeyListener; abstract class */
public abstract class KeyListener {
	private float scrollAmount = 0;

	/* all the abstract methods */
	public abstract void keyDown(int key);
	public abstract void keyUp(int key);
	public abstract void keyTyped(char c);
	public abstract void touchUp(int x, int y, int button);
	public abstract void touchDown(int x, int y, int button);
	public abstract void touchDragged(int x, int y);
	public abstract void mouseMoved(int x, int y);

	/* used to call ScrollTo method
	 * can be overridden */
	public void scrolled(float amount) {
		setScrollPos(scrollAmount + amount);
		scrollTo(scrollAmount);
	}

	/* abstract method to tell actual relative position to scroll to */
	public abstract void scrollTo(float pos);

	/* method to allow modifying the current scroll position
	 * can be overridden */
	public void setScrollPos(float pos){
		scrollAmount = pos;
	}
}
