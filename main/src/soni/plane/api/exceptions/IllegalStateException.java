package soni.plane.api.exceptions;

import soni.plane.gs.Main;

public class IllegalStateException extends Exception {
	public IllegalStateException(Object o, boolean forceExit){
		super(o.toString());

		if(forceExit){
        	/* Fatal error, close tool */
			Main.exit(plugin.class + ": IllegalStateException(" + o.toString() + ")");
		}
	}

	public IllegalStateException(Object o, String info, boolean forceExit){
		super(o.toString() +": "+ info);

		if(forceExit){
        	/* Fatal error, close tool */
			Main.exit(plugin.class + ": IllegalStateException("+ o.toString()+ ": "+ info +")");
		}
	}
}
