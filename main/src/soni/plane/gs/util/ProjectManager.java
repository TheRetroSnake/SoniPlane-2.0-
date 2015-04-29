package soni.plane.gs.util;

import soni.plane.gs.draw.Draw;
import soni.plane.gs.tools.ConfigFile;

import java.io.File;
import java.util.ArrayList;

public class ProjectManager {
	/* list of open projects */
	private static ArrayList<ProjectData> proj = new ArrayList<ProjectData>();
	/* set current project */
	private static int current;

	/* create a new project */
	public static ProjectData create(ProjectFile cfg){
		/* create new ProjectData */
		ProjectData pj = new ProjectData(cfg, new Draw());

		/* if first created project is null */
		if(proj.size() >= 1 && proj.get(0).getConfig() == null){
			/* then replace first project */
			proj.set(0, pj);

		} else {
			/* else add new project at last place */
			proj.add(proj.size(), pj);
		}

		/* initialize Draw and return ProjectData */
		pj.getDraw().init();
		return pj;
	}

	public static ProjectData open(String file){
		/* check if project exists */
		if(new File(file).exists()) {
			/* if does, read from it */
			return create(new ProjectFile(file, ConfigFile.READ | ConfigFile.WRITE, FileUtil.read(file)));
		}

		/* else just create it */
		return create(new ProjectFile(file, ConfigFile.READ | ConfigFile.WRITE));
	}

	/* delete project */
	public static void delete(int off){
		proj.remove(off);
	}

	public static ProjectData get(int off){
		return proj.get(off);
	}

	public static ProjectData get(){
		return proj.get(current);
	}

	public static ProjectData setProject(int id){
		current = id;
		return get();
	}

	public static int get(ProjectData pr){
		for(int i = 0;i < proj.size();i ++){
			if(pr == proj.get(i)){
				return i;
			}
		}

		return -1;
	}

	public static int getProject(){
		return current;
	}

	public static int getAmount(){
		return proj.size();
	}
}
