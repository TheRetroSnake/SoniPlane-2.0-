package soni.plane.gs;

import soni.plane.api.java.io.File;

public class FileSelectorConfiguration {
	/* if can enter sub-directories or super-directories */
	protected boolean canGoSubDirectory;
	protected boolean canGoSuperDirectory;
	/* if can create files and folders */
	protected boolean canDoFolders;
	protected boolean canDoFiles;
	/* initial directory */
	protected String startDirectory;

	/* initialize */
	public FileSelectorConfiguration(){
		canGoSubDirectory = true;
		canGoSuperDirectory = true;
		startDirectory = System.getProperty("user.home");
	}

	/* set if can go subDirectory */
	public FileSelectorConfiguration setCanGoSubDir(boolean can){
		canGoSubDirectory = can;
		return this;
	}

	/* set if can go superDirectory */
	public FileSelectorConfiguration setCanGoSuperDir(boolean can){
		canGoSuperDirectory = can;
		return this;
	}

	/* set initial directory from String */
	public FileSelectorConfiguration setInitialDir(String dir){
		startDirectory = dir;
		return this;
	}

	/* set initial directory from File */
	public FileSelectorConfiguration setInitialDir(File dir){
		startDirectory = dir.getFilePath();
		return this;
	}

	/* if can create folders */
	public FileSelectorConfiguration setCreateFolders(boolean can){
		canDoFolders = can;
		return this;
	}

	/* if can create files */
	public FileSelectorConfiguration setCreateFiles(boolean can){
		canDoFiles = can;
		return this;
	}
}
