package soni.plane.gs;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.opengl.GL11;
import soni.plane.gs.util.ProjectManager;

public class Main implements ApplicationListener {
    private static final int WIDTH = 600, HEIGHT = 600, FPS = 30;

    /* folder to save files to TODO: Check if this works on all OS */
    public static final String folder = System.getenv("APPDATA") +"/.SoniPlane/";
    /* version ID of the application */
    private static final String version = "2.0";

	/* camera projection used */
	private static OrthographicCamera camera;
	/* SpriteBatch instance to used */
	private static SpriteBatch batch;
	/* if system is running */
	private static boolean isRunning = true;

	private static String getTitle() {
        return "SoniPlane "+ version;
    }

    public static void exit(String caller){
        System.err.println("Something unexpected happened and");
        System.err.println("'"+ caller +"'");
        System.err.println("requested SoniPlane to close! We are sorry for any inconveniences caused by this!");
		Gdx.app.exit();
    }

    public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.backgroundFPS = 30;
		cfg.foregroundFPS = 60;
		cfg.forceExit = false;
		cfg.height = HEIGHT;
		cfg.width = WIDTH;
		cfg.resizable = true;
		cfg.title = getTitle();
		cfg.audioDeviceSimultaneousSources = 0;
		cfg.audioDeviceBufferCount = 0;
		cfg.audioDeviceBufferSize = 0;
		cfg.initialBackgroundColor = Color.BLACK;

        new LwjglApplication(new Main(), cfg);

		/* run this loop to wait this being finished */
		while(true){
			if(!isRunning){
				break;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void create() {
        /* init components and create project which is used to render anything for now */
		ProjectManager.create(null);
		ProjectManager.setProject(0);

		/* set up various internal shits */
		camera = new OrthographicCamera();
		camera.setToOrtho(true, WIDTH, HEIGHT);
		batch = new SpriteBatch();
	}

	@Override
	public void resize(int w, int h) {
		camera.setToOrtho(true, w, h);
		ProjectManager.get().getDraw().resize(w, h);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		ProjectManager.get().getDraw().draw();
		batch.end();

		ProjectManager.get().getDraw().logic(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		isRunning = false;
	}

	public static SpriteBatch getBatch() {
		return batch;
	}

	public static OrthographicCamera getCamera() {
		return camera;
	}
}
