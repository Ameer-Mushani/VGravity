package com.catani.vgravity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.catani.vgravity.Assets;
import com.catani.vgravity.Constants;
import com.catani.vgravity.GamVGravity;

/**
 * Created by am44_000 on 2018-09-28.
 */

public class ScrGameover implements Screen {
	GamVGravity game;
	int score, coins, highscore;
	public ScrGameover(GamVGravity game, int score, int coins, int highscore) {
		this.game = game;
		this.score = score;
		this.coins = coins;
		this.highscore = highscore;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		game.camera.update();
		Gdx.gl.glClearColor(230 / 255f, 220 / 255f, 200 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		game.batch.setProjectionMatrix(game.camera.combined);
		game.drawImage("background1.png", 0, 0);
		game.drawText(Assets.Fonts.GAMEOVER, "Game over", Constants.WORLDWIDTH/2- 500, Constants.CEILING/2+450);
		game.drawText(Assets.Fonts.GAMEOVERSMALL,"Score "+ score, Constants.WORLDWIDTH/2- 500, Constants.CEILING/2 +120);
		game.drawText(Assets.Fonts.GAMEOVERSMALL,"Coins "+ coins, Constants.WORLDWIDTH/2- 500, Constants.CEILING/2);
		game.drawText(Assets.Fonts.GAMEOVERSMALL,"Hi-Score "+ score, Constants.WORLDWIDTH/2- 500, Constants.CEILING/2 - 120);

//        animator.AnmFadePlayBtn(batch, 630, 730);
//        batch.draw(assets.manager.get("Mario.png", Texture.class), 500, 128, 75, 150);

		game.batch.end();
		if(Gdx.input.isTouched()){
			game.changeScreen(Screens.SCRMAINMENU);
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
