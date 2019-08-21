package com.catani.vgravity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.catani.vgravity.Assets;
import com.catani.vgravity.Constants;
import com.catani.vgravity.obstacles.ObsFlying;
import com.catani.vgravity.obstacles.ObsPitfall;
import com.catani.vgravity.obstacles.ObsSpinning;
import com.catani.vgravity.ScrollingBackground;
import com.catani.vgravity.SprCollectables;
import com.catani.vgravity.obstacles.ObsTree;
import com.catani.vgravity.obstacles.SprObstacle;
import com.catani.vgravity.GamVGravity;

public class ScrGame implements Screen, InputProcessor {
    GamVGravity game;

    private Vector3 vTouched;
    ShapeRenderer sr;
    SprObstacle obsShip;
    ObsFlying obsFlying;
    ObsSpinning obsSpinning;
    ObsPitfall obsPitfall;
    ObsTree obsTree;

    SprCollectables sprCoin;

    Array<SprObstacle> obstacles;
    ScrollingBackground sbg;
    Music musElectro;

    int nScore, ncoinCounter = 0;
    private int nObsAvoided;
    float fGameSpeed, fPixelsRan;
    int nObstacle;

    public ScrGame(GamVGravity _game) {

        this.game = _game;

        Gdx.input.setInputProcessor(this);
        vTouched = new Vector3();

        obsShip = new SprObstacle("ship.png", -18);
        obsPitfall = new ObsPitfall("floorhole.png", -13);
        obsFlying = new ObsFlying("Ameer.png", -5);
        obsSpinning = new ObsSpinning("Axe.png", -13, 1);
        obsTree = new ObsTree("Tree.png", -10);
        obstacles = new Array<SprObstacle>(new SprObstacle[]{obsPitfall});

        resize(1920, 1080);

        sprCoin = new SprCollectables("coin.png", -10);

        sr = new ShapeRenderer();
        sbg = new ScrollingBackground();
        musElectro = game.assets.manager.get("electroman.mp3", Music.class);
        musElectro.setLooping(true);
        nScore = 0;
        nObsAvoided = 0;
        fGameSpeed = 12;
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        nScore = 0;

        resetObstacles();

        fGameSpeed = 10;
        sbg.setSrcollSpeed(fGameSpeed);
        fPixelsRan = 0;

        ncoinCounter = 0;
        game.chrMain.reset();
        musElectro.play();
    }

    public void resetObstacles() {
        obstacles.clear();
        obstacles.add(obsPitfall);
        obsPitfall.setX(Constants.WORLDWIDTH + obsPitfall.getWidth());
        //obsPitfall.setTexture(game.assets.manager.get("floorhole.png", Texture.class));
        //obsPitfall.isPitfallFlipped = false;
        obsTree.isTreeFlipped = false;
    }


    @Override
    public void render(float delta) {
        game.camera.update();
        Gdx.gl.glClearColor(230 / 255f, 220 / 255f, 200 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.setProjectionMatrix(game.camera.combined);

        sbg.render(game.batch);

        fPixelsRan += fGameSpeed;
        nScore = (int) ((fPixelsRan / Constants.WORLDWIDTH) * 3); //convert pixels ran to score units
        if (nScore > 2) {
            for (SprObstacle obstacle : obstacles) {
                obstacle.render(game.batch);
                obstacle.setXVel(fGameSpeed * -1);


                if (obstacle.getX() < -300) {
                    redrawObstacles(obstacle);
                }

                if (obstacle.isHit(game.chrMain.getBoundingRectangle())) {
                    deathAnimations(obstacle);
                }

            }
        }
        sprCoin.render(game.batch);
        sprCoin.setXVel(fGameSpeed * -1);
        redrawCoin();
        game.chrMain.render(game.batch);
        game.chrMain.constrain();
        text();
        game.batch.end();
    }

    public void redrawObstacles(SprObstacle obstacle) {

        obstacles.clear();

        if (nScore < 15) {
            nObstacle = MathUtils.random(0, 1);
        } else if (nScore >= 15 && nScore < 30) {
            nObstacle = MathUtils.random(0, 2);
        } else if (nScore >= 30 && nScore < 50) {
            nObstacle = MathUtils.random(0, 3);
        } else if (nScore >= 50) {
            nObstacle = MathUtils.random(0, 4);
        }

        obstacle.setX(Constants.WORLDWIDTH + 200);
        if (nObstacle == 0) {
            obstacles.add(obsPitfall);
            obsPitfall.reDraw();
        } else if (nObstacle == 1) {
            obstacles.add(obsTree);
            obsTree.reDraw();
        } else if (nObstacle == 2) {
            obstacles.add(obsSpinning);
            obsSpinning.reDraw();
        } else if (nObstacle == 3) {
            obstacles.add(obsShip);
            obsShip.reDraw();
        } else if (nObstacle == 4) {
            obstacles.add(obsFlying);
            obsFlying.reDraw();
        }




        nObsAvoided++;


        if (nObsAvoided % 1 == 0 && nObsAvoided != 0 && fGameSpeed < 32) {
            fGameSpeed += 0.75;
        }
    }


    public void deathAnimations(SprObstacle obstacle) {
        if (obstacle == obsPitfall) {
            obsPitfall.deathAnimation(game.chrMain);
            if (game.chrMain.getY() + game.chrMain.getHeight() <= 10 || game.chrMain.getY() >= Constants.CEILING) {
                game.setScrGameover(nScore, ncoinCounter);
               // game.changeScreen(Screens.SCRGAMEOVER);
            }
        } else {
            game.setScrGameover(nScore, ncoinCounter);
            //game.changeScreen(Screens.SCRGAMEOVER);
        }
    }


    public void redrawCoin() {
        if (sprCoin.getX() < 0 - sprCoin.getWidth()) {
            sprCoin.setX(Constants.WORLDWIDTH);
            sprCoin.setY(MathUtils.random(Constants.FLOOR, Constants.CEILING - sprCoin.getHeight()));
        }

        if (sprCoin.isHit(game.chrMain.getBoundingRectangle())) {
            ncoinCounter += 1;
            sprCoin.setX(Constants.WORLDWIDTH);
            sprCoin.setY(MathUtils.random(Constants.FLOOR, Constants.CEILING - sprCoin.getHeight()));
        }
    }

    private void text() {
        game.drawText(Assets.Fonts.SCORE, Integer.toString(nScore) + "m", 0, 1065);

        if (ncoinCounter < 10) {
            game.drawText(Assets.Fonts.SCORE, Integer.toString(ncoinCounter), 1845, 1065);
        } else if (ncoinCounter >= 10 && ncoinCounter < 100) {
            game.drawText(Assets.Fonts.SCORE, Integer.toString(ncoinCounter), 1775, 1065);
        } else if (ncoinCounter >= 100 && ncoinCounter < 1000) {
            game.drawText(Assets.Fonts.SCORE, Integer.toString(ncoinCounter), 1705, 1065);
        } else if (ncoinCounter >= 1000 && ncoinCounter < 10000) {
            game.drawText(Assets.Fonts.SCORE, Integer.toString(ncoinCounter), 1635, 1065);
        }
    }


    @Override
    public void hide() {
        musElectro.stop();
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


    //disposes so that there are no memory leakes, so no extra memory is used
    @Override
    public void dispose() {
        game.batch.dispose();
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
        if (game.chrMain.getY() == Constants.FLOOR || game.chrMain.getY() == Constants.CEILING - game.chrMain.getHeight()) {
            if (character == ' ') {
                game.chrMain.flipY();
            }
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("MouseX: " + screenX + " Mouse Y: " + screenY);

        if (game.chrMain.getY() == Constants.FLOOR) {
            game.chrMain.setYVel(20);
        } else if (game.chrMain.getY() == Constants.CEILING - game.chrMain.getHeight()) {
            game.chrMain.setYVel(-20);
        }

        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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