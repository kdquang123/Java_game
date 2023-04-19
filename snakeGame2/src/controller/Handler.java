package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Snake;
import view.GameFrame;
import view.GameScreen;
import view.SnakeGame;

public class Handler implements KeyListener {
	private Snake sn;
	private GameScreen gs;
	private SnakeGame sng;

	public Handler(Snake sn, GameScreen gs, SnakeGame sng) {
		this.sn = sn;
		this.gs = gs;
		this.sng = sng;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (sn.isGameOver() == true || sn.isWin()) {
				sn.setGameOver(false);
				sng.dispose();
				new GameFrame();
			}

			if (sn.isStop() == false) {
				sn.setStop(true);
			} else if (sn.isStop() == true) {
				sn.setStop(false);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (sn.getVector() != Snake.GO_UP && sn.getVector() != Snake.GO_DOWN) {
				sn.setVector(Snake.GO_DOWN);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (sn.getVector() != Snake.GO_UP && sn.getVector() != Snake.GO_DOWN) {
				sn.setVector(Snake.GO_UP);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (sn.getVector() != Snake.GO_LEFT && sn.getVector() != Snake.GO_RIGHT) {
				sn.setVector(Snake.GO_LEFT);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (sn.getVector() != Snake.GO_LEFT && sn.getVector() != Snake.GO_RIGHT) {
				sn.setVector(Snake.GO_RIGHT);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
