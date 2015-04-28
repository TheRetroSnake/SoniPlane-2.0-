package soni.plane.api.implement;

public interface Window {
    public void init();
    public void draw();
    public void logic();
    public void dispose();
    public void resize(int width, int height);
    public void buttonEvent(int id);
    public boolean unFocus();
    public boolean focus();
    public void update();
    public boolean checkUpdate(String version);
}
