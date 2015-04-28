package soni.plane.api.exceptions;

import soni.plane.gs.Main;

public class IllegalArgumentException extends Exception {
	public IllegalArgumentException(Object o, boolean forceExit){
		super(o.toString());

		if(forceExit){
        	/* Fatal error, close tool */
			Main.exit(plugin.class + ": IllegalArgumentException("+ o.toString() +")");
		}
	}

	public IllegalArgumentException(Object o, String info, boolean forceExit){
		super(o.toString() +": "+ info);

		if(forceExit){
        	/* Fatal error, close tool */
			Main.exit(plugin.class + ": IllegalArgumentException("+ o.toString()+ ": "+ info +")");
		}
	}
}
