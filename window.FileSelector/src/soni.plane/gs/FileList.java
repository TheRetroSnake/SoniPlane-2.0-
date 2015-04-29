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
	public FileList(String location, FileSelectorController control){
		path = location;
		files = new ArrayList<FileDef>();
		folders = new ArrayList<FileDef>();
		loadFiles(control);
	}

	/* load files from specified path to memory */
	private void loadFiles(FileSelectorController c) {
		/* check all files in specific path */
		for(File f : new File(path).listFiles()){

			/* check if this is a file */
			if(f.isFile() && c.listFile(f.getFilePath(), f.getExtension())){
				files.add(getFileDef(f));

			/* is it a folder? */
			} else if(f.isDirectory() && c.listFolder(f.getFilePath())) {
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
	class FileDef {
		/* path of the file */
		final String path;
		/* name of the File */
		final String name;

		/* default constructor for FileDef object */
		public FileDef(String FullPath, String FileName) {
			path = FullPath;
			name = FileName;
		}
	}
}
