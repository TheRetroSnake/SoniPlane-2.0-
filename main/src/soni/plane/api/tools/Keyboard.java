package soni.plane.api.tools;

import soni.plane.api.implement.Window;

import java.util.ArrayList;
import java.util.Arrays;

public final class Keyboard {
	/* list of added listeners so far */
	private static ArrayList<ListenerBindings> list = new ArrayList<ListenerBindings>();

	/* get list of ListenerBindings' */
	public static ListenerBindings[] getListeners() {
		return list.toArray(new ListenerBindings[list.size()]);
	}

	/* add KeyListener to the application */
	public static void addListener(KeyListener ls, Window w){
		soni.plane.gs.util.Keyboard.addListener(ls);
		list.add(new ListenerBindings(ls, w));
	}

	/* remove specific KeyListener */
	public static void rmvListener(KeyListener ls, Window w){
		for(ListenerBindings lb : getListeners()){
			/* if Window and KeyListener are same */
			if(lb.w == w && lb.ls == ls){
				list.remove(lb);
			}
		}
	}

	/* remove all KeyListeners from a Window */
	public static void rmvAllListener(Window w){
		for(ListenerBindings lb : getListeners()){
			/* if Window is same */
			if(lb.w == w){
				list.remove(lb);
				/* remove it from other place too */
				soni.plane.gs.util.Keyboard.remove(lb.ls);
			}
		}
	}

	/* this is used to track which Windows load what listeners, to remove them accordingly */
	private static class ListenerBindings {
		private Window w;
		private KeyListener ls;

		public ListenerBindings(KeyListener k, Window win){
			w = win;
			ls = k;
		}
	}
}
