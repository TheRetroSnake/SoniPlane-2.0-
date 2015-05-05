package soni.plane.api.tools;

import java.util.ArrayList;

public final class Arguments {
	/* class to store an Argument */
	private static class Argument {
		Object[] args;
		String context;

		public Argument(String ctx, Object[] args){
			this.args = args;
			context = ctx;
		}
	}

	/* list of arguments stored */
	private static ArrayList<Argument> stored = new ArrayList<Argument>();

	/* get Arguments of specified object */
	private static Argument getArgument(Object owner){
		/* check for all arguments */
		for(Argument a : stored){
			/* check if context is correct */
			if(a.context.equals(owner.toString())){
				/* return arguments */
				return a;
			}
		}

		return null;
	}

	/* get Arguments of specified object */
	public static Object[] get(Object owner){
		/* get argument object */
		Argument a = getArgument(owner);

		/* if argument is null, avoid NullPointerArgument */
		if(a == null){
			return null;
		}

		/* return the actual arguments */
		return a.args;
	}

	/* add new Arguments for an object */
	public static void add(Object owner, Object[] args){
		stored.add(new Argument(owner.toString(), args));
	}

	/* remove Arguments for an object */
	public static boolean remove(Object owner){
		/* get argument object */
		Argument a = getArgument(owner);

		/* if argument is not null, remove the Argument object */
		if(a != null){
			stored.remove(a);
			return true;
		}

		return false;
	}

	/* check if arguments match */
	public static boolean check(Object owner, Class[] cls){
		/* get argument object */
		Argument a = getArgument(owner);

		/* if argument is not null, continue check */
		if(a != null){
			/* make sure arguments and check is same length */
			if(cls.length != a.args.length){
				return false;
			}

			/* loop for all arguments */
			for(int i = 0;i < cls.length;i ++){
				/* check if argument class is not same as check */
				if(!a.args[i].getClass().equals(cls[i])){
					/* check also if super class would be correct */
					if(!a.args[i].getClass().getSuperclass().equals(cls[i])) {
						return false;
					}
				}
			}

			return true;
		}

		return false;
	}

	/* used by the debugger */
	public static int getAmount(){
		return stored.size();
	}
}
