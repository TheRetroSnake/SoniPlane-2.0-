package soni.plane.api.tools;

import soni.plane.gs.Loader;
import soni.plane.gs.util.ProjectData;
import soni.plane.gs.util.ProjectManager;

public final class ProjectTools {
	private static ProjectData context;

	/* use project */
	public static boolean use(int id){
		/* check if trying to read out of bounds */
		if(id <= length() && id >= 0){
			/* if not, get context and return if null or not */
			context = ProjectManager.get(id);
			return context != null;
		}

		/* out of range */
		return false;
	}

	/* create new project */
	public static boolean create(String name){
		/* get the actual file location */
		String file = Loader.getDataFolder() +"projects/"+ name +".SPP";
		/* try to open the project */
		context = ProjectManager.open(file);

		/* check if the context is not null */
		return context != null;
	}

	/* get ID of current context */
	public static int getID(){
		return ProjectManager.get(context);
	}

	/* make project with ID the project used */
	public static boolean current(int id){
		if(use(id)){
			ProjectManager.setProject(id);
			return true;
		}

		/* failed to use the project */
		return false;
	}

	/* delete a project */
	public static void remove(int id){
		ProjectManager.delete(id);
	}

	/* get amount of projects */
	public static int length(){
		return ProjectManager.getAmount();
	}

	/* set value */
	public static boolean setValue(String field, String value){
		return context.getConfig().setField(field, value) != null;
	}

	/* Get value */
	public static String getValue(String field){
		return context.getConfig().getField(field).getValue();
	}

	/* get file name */
	public static String getFileName(){
		return context.getConfig().getFile();
	}
}
