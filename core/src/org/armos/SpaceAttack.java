package org.armos;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.armos.tools.ScrollingBackground;

import static org.armos.constants.Constants.HEIGHT;
import static org.armos.constants.Constants.WIDTH;

public class SpaceAttack extends Game {

	public static boolean IS_MOBILE = false;
	SpriteBatch batch;
	Texture img;

	public ScrollingBackground scrollingBackground;
	private OrthographicCamera cam;
	private StretchViewport viewport;
	
	@Override
	public void create () {

		if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {
			IS_MOBILE = true;
		}
		this.scrollingBackground = new ScrollingBackground();
		this.batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));

		this.cam = new OrthographicCamera();
		this.viewport = new StretchViewport(WIDTH, HEIGHT, this.cam);
		this.viewport.apply();
		this.cam.position.set(WIDTH / 2, HEIGHT / 2, 0);
		cam.update();
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(this.cam.combined);
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public void resize(int width, int height) {
		this.viewport.update(width, height);
		this.scrollingBackground.resize(width, height);
		super.resize(width, height);
	}
}
