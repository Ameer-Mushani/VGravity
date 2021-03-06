package com.catani.vgravity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.catani.vgravity.Animator;
import com.catani.vgravity.Constants;
import com.catani.vgravity.GamVGravity;
import com.sun.org.apache.bcel.internal.classfile.Constant;


public class ScrMainMenu implements Screen, InputProcessor {

    GamVGravity game;
    TextureAtlas textureAtlas;
    Animator animator;
    Sprite sprLogo; //logo must be a sprite not texture to modify opacity
    float alphaCount; //alpha value for sprite to be incremented
    boolean touchDown;

    Texture txLogo;
    public ScrMainMenu(GamVGravity _game) {
        this.game = _game;
        animator = new Animator(game.assets);
        sprLogo = new Sprite(new Texture("logo2.png"));
        sprLogo.setPosition(Constants.WORLDWIDTH/2-500, Constants.WORLDHEIGHT/2);
        sprLogo.setSize(1000,400);
        alphaCount = 0;
    }

    @Override
    public void show() {
        if(Gdx.input.isTouched()){
            touchDown = true;
        }
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        game.camera.update();
        Gdx.gl.glClearColor(230 / 255f, 220 / 255f, 200 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.sbg.staticImage(game.batch);

        if (game.chrMain.getX() < 500) {
            game.chrMain.menuAnimation(game.batch);
        } else {
            game.drawImage("Hero.png", 500, Constants.FLOOR);
            if(alphaCount < 1)
                alphaCount += 0.025;
            sprLogo.setAlpha(alphaCount);
            sprLogo.draw(game.batch);
            animator.drawAni(game.batch, 700, Constants.FLOOR);

            if (Gdx.input.justTouched() && !touchDown) {
                game.changeScreen(Screens.SCRGAME);
            }
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
        game.camera.position.set(game.camera.viewportWidth / 2, game.camera.viewportHeight / 2, 0);
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
        textureAtlas.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(touchDown){
            touchDown = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return true;
    }
}

