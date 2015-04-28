package soni.plane.api.tools;

import com.badlogic.gdx.math.Rectangle;

public final class FloatRectangle {
    public float x, y, width, height;

    public FloatRectangle(){
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

    public FloatRectangle(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public FloatRectangle(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public FloatRectangle(FloatRectangle rect){
		x = rect.x;
		y = rect.y;
		width = rect.width;
		height = rect.height;
    }

	public FloatRectangle(Rectangle rect) {
		x = rect.x;
		y = rect.y;
		width = rect.width;
		height = rect.height;
	}

	public FloatRectangle copy(){
        return new FloatRectangle(this);
    }

    public Rectangle toRectangle() {
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }

	public String toString(){
		return x + "," + y + "," + width + "," + height;
	}
}
