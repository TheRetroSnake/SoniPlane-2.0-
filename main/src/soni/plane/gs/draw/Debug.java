package soni.plane.gs.draw;

import com.badlogic.gdx.Gdx;
import soni.plane.api.graphics.*;
import soni.plane.api.java.lang.Maths;
import soni.plane.api.tools.AppTools;
import soni.plane.api.tools.Arguments;
import soni.plane.api.tools.FloatRectangle;
import soni.plane.gs.Main;
import soni.plane.gs.util.Keyboard;
import soni.plane.gs.util.ProjectManager;

public class Debug {
	/* generate font for use */
	private static String font = soni.plane.api.graphics.Font.createFont("DejaVuSansMono", 10, new Color(1f, 1f, 1f, 1f));

	/* overlays with debug information */
	public static void render() {
		soni.plane.api.graphics.Font.useFont(font);

		doStrings(0, 0, new String[]{
				"Windows loaded: " + ProjectManager.get().getDraw().createFullList().length,
				"Arguments created: " + Arguments.getAmount(),
				"KeyListeners created: " + Keyboard.getAmount(),
		});

		int mem = (int)((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 1f /
				sfdiv(Runtime.getRuntime().totalMemory()) * 100.0f);
		int mem2 = (int)(Runtime.getRuntime().totalMemory() * 1f / sfdiv(Runtime.getRuntime().maxMemory()) * 100.0f);
		int time = (int)(Main.renderTime * 1f / getFPSMaxTime() * 100.0f);

		doStrings(0, (int) (AppTools.getHeight() - (soni.plane.api.graphics.Font.getHeight("Q") * 6)) - 6, new String[]{
				"Draw calls: "+ (int)(Main.drawCallsThisFrame * 1f / sfdiv(Main.drawCallsInTotal)) +"% "+
						Main.drawCallsThisFrame +"/"+ Main.drawCallsInTotal,
				"Allocated memory: "+ mem2 +"% "+ Maths.BtoMB(Runtime.getRuntime().totalMemory()) +"MB/"+
						Maths.BtoMB(Runtime.getRuntime().maxMemory()) +"MB",
				"Used memory: "+ mem +"% "+ Maths.BtoMB(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) +"MB/"+
						Maths.BtoMB(Runtime.getRuntime().totalMemory()) +"MB",
				"Processor usage: "+ time +"% "+ Main.renderTime +"ns/"+ getFPSMaxTime() +"ns ("+ Gdx.graphics.getFramesPerSecond() +" FPS)",
		});
	}

	/* SAFE DIVISION
	 * makes sure int to be divided is nonzero */
	private static long sfdiv(long i) {
		return i == 0 ? 1 : i;
	}

	/* draw specified Strings */
	private static void doStrings(int iniX, int iniY, String[] render) {
		/* get height of each line */
		float h = soni.plane.api.graphics.Font.getHeight(render[0]) + 4;

		/* loop for all Strings */
		for(String r : render){
			Render.drawRect(new FloatRectangle(iniX, iniY, soni.plane.api.graphics.Font.getWidth(r) + 4, h), new Color(0.2f, 0.2f, 0.2f, 0.5f));
			/* draw */
			soni.plane.api.graphics.Font.draw(r, iniX + 2, iniY + 2);
			/* next line */
			iniY += h;
		}
	}

	/* get maximum time to spend per frame with current frame rate
	 * (approx, no way to get 100% reliable result) */
	private static long getFPSMaxTime() {
		long res = 1000000000 / sfdiv(Gdx.graphics.getFramesPerSecond());
		return sfdiv(res);
	}
}
