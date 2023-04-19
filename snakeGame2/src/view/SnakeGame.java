package view;

import javax.swing.JFrame;

import controller.Handler;

public class SnakeGame extends JFrame {
	private GameScreen screen;
	private int gameLevel;

	public SnakeGame(int x) {
		this.gameLevel = x;
		this.screen = new GameScreen(this.gameLevel);
		this.add(screen);
		Handler handler = new Handler(this.screen.getSnake(), this.screen, this);
		this.addKeyListener(handler);
		this.setResizable(false);
		this.setTitle("Snake Game");
		this.setSize(520, 540);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
