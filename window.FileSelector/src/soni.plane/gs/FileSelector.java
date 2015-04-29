package soni.plane.gs;

import soni.plane.api.exceptions.IllegalArgumentException;
import soni.plane.api.exceptions.IllegalStateException;
import soni.plane.api.graphics.*;
import soni.plane.api.implement.Window;
import soni.plane.api.java.io.File;
import soni.plane.api.java.util.Date;
import soni.plane.api.tools.*;

import java.util.Arrays;
import java.util.Collections;

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
	/* icon X */
	private Sprite XIco;
	/* font used */
	private String font;
	/* selected file */
	private String selected = "";

	/* constants */
	private final int BORDER_WIDTH = 40;

	@Override
	public void init() {
		/* check if expected arguments */
		if(Arguments.check(this, new Class<?>[]{ FileSelectorConfiguration.class, FileSelectorController.class })) {
			/* collect arguments */
			Object[] o = Arguments.get(this);
			/* store arguments */
			cfg = (FileSelectorConfiguration) o[0];
			control = (FileSelectorController) o[1];

			/* check the initial directory exists */
            if (!new File(cfg.startDirectory).exists()) {
                try {
                    throw new IllegalArgumentException(this, "'" + cfg.startDirectory + "' does not exist!", false);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

                WindowTools.exit();
                return;

			/* check the initial directory is an actual directory */
            } else if (!new File(cfg.startDirectory).isDirectory()) {
				try {
					throw new IllegalArgumentException(this, "'" + cfg.startDirectory + "' is not a directory!", false);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}

				WindowTools.exit();
                return;

			/* store the initial folder */
			} else {
				folder = cfg.startDirectory.replace("\\", "/");
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

		/* create font used */
		font = Font.createFont("DejaVuSans.txt");
		/* generate information about files in said directory */
		updateFileList();

		/* initialize used colors */
		colors = new Color[]{
				new Color("normal.txt"),
				new Color("selected.txt"),
				new Color("highlighted.txt"),
				new Color("Xnormal.txt"),
				new Color("Xhighlighted.txt"),
				new Color("fade.txt"), };

		/* create the X icon */
		XIco = new Sprite(new Texture("X.png").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest)).setSize(32, 32).setPosition(-100, -100).setFlip(true, false);
	}

	/* creates new FileList object with information of files in directory */
	private void updateFileList() {
		fl = new FileList(folder, control);
	}

	@Override
	public void draw() {
        /* draw backdrop fade */
		Render.drawRect(WindowTools.getBounds(), colors[5]);
		Render.drawRect(new FloatRectangle(BORDER_WIDTH, BORDER_WIDTH,
		                AppTools.getWidth() - (BORDER_WIDTH * 2), AppTools.getHeight() - (BORDER_WIDTH * 2)), new Color(0.85f, 0.85f, 0.85f, 1f));

		/* render X icon */
		XIco.setPosition(AppTools.getWidth() - 32, 0).setColor(Mouse.compare(XIco.getBounds()) ? colors[4] : colors[3]).draw();

		/* render folder selector */
		drawFolders();
		/* render files */
		drawFiles();
	}

	/* render files */
	private void drawFiles() {
		/* starting Y position */
		float y = BORDER_WIDTH + 20;

		/* loop for all folders */
		for(FileList.FileDef f : fl.folders){
			renderFile(new File(f.path), y);
			y += 20;
		}

		/* loop for all folders */
		for(FileList.FileDef f : fl.files){
			renderFile(new File(f.path), y);
			y += 20;
		}
	}

	/* render the specific file */
	private void renderFile(File f, float y) {
		/* create boundaries */
		FloatRectangle fr = new FloatRectangle(BORDER_WIDTH, y, AppTools.getWidth() - (BORDER_WIDTH * 2), 20);

		/* render rectangle */
		Render.drawRect(fr, Mouse.compare(fr) || selected.equals(f.getFilePath()) ? Mouse.isPressed(Mouse.Buttons.LEFT) ? colors[1] : colors[2] : colors[0]);

		/* draw texts */
		Font.drawCentered(f.getName(), new FloatRectangle(fr.x + 20, y, Font.getWidth(f.getName()) + 4, fr.height));
		Font.drawCentered(getModified(f), new FloatRectangle(fr.x + 20 + ((fr.width - 30) * 0.5f), y, (fr.width - 30) * 0.4f, fr.height));

		if(f.isFile()){
			Font.drawCentered(getSize(f), new FloatRectangle(fr.x + 20 + ((fr.width - 30) * 0.9f), y, (fr.width - 30) * 0.1f, fr.height));
		}

		/* if mouse is pressed over the file */
		if(Mouse.compare(fr) && Mouse.isPressed(Mouse.Buttons.LEFT)){
			/* is selected? */
			if(selected.equals(f.getFilePath())){
				/* if is, check if file can be opened */
				if(f.isFile() ? control.openFile(f.getFilePath(), f.getExtension()) : control.openFolder(f.getFilePath())){
					/* if opened, exit */
					WindowTools.exit();
				}
			/* else select file */
			} else {
				selected = f.getFilePath();
			}
		}
	}

	/* get file size in human readable format */
	private String getSize(File f) {
		/* if less than kilobyte, return bytes */
		if (f.getSize() < 1024) return f.getSize() + " B";
		/* else get offset for string read */
		int exp = (int) (Math.log(f.getSize()) / Math.log(1024));
		/* format the string */
		return String.format("%.1f %sB", f.getSize() / Math.pow(1024, exp), "kMGTPE".charAt(exp - 1));
	}

	/* get default last modified string */
	private String getModified(File f) {
		Date d = f.lastModified();
		return d.getDay() +"."+ d.getMonth() +"."+ d.getYear() +" "+ d.getHour() +";"+ d.getMinute();
	}

	/* render folder images */
	private void drawFolders() {
		/* use font with operations */
		Font.useFont(font);

		/* starting X position */
		float x = AppTools.getWidth() - BORDER_WIDTH - 10;

		/* get list of all golders and reverse the list
		 * this is done, so that last folders are read first */
		String[] l = folder.split("/");
		Collections.reverse(Arrays.asList(l));

		/* loop for all subfolders */
		for(String name : l){
			/* get font width and set X position */
			float w = Font.getWidth(name) + 10;
			x -= w;

			if(x > BORDER_WIDTH){
				/* create boundaries */
				FloatRectangle f = new FloatRectangle(x, BORDER_WIDTH, w, 20);

				/* render rectangle and text */
				Render.drawRect(f, Mouse.compare(f) ? Mouse.isPressed(Mouse.Buttons.LEFT) ? colors[1] : colors[2] : colors[0]);
				Font.drawCentered(name, f);

				/* check if should move to folder */
				if(Mouse.isPressed(Mouse.Buttons.LEFT) && Mouse.compare(f) && cfg.canGoSuperDirectory){
					if(!name.equals(folder)){
					/* save new folder and reload files */
						folder = folder.split(name)[0] + name;
					}
					updateFileList();
				}

				/* finally draw the separator */
				Font.drawCentered("/", new FloatRectangle(x + w, BORDER_WIDTH, 0, 20));
			}
		}
	}

	@Override
	public void logic() {
		/* if left mouse button is pressed and is in bounds with icon, exit */
		if(Mouse.isPressed(Mouse.Buttons.LEFT) && Mouse.compare(XIco.getBounds())){
			WindowTools.exit();
		}
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
