package soni.plane.gs.util;

import soni.plane.api.java.util.Date;
import soni.plane.gs.tools.ConfigFile;

public class ProjectFile extends ConfigFile {
	public ProjectFile(String file, String name, int perms, String data) {
		super(file, name, perms, data);
	}

	public ProjectFile(String file, int perms) {
		super(file, perms);
	}

	public ProjectFile(String file, int perms, String data) {
		super(file, perms, data);
	}

	public String getVersion(){
		return getField("versionID").getValue();
	}

	public String getType(){
		return getField("type").getValue();
	}

	public String getName(){
		return getField("name").getValue();
	}

	public Date getModified(){
		return new Date(Long.parseLong(getField("modified").getValue()));
	}

	public Date getCreated(){
		return new Date(Long.parseLong(getField("created").getValue()));
	}
}
