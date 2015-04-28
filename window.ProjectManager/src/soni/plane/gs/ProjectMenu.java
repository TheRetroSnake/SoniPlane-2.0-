package soni.plane.gs;

import soni.plane.api.exceptions.*;
import soni.plane.api.exceptions.IllegalStateException;
import soni.plane.api.graphics.*;
import soni.plane.api.implement.Window;
import soni.plane.api.java.io.File;
import soni.plane.api.tools.*;

import java.util.ArrayList;

public class ProjectMenu implements Window {
	/* font used */
	String font;
	/* ArrayList for projects loaded */
	ArrayList<Project> pr;
	Project pPlus;
	Project selected;
	/* Textures for stuff */
	Texture[] parts;
	Texture[] tButtons;
	Texture fade;
	/* Sprites for stuff */
	Sprite[] pButtons;
	Sprite[] pArrow;
	/* stored colors */
	Color[] cols;
	/* variables used */
	float settRot = 0;
	float settRotSpd = 0;
	float drawPos = 0;
	float drawTarget = 0;
	float drawSpeed = 0;
	/* constants used */
	final static int DRAW_Y = -6;
	final static int DRAW_HEIGHT = 42;
	final static int PART_VISIBLESIZE = 30;

	@Override
    public void init() {
		/* make sure this is at 0,0 */
        WindowTools.move(0, 0);
		/* make sure Window is resize correctly */
		resize((int)AppTools.getWidth(), (int)AppTools.getHeight());

		/* create font used */
		font = Font.createFont("DejaVuSans", 20, new Color(1f, 1f, 1f, 1f));
		/* init projects list */
		pr = new ArrayList<Project>();

		/* load colors */
		cols = new Color[]{
				new Color("normal.txt"),
				new Color("selected.txt"),
				new Color("highlighted.txt"), };

		/* load Textures */
		parts = new Texture[]{
				new Texture("1.png").		setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest),
				new Texture("2.png").		setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest),
				new Texture("3.png").		setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest),
				new Texture("plus.png").	setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest), };
		tButtons = new Texture[]{
				new Texture("plus2.png").	setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest),
				new Texture("info.png").	setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest),
				new Texture("settings.png").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest),
				new Texture("arrow.png").	setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest), };
		fade =  new Texture("fade.png").	setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

		/* create Sprites */
		pPlus = new Project(-1, -200, parts[3], this).setTarget(0);
		pButtons = new Sprite[]{
				new Sprite(tButtons[0]),
				new Sprite(tButtons[1]),
				new Sprite(tButtons[2]),
				new Sprite(fade),
				new Sprite(fade).setFlip(true, false).setPosition(-DRAW_HEIGHT, DRAW_Y + 1), };
		pArrow = new Sprite[]{
				new Sprite(tButtons[3]),
				new Sprite(tButtons[3]).setFlip(true, false).setPosition(2, DRAW_Y + 7), };
    }

    @Override
    public void draw() {
		/* set Font context */
		Font.useFont(font);

		/* draw background and edge */
		Render.drawRect(new FloatRectangle(0, 0, WindowTools.getWidth(), WindowTools.getHeight() - 4), new Color(0.2f, 0.2f, 0.2f));
		Render.drawRect(new FloatRectangle(0, WindowTools.getHeight() - 4, WindowTools.getWidth(), 2), new Color(0.3f, 0.3f, 0.3f));
		Render.drawRect(new FloatRectangle(0, WindowTools.getHeight() - 2, WindowTools.getWidth(), 2), new Color(0.4f, 0.4f, 0.4f));

		/* check if should or should not draw boundaries */
		boolean longArea = getTarget() <= WindowTools.getWidth() - (DRAW_HEIGHT * 2) - pPlus.getWidth();
		/* draw Projects */
		for(Project p : pr){
			p.draw(selected == p, drawPos, longArea ? -1 : WindowTools.getWidth() - 126);

			/* check if was clicked on this frame */
			if(p.wasPressed()){
				selected = p;
				/* make sure actual file is loaded */
				if(p.getLocation() != null) {
					ProjectTools.current(getProjectID(p.getLocation()));
				}
			}
		}

		/* if not big enough area loaded textures */
		if(longArea) {
			drawTarget = 0;
			pPlus.draw(false, drawPos, -1);
			/* if plus was pressed */
			if (pPlus.wasPressed()) {
				getAddFile();
			}
		} else {
		/* else render other sprites */
			drawFade();
			drawPlus();
			drawArrow();
		}

		/* draw settings & plus icon */
		drawSettings();
		drawInfo();
		moveScreen();
	}

	/* find project ID */
	private int getProjectID(String location) {
		/* loop for all projects loaded */
		for(int i = 0; i < ProjectTools.length();i ++){
			ProjectTools.use(i);
			/* if project file name is same as asked file name, return as correct id */
			if(ProjectTools.getFileName().equals(location)){
				return i;
			}
		}

		return -1;
	}

	/* move screen to target position */
	private void moveScreen() {
		/* if positions match, stop speed */
		if(drawTarget == drawPos){
			drawSpeed = 0f;

		/* if target is further than current position */
		} else if(drawTarget > drawPos){
			/* apply speed and position */
			drawSpeed += 1.5f;
			drawPos += drawSpeed;

			/* if moved past target position, halve speed */
			if(drawTarget < drawPos){
				drawPos = drawTarget;
				drawSpeed = 0f;
			}

		/* current is further than target position */
		} else {
			/* apply speed and position */
			drawSpeed -= 1.5f;
			drawPos += drawSpeed;

			/* if moved past target position, halve speed */
			if(drawTarget > drawPos){
				drawPos = drawTarget;
				drawSpeed = 0f;
			}
		}
	}

	/* draw arrow icons */
	private void drawArrow() {
		/* if mouse is in area of Sprite */
		boolean[] isInArea = new boolean[]{
				Mouse.compare(pArrow[0].getBounds()),
				Mouse.compare(pArrow[1].getBounds()),
				Mouse.isPressed(Mouse.Buttons.LEFT),
		};

		/* draw arrows */
		pArrow[0].setColor(isInArea[0] ? getHigh() : getNormal()).draw();
		pArrow[1].setColor(isInArea[1] ? getHigh() : getNormal()).draw();

		/* if button 0 pressed, move screen */
		if(isInArea[1] && isInArea[2]){
			drawTarget -= (WindowTools.getWidth() / 2);
			if(drawTarget < -30){
				drawTarget = -30;
			}
		} else if(isInArea[0] && isInArea[2]){
		/* if button 1 is pressed, move screen forwards */
			drawTarget += (WindowTools.getWidth() / 2);
			float max = getTarget()- (WindowTools.getWidth() - 186);
			if(drawTarget > max){
				drawTarget = max;
			}
		}
	}

	/* draw fade thing */
	private void drawFade() {
		pButtons[3].setColor(new Color(0.4f, 0.4f, 0.4f)).draw();
		pButtons[4].setColor(new Color(0.4f, 0.4f, 0.4f)).draw();
		Render.drawRect(new FloatRectangle(WindowTools.getWidth() - 126, DRAW_Y, 126, DRAW_HEIGHT), new Color(0.4f, 0.4f, 0.4f));
	}

	/* draw static plus icon */
	private void drawPlus() {
		/* if mouse is in area of Sprite */
		boolean isInArea = Mouse.compare(pButtons[0].getBounds());
		/* draw info icon */
		pButtons[0].setColor(isInArea ? getHigh() : getNormal()).draw();

		/* if left buttons is pressed */
		if(isInArea && Mouse.isPressed(Mouse.Buttons.LEFT)){
			getAddFile();
		}
	}

	/* draw info icon */
	private void drawInfo() {
		/* if mouse is in area of Sprite */
		boolean isInArea = Mouse.compare(pButtons[1].getBounds());
		/* draw info icon */
		pButtons[1].setColor(isInArea ? getHigh() : getNormal()).draw();
	}

	/* draw Settings icon */
	private void drawSettings() {
		/* if mouse is in area of Sprite */
		boolean isInArea = Mouse.compare(pButtons[2].getBounds());
		/* draw the settings icon */
		settRot += settRotSpd;
		pButtons[2].setRotation(settRot).setColor(isInArea ? getHigh() : getNormal()).draw();

		/* is the mouse inside the Sprite region? */
		if(isInArea){
			/* if so, make rotate faster */
			settRotSpd += 0.04f;
			/* cap the rotation speed */
			if(settRotSpd > 6f){
				settRotSpd = 6f;
			}
		} else {
			/* else make rotate slower */
			settRotSpd -= 0.04f;
			/* make sure rotation is stopped correctly */
			if(settRotSpd < 0f){
				settRotSpd = 0f;
			}
		}
	}

	@Override
    public void logic() {

    }

	/* method to find out which file should be loaded
	 * TODO: Allow any file to be opened */
	private void getAddFile() {
		FileSelectorController fsc = new FileSelectorController() {
			@Override
			boolean listFile(String s, String ext) {
				return ext.equals("SPP");
			}

			@Override
			boolean listFolder(String s) {
				return false;
			}

			@Override
			boolean openFile(String file, String ext) {
				boolean ends = ext.equals("SPP");

				if(ends){
					addFile(file);
				}

				return ends;
			}

			@Override
			boolean openFolder(String s) {
				return false;
			}

			@Override
			String createFolder(String s) {
				return null;
			}

			@Override
			String createFile(String file) {
				return file.endsWith(".SPP") ? file : file +".SPP";
			}

			@Override
			void error(String error, int errorCode) throws IllegalStateException {
				throw new IllegalStateException(this, error, false);
			}
		};

		FileSelectorConfiguration cfg = new FileSelectorConfiguration();
		cfg.setCanGoSubDir(false);
		cfg.setCanGoSuperDir(false);
		cfg.setCreateFiles(true);
		cfg.setCreateFolders(false);
		cfg.setInitialDir(File.getMainFolder() +"projects/");

		WindowTools.addWindow("window/FileSelector", new Object[]{ cfg, fsc });
	}

	/* open file as a project and add it to Project list */
	private void addFile(String file) {
		/* try to create a new project with file name */
		if(ProjectTools.create(file)){
			int id = ProjectTools.getID();
			/* get full file name */
			String name = ProjectTools.getFileName();

			/* search for any Project containing file of the same name */
		/*	for(Project p : pr){
				if(p.getLocation().equals(name)){
					/* update name and select */
		/*			p.setName(ProjectTools.getValue("name"));
					selected = p;
					return;
				}
			}*/

			/* no project found, create a new one */
			float start = getTarget(), target = getTarget_(start);
			/* create the actual project */
			Project pp = new Project(id, start, parts[1], this).setTarget(target);
			/* add to Project list */
			pr.add(0, pp);

			/* set position for plus as well */
			pPlus.setTarget(target + pp.getWidth() + ((PART_VISIBLESIZE - 8) * 2));
		}
	}

	/* gets the actual target position of the new Project */
	private float getTarget_(float target) {
		/* loop for all projects */
		for(Project p : pr){
			/* if Project target and recorded target match, calculate actual target */
			if(p.getTarget() == target){
				return target + p.getWidth() + ((PART_VISIBLESIZE - 8) * 2);
			}
		}

		return 0;
	}

	/* get target position of last Project */
	private float getTarget() {
		float last = -200;

		/* loop for all projects */
		for(Project p : pr){
			if(p.getTarget() > last){
				last = p.getTarget();
			}
		}

		return last;
	}

	@Override
    public void dispose() {
		/* dispose all Textures */
		for(Texture t : tButtons) {
			t.dispose();
		}
		parts[0].dispose();
		parts[1].dispose();
		parts[2].dispose();
    }

    @Override
    public void resize(int width, int height) {
		WindowTools.resize(AppTools.getWidth(), 36);
		/* if not null, set settings icon position */
		if(pButtons != null) {
			float x = WindowTools.getWidth() - 37 - (DRAW_HEIGHT * 2);
			/* fix all positions */
			for(Sprite s : pButtons) {
				s.setPosition(x, DRAW_Y + 1);
				x += DRAW_HEIGHT;
			}

			/* fix fade positions */
			pButtons[3].setPosition(WindowTools.getWidth() - 168, DRAW_Y + 1);
			pButtons[4].setPosition(0, DRAW_Y + 1);
			pArrow[0].setPosition(WindowTools.getWidth() - 168 + 10, DRAW_Y + 7);
		}
    }

    @Override
    public void buttonEvent(int id) {
		/* no buttons associated */
    }

    @Override
    public boolean unFocus() {
        return true;
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

	/* gets specified part */
	public Texture getPart(int i) {
		return parts[i];
	}

	/* functions to return different colors */
	public Color getNormal(){
		return cols[0];
	}

	public Color getSel(){
		return cols[1];
	}

	public Color getHigh(){
		return cols[2];
	}
}