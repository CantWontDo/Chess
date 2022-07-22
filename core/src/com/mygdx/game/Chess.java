package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Chess extends ApplicationAdapter {
	final static int CAM_WIDTH = 800;
	final static int CAM_HEIGHT = 800;
	OrthographicCamera cam;
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	FitViewport viewport;

	Sprite img;

	Board chessBoard;
	
	@Override
	public void create () {
		cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT);
		viewport = new FitViewport(CAM_WIDTH, CAM_HEIGHT, cam);
		cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2, 0);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		Texture texture = new Texture("ChessSpace.png");
		texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
		img = new Sprite(texture);
		chessBoard = new Board();
	}

	@Override
	public void render () {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Space space = chessBoard.getSpace(i, j);
				batch.setColor(space.convertColor(space.getPiece().getColor()));
				space.draw(batch, CAM_WIDTH / 8, img);
			}
		}
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
