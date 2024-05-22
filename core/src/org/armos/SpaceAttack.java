package org.armos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.armos.tools.ScrollingBackground;

public class SpaceAttack extends Game {
	SpriteBatch batch;
	Texture img;

	public ScrollingBackground scrollingBackground;
	
	@Override
	public void create () {
		this.scrollingBackground = new ScrollingBackground();
		this.batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public void resize(int width, int height) {
		this.scrollingBackground.resize(width, height);
		super.resize(width, height);
	}
}
