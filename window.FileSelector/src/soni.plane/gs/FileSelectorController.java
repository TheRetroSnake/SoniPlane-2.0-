package soni.plane.gs;

public abstract class FileSelectorController {
	/* if file/folder can be listed/opened */
	abstract boolean listFile(String file, String extension);
	abstract boolean listFolder(String folder);
	abstract boolean openFile(String file, String extension);
	abstract boolean openFolder(String folder);
	/* if folder/file is created what it should be named (mostly used to add correct extension if required)
	* NOTE: return null and file/folder will not be created and opened */
	abstract String createFolder(String folder);
	abstract String createFile(String file);
	/* send error to controller object */
	abstract void error(String error, int errorCode) throws Exception;
}
