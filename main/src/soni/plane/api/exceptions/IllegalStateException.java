package soni.plane.api.exceptions;

import soni.plane.gs.Main;

public class IllegalStateException extends Exception {
	public IllegalStateException(Object o, boolean forceExit){
		super(o.getClass().getName());

		if(forceExit){
        	/* Fatal error, close tool */
			Main.exit(plugin.class + ": IllegalStateException(" + o.getClass().getName() + ")");
		}
	}

	public IllegalStateException(Object o, String info, boolean forceExit){
		super(o.getClass().getName() +": "+ info);

		if(forceExit){
        	/* Fatal error, close tool */
			Main.exit(plugin.class + ": IllegalStateException("+ o.getClass().getName() +": "+ info +")");
		}
	}
}
