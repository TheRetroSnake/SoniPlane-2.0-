package soni.plane.gs;

import soni.plane.api.graphics.Font;
import soni.plane.api.graphics.Sprite;
import soni.plane.api.graphics.Texture;
import soni.plane.api.tools.FloatRectangle;
import soni.plane.api.tools.Mouse;
import soni.plane.api.tools.ProjectTools;

public class Project {
	/* sprite for background behind text */
	private Sprite spr;
	/* variables to identify project */
	private String prLoc;
	private String prName;
	/* reference to ProjectMenu */
	private ProjectMenu ref;

	/* true if is hovering on sprite and was pressed mouse */
	private boolean pressed;
	/* target x-pos */
	private float target;
	/* current x-pos */
	private float current = 0;
	/* current speed to move sprite */
	private float speed = 0;
	/* max change of Speed per tick */
	private final float SPEED_CHANGE = 0.25f;
	private final float SPEED_DIVIDER = 1.4f;
	private final float SPEED_MINIMUM = 1f;

	public Project(int prID, float startX, Texture tx, ProjectMenu pr){
		/* create new sprite with set position */
		ref = pr;
		spr = new Sprite(tx);
		spr.setPosition(startX, ProjectMenu.DRAW_Y);

		/* set current position as well */
		current = startX;

		/* if ID greater than 0 */
		if(prID >= 0) {
			/* get project file name and name itself */
			ProjectTools.use(prID);
			prLoc = ProjectTools.getFileName();
			prName = ProjectTools.getValue("name");
			/* set Sprite size according to name
			* TODO: Figure out why needs to multiply by 1.5f to get correct size! */
			spr.setSize(Font.getWidth(prName) * 1.5f, ProjectMenu.DRAW_HEIGHT);
		}
	}

	public void draw(boolean selected, float pos, float max) {
		doSpeed();
		/* make sure we don't draw beyond visible area */
		float part2x = current + spr.getBounds().width + 4 - pos, part0x = current - ProjectMenu.DRAW_HEIGHT + 5 - pos;
		if(max != -1 && !(part2x >= 0 && part0x < max)){
			return;
		}

		/* check if mouse is on the sprite */
		boolean isInArea = !(max != -1 && !(Mouse.getX() >= ProjectMenu.DRAW_HEIGHT && Mouse.getX() < max - ProjectMenu.DRAW_HEIGHT)) &&
				Mouse.compare(new FloatRectangle(current - (ProjectMenu.DRAW_HEIGHT / 2) - pos, ProjectMenu.DRAW_Y,
				prName == null ? (spr.getBounds().width - ProjectMenu.DRAW_HEIGHT) :
						(spr.getBounds().width + ProjectMenu.DRAW_HEIGHT), ProjectMenu.DRAW_HEIGHT));
		pressed = isInArea && Mouse.isPressed(Mouse.Buttons.LEFT) && !selected;

		/* if this is not plus */
		if(prName != null) {
			/* set correct position and draw */
			spr.setPosition(current + 4 - pos, ProjectMenu.DRAW_Y);
			spr.setColor(isInArea ? ref.getHigh() : selected ? ref.getNormal() : ref.getSel());
			spr.draw();

			/* draw end pieces */
			new Sprite(ref.getPart(0)).setPosition(part0x, ProjectMenu.DRAW_Y).
					setColor(isInArea ? ref.getHigh() : selected ? ref.getNormal() : ref.getSel()).draw();
			new Sprite(ref.getPart(2)).setPosition(part2x, ProjectMenu.DRAW_Y).
					setColor(isInArea ? ref.getHigh() : selected ? ref.getNormal() : ref.getSel()).draw();

			/* draw the text */
			Font.setColor(isInArea ? ref.getHigh() : selected ? ref.getNormal() : ref.getSel());
			Font.drawCentered(prName, spr.getBounds());

		} else {
		/* draw plus */
			spr.setPosition(current - ProjectMenu.DRAW_HEIGHT + 4 - pos, ProjectMenu.DRAW_Y);
			spr.setColor(isInArea ? ref.getHigh() : ref.getNormal());
			spr.draw();
		}
	}

	/* used to update Sprite position */
	private void doSpeed() {
		/* if positions match, stop speed */
		if(target == current){
			speed = 0f;

		/* if target is further than current position */
		} else if(target > current){
			/* apply speed and position */
			speed += SPEED_CHANGE;
			current += speed;

			/* if moved past target position, halve speed */
			if(target < current){
				speed /= SPEED_DIVIDER;

				/* if too small speed, stop and fix position */
				if(speed <= SPEED_MINIMUM){
					speed = 0f;
					current = target;
				}
			}

		/* current is further than target position */
		} else {
			/* apply speed and position */
			speed -= SPEED_CHANGE;
			current += speed;

			/* if moved past target position, halve speed */
			if(target > current){
				speed /= SPEED_DIVIDER;

				/* if too small speed, stop and fix position */
				if(speed >= -SPEED_MINIMUM){
					speed = 0f;
					current = target;
				}
			}
		}
	}

	/* set position to target */
	public Project setTarget(float target) {
		this.target = target;
		return this;
	}

	/* gets stored file location */
	public String getLocation(){
		return prLoc;
	}

	/* stores new file location */
	public void setLocation(String location){
		prLoc = location;
	}

	/* checks if this is in expected position */
	public boolean isOnPosition(){
		return current == target;
	}

	/* returns whether was pressed or not */
	public boolean wasPressed(){
		return pressed;
	}

	/* set correct Project name */
	public void setName(String name) {
		prName = name;
	}

	/* get position this Project is targeting */
	public float getTarget() {
		return target;
	}

	/* get width */
	public float getWidth(){
		return spr.getBounds().width;
	}
}