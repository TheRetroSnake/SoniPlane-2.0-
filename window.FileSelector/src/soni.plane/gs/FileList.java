package soni.plane.gs;

import soni.plane.api.java.io.File;

import java.util.ArrayList;

public class FileList {
	/* file path data is loaded from */
	final String path;
	/* arrayList for file definitions loaded */
	final ArrayList<FileDef> files;
	final ArrayList<FileDef> folders;

	/* constructor to create FileLists based on file path */
	public FileList(String location){
		path = location;
		files = new ArrayList<FileDef>();
		folders = new ArrayList<FileDef>();
		loadFiles();
	}

	/* load files from specified path to memory */
	private void loadFiles() {
		/* check all files in specific path */
		for(File f : new File(path).listFiles()){

			/* check if this is a file */
			if(f.isFile()){
				files.add(getFileDef(f));

			/* is it a folder? */
			} else if(f.isDirectory()) {
				folders.add(getFileDef(f));
			}
		}
	}

	/* gets standard FileDef for File f */
	private FileDef getFileDef(File f) {
		/* return new FileDef object */
		return new FileDef(f.getFilePath(), f.getName());
	}

	/* this class describes instance of file/folder that is loaded */
	private class FileDef {
		/* path of the file */
		private final String path;
		/* name of the File */
		private final String name;

		/* default constructor for FileDef object */
		public FileDef(String FullPath, String FileName) {
			path = FullPath;
			name = FileName;
		}
	}
}
