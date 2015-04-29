package soni.plane.api.exceptions;

import soni.plane.gs.Main;

public class IllegalArgumentException extends Exception {
	public IllegalArgumentException(Object o, boolean forceExit){
		super(o.getClass().getName());

		if(forceExit){
        	/* Fatal error, close tool */
			Main.exit(plugin.class + ": IllegalArgumentException("+ o.getClass().getName() +")");
		}
	}

	public IllegalArgumentException(Object o, String info, boolean forceExit){
		super(o.getClass().getName() +": "+ info);

		if(forceExit){
        	/* Fatal error, close tool */
			Main.exit(plugin.class + ": IllegalArgumentException("+ o.getClass().getName() +": "+ info +")");
		}
	}
}
