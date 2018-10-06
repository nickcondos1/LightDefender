package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.MenuState;


public class LightDefender extends ApplicationAdapter
{
	public static final int WIDTH = 1440; //288
	public static final int HEIGHT = 2560; //512
	public static final String TITLE = "Light Defender";

	private SpriteBatch batch;
	private GameStateManager gsm;
	public static AdHandler handler;
	public static boolean toggle;

	public LightDefender(AdHandler handler)
	{
		this.handler = handler;
	}



	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(35/255f, 35/255f, 35/255f, 1);
		gsm.push(new MenuState(gsm));


	}

	@Override
	public void render () {
		handler.showAds(toggle);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}


	/*
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}*/
}
