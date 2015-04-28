package soni.plane.gs;

import soni.plane.api.exceptions.IllegalArgumentException;
import soni.plane.api.exceptions.IllegalStateException;
import soni.plane.api.graphics.Color;
import soni.plane.api.graphics.Render;
import soni.plane.api.implement.Window;
import soni.plane.api.java.io.File;
import soni.plane.api.tools.Arguments;
import soni.plane.api.tools.WindowTools;

import java.util.Arrays;

public class FileSelector implements Window{
	/* folder to start browsing at */
	private String folder;
	/* object which allows to control this */
	private FileSelectorController control;
	/* configuration for this */
	private FileSelectorConfiguration cfg;
	/* list of loaded colors */
	private Color[] colors;
	/* list of loaded files on location */
	private FileList fl;

	@Override
	public void init() {
		/* check if expected arguments */
		if(Arguments.check(this, new Class<?>[]{ FileSelectorConfiguration.class, FileSelectorController.class })) {
			/* collect arguments */
			Object[] o = Arguments.get(this);
			/* store arguments */
			cfg = (FileSelectorConfiguration) o[0];
			control = (FileSelectorController) o[1];

			/* check the initial directory is an actual directory */
			if (!new File(cfg.startDirectory).isDirectory()) {
				try {
					throw new IllegalArgumentException(this, "'" + cfg.startDirectory + "' is not a directory!", false);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}

				WindowTools.exit();

			/* check the initial directory exists */
			} else if (!new File(cfg.startDirectory).exists()) {
				try {
					throw new IllegalArgumentException(this, "'" + cfg.startDirectory + "' does not exist!", false);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}

				WindowTools.exit();

			/* store the initial folder */
			} else {
				folder = cfg.startDirectory;
			}

		/* incorrect Argument types */
		} else {
			try {
				throw new IllegalArgumentException(this, "Unexpected arguments! Expected '"+
						Arrays.toString(new Class<?>[]{ FileSelectorConfiguration.class, FileSelectorController.class }) +"' found '"+
						Arrays.toString(Arguments.get(this)) +"'!", false);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			WindowTools.exit();
			return;
		}

		/* if configuration or control are null, throw error and close */
		if(cfg == null || control == null){
			WindowTools.exit();

			/* if control object is not null, send error to it */
			if(control != null){
				doError("FileSelector could not be initialized! Configuration is " + cfg + "! Terminating current FileSelector context!", 100);

			/* else directly throw error. This is not desirable but neither is fixable */
			} else {
				/*  */
				try {
					throw new IllegalStateException(this, "FileSelector could not be initialized! Control is "+ control +"!", false);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}
		}

		/* generate information about files in said directory */
		updateFileList();

		/* initialize used colors */
		colors = new Color[]{
				new Color("fade.txt"), };
	}

	/* creates new FileList object with information of files in directory */
	private void updateFileList() {
		fl = new FileList(folder);
	}

	@Override
	public void draw() {
		Render.drawRect(WindowTools.getBounds(), colors[0]);
	}

	@Override
	public void logic() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void resize(int w, int h) {
		WindowTools.resize(w, h);
	}

	@Override
	public void buttonEvent(int i) {

	}

	@Override
	public boolean unFocus() {
		return false;
	}

	@Override
	public boolean focus() {
		return false;
	}

	@Override
	public void update() {

	}

	@Override
	public boolean checkUpdate(String s) {
		return false;
	}

	/* display error message if one is requested */
	private void doError(String s, int errorCode) {
		try {
			/* if Control object is not null, do regular error tracking */
			if(control != null) {
				control.error(s, errorCode);

			/* else throw generic error */
			} else {
				throw new Exception(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
