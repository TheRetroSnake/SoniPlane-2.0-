package soni.plane.api.graphics;

import com.badlogic.gdx.Gdx;
import soni.plane.gs.Loader;
import soni.plane.gs.util.ProjectManager;

public final class Texture {
	/* enumeration for available TextureFilters */
	public enum TextureFilter {
		Linear(com.badlogic.gdx.graphics.Texture.TextureFilter.Linear),
		Nearest(com.badlogic.gdx.graphics.Texture.TextureFilter.Nearest),
		MipMap(com.badlogic.gdx.graphics.Texture.TextureFilter.MipMap),
		MipMapLinearLinear(com.badlogic.gdx.graphics.Texture.TextureFilter.MipMapLinearLinear),
		MipMapNearestNearest(com.badlogic.gdx.graphics.Texture.TextureFilter.MipMapNearestNearest),
		MipMapLinearNearest(com.badlogic.gdx.graphics.Texture.TextureFilter.MipMapLinearNearest),
		MipMapNearestLinear(com.badlogic.gdx.graphics.Texture.TextureFilter.MipMapNearestLinear);

		private com.badlogic.gdx.graphics.Texture.TextureFilter value;

		private TextureFilter(com.badlogic.gdx.graphics.Texture.TextureFilter value) {
			this.value = value;
		}
	}

	/* stored texture reference */
	private com.badlogic.gdx.graphics.Texture tx;

	/* create new Texture
	 * NOTE: Always dispose any created Textures! */
	public Texture(String file){
		tx = new com.badlogic.gdx.graphics.Texture(Gdx.files.absolute(
				Loader.getDataFolder() +"res/graphics/"+ ProjectManager.get().getDraw().getContext().getLocation() +"/"+ file));
	}

	/* return Texture from memory */
	public com.badlogic.gdx.graphics.Texture get(){
		return tx;
	}

	/* dispose this Texture */
	public void dispose(){
		tx.dispose();
		tx = null;
	}

	/* set filter for this Texture */
	public Texture setFilter(TextureFilter minFilter, TextureFilter magFilter){
		tx.setFilter(minFilter.value, magFilter.value);
		return this;
	}

	/* get Texture width */
	public int getWidth(){
		return tx.getWidth();
	}

	/* get Texture height */
	public int getHeight(){
		return tx.getHeight();
	}
}
