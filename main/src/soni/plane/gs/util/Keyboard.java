package soni.plane.gs.util;


import com.badlogic.gdx.InputProcessor;
import soni.plane.api.tools.KeyListener;

import java.util.ArrayList;

public class Keyboard implements InputProcessor {
	private static ArrayList<KeyListener> listeners = new ArrayList<KeyListener>();

	public static void addListener(KeyListener listener){
		listeners.add(listener);
	}

	public static void rmvListener(KeyListener listener){
		listeners.remove(listener);
	}

	public static KeyListener[] getListeners() {
		return listeners.toArray(new KeyListener[listeners.size()]);
	}

	@Override
	public boolean keyDown(int key) {
		for(KeyListener l : getListeners()){
			l.keyDown(key);
		}

		return true;
	}

	@Override
	public boolean keyUp(int key) {
		for(KeyListener l : getListeners()){
			l.keyUp(key);
		}

		return true;
	}

	@Override
	public boolean keyTyped(char c) {
		for(KeyListener l : getListeners()){
			l.keyTyped(c);
		}

		return true;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		for(KeyListener l : getListeners()){
			l.touchDown(x, y, button);
		}

		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		for(KeyListener l : getListeners()){
			l.touchUp(x, y, button);
		}

		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		for(KeyListener l : getListeners()){
			l.touchDragged(x, y);
		}

		return true;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		for(KeyListener l : getListeners()){
			l.mouseMoved(x, y);
		}

		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		for(KeyListener l : getListeners()){
			l.scrolled(amount);
		}

		return true;
	}
}
