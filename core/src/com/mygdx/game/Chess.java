package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Vector;

public class Chess extends ApplicationAdapter {
	final static int CAM_WIDTH = 2400;
	final static int CAM_HEIGHT = 2400;
	OrthographicCamera cam;
	SpriteBatch batch;
	FitViewport viewport;

	ChessDisplay chessDisplay;
	Texture texture;

	private Vector2 mouseInWorld2D;
	private Vector3 mouseInWorld3D;

	InputMaster inputMaster;
	Board board;
	
	@Override
	public void create () {
		cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT);
		viewport = new FitViewport(CAM_WIDTH, CAM_HEIGHT, cam);
		cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2, 0);
		batch = new SpriteBatch();

		board = new Board();
		chessDisplay = new ChessDisplay(board);
		inputMaster = new InputMaster(CAM_WIDTH / 8, board);

		mouseInWorld2D = new Vector2();

		mouseInWorld3D = new Vector3();
	}

	@Override
	public void render () {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		chessDisplay.redraw(batch);
		setInput();
		if(board.getCheckmate() && chessDisplay.victoryTimer >= 0) {
			chessDisplay.drawVictory(batch, board.getWinner());
		}
		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && !board.getCheckmate()) {
			inputMaster.setBoardX(true);
			inputMaster.setBoardY(true);
		}
		else if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !board.getCheckmate()) {
			inputMaster.setBoardX(false);
			inputMaster.setBoardY(false);
			inputMaster.putInput();
		}
	}

	public void setInput() {
		mouseInWorld3D.x = Gdx.input.getX();
		mouseInWorld3D.y = Gdx.input.getY();
		mouseInWorld3D.z = 0;
		cam.unproject(mouseInWorld3D);
		mouseInWorld2D.x = mouseInWorld3D.x;
		mouseInWorld2D.y = mouseInWorld3D.y;

		inputMaster.setTrueInput((int)mouseInWorld2D.x, (int)mouseInWorld2D.y);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

}
