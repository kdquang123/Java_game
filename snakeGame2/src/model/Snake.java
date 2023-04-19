package model;

import java.awt.Color;
import java.awt.Graphics;

import view.GameScreen;

public class Snake {
	private int size;
	private int[] x;
	private int[] y;
	private boolean isUpdate;
	private boolean isGameOver;
	private boolean isStop;
	private boolean isWin;

	// Các trạng thái của rắn
	public static int GO_UP = 0;
	public static int GO_DOWN = 1;
	public static int GO_RIGHT = 2;
	public static int GO_LEFT = 3;
	public static int STOP = 4;
	
	public static int MAX_LENGTH=390;

	int vector = Snake.GO_RIGHT;

	public Snake() {
		this.isWin=false;
		this.isGameOver = false;
		this.isUpdate = true;
		this.isStop=false;
		this.size = 4;
		this.x = new int[MAX_LENGTH];
		this.y = new int[MAX_LENGTH];

		this.x[0] = 5;
		this.y[0] = 8;
		this.x[1] = 4;
		this.y[1] = 8;
		this.x[2] = 3;
		this.y[2] = 8;
		this.x[3] = 2;
		this.y[3] = 8;

	}

	public void drawSnake(Graphics g) {
		g.setColor(Color.black);
		g.fillRoundRect(x[0] * 25 + 1, y[0] * 25 + 1, 23, 23, 20, 20);
		g.setColor(new Color(0, 0, 153));
		for (int i = 1; i < size; i++) {
			g.fillRoundRect(x[i] * 25, y[i] * 25, 25, 25, 15, 15);
		}
	}

	public int getVector() {
		return this.vector;
	}

	public void setVector(int v) {
		if (this.vector != v && isUpdate) {
			this.vector = v;
			isUpdate = false;
		}
	}

	public void update() {
		if (this.isGameOver == false) {
			for (int i = size - 1; i > 0; i--) {
				x[i] = x[i - 1];
				y[i] = y[i - 1];
			}

			if (vector == Snake.GO_DOWN) {
				y[0]++;
				if (y[0] == 20) {
					y[0] = 0;
				}
			}
			if (vector == Snake.GO_UP) {
				y[0]--;
				if (y[0] == -1) {
					y[0] = 19;
				}
			}
			if (vector == Snake.GO_LEFT) {
				x[0]--;
				if (x[0] == -1) {
					x[0] = 19;
				}
			}
			if (vector == Snake.GO_RIGHT) {
				x[0]++;
				if (x[0] == 20) {
					x[0] = 0;
				}
			}
			isUpdate = true;
		}

	}

	// kiem tra an moi
	public boolean checkEatFood() {
		if (x[0] == GameScreen.point.x && y[0] == GameScreen.point.y) {
			this.eatFood();
			return true;
		}
		return false;
	}

	// kiem tra moi tao ra co trung voi than ran hay khong
	public boolean checkCreateFood() {
		for (int i = 0; i < size; i++) {
			if (GameScreen.point.x == x[i] && GameScreen.point.y == y[i]) {
				return false;
			}
		}
		return true;
	}

	public void eatFood() {
		this.size++;
	}

	public boolean isGameOver() {
		return this.isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public boolean checkGameOver() {
		for (int i = 3; i < size; i++) {
			if (x[0] == x[i] && y[0] == y[i]) {
				return true;
			}
		}
		return false;
	}


	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public boolean isWin() {
		return size==MAX_LENGTH;
	}
}
