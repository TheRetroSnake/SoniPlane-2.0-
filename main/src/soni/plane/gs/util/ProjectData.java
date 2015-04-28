package soni.plane.gs.util;

import soni.plane.gs.draw.Draw;
import soni.plane.gs.tools.ConfigFile;

public class ProjectData {
	private ProjectFile config;
	private Draw draw;

	public ProjectData(ProjectFile cfg, Draw gfx){
		config = cfg;
		draw = gfx;
	}

	public ConfigFile getConfig(){
		return config;
	}

	public Draw getDraw(){
		return draw;
	}
}
