package soni.plane.api.java.io;

import soni.plane.api.java.util.Date;
import soni.plane.gs.Main;

import java.io.IOException;
import java.util.Arrays;

public final class File {
	/* File context */
	private java.io.File f;

	/* create new File from String */
	public File(String file){
		f = new java.io.File(file);
	}

	/* get File absolute path */
	public String getFilePath(){
		return f.getAbsolutePath();
	}

	/* get parent File absolute path */
	public File getParentFile(){
		return new File(f.getParentFile().getAbsolutePath());
	}

	/* is this a file */
	public boolean isFile(){
		return f.isFile();
	}

	/* is this a directory */
	public boolean isDirectory(){
		return f.isDirectory();
	}

	/* does the file exist? */
	public boolean exists(){
		return f.exists();
	}

	/* attempt to create a new file here */
	public boolean createNewFile() throws IOException {
		return f.createNewFile();
	}

	/* if the file can be read */
	public boolean canRead() {
		return f.canRead();
	}

	/* if the file can be written */
	public boolean canWrite() {
		return f.canWrite();
	}

	/* if the file is hidden from view */
	public boolean isHidden() {
		return f.isHidden();
	}

	/* make directory at target path */
	public boolean mkdir() {
		return f.mkdir();
	}

	/* make directories needed to write file at destination */
	public boolean mkdirs() {
		return f.mkdirs();
	}

	/* get file name */
	public String getName() {
		return f.getName();
	}

	/* get file extension */
	public String getExtension(){
		if(f.isFile()){
			/* split the file for each period */
			String[] split = f.getName().split("\\.");

			/* if there were no periods, return empty */
			if(split.length == 0){
				return "";
			}

			/* return extension */
			return split[split.length -1];
		}

		return "";
	}

	/* list files at target directory */
	public File[] listFiles() {
		return toFiles(f.listFiles());
	}

	/* quickly convert java.io.File array to soni.plane.api.java.io.File array */
	private File[] toFiles(java.io.File[] files) {
		File[] res = new File[files.length];

		for(int i = 0;i < res.length;i ++){
			res[i] = new File(files[i].getAbsolutePath());
		}

		return res;
	}

	/* date at when last modified */
	public Date lastModified() {
		return new Date(f.lastModified());
	}

	/* get file size */
	public long getSize() {
		return f.length();
	}

	/* get SoniPlane main folder */
	public static String getMainFolder(){
		return Main.folder;
	}
}