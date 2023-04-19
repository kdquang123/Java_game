package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Snake;

public class GameScreen extends JPanel implements Runnable {
	private Snake snake;
	private int score;
	private int maxScore;
	private Image food;
	public static Point point;
	public int level;
	private File eatFoodAudio;
	private File gameOverAudio;
	private File winGameAudio;
	private AudioInputStream audioStreamGameOver;
	private Clip clipGameOVer;
	private AudioInputStream audioStreamEatFood;
	private Clip clipEatFood;
	private AudioInputStream audioStreamWinGame;
	private Clip clipWinGame;

	public GameScreen(int level) {
		this.food = Toolkit.getDefaultToolkit().getImage("images/food.png");
		this.eatFoodAudio = new File("audio/audio2.wav");
		this.gameOverAudio = new File("audio/audio1.wav");
		this.winGameAudio = new File("audio/wingame.wav");
		try {
			this.initAudio();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.level = level;
		this.score = 0;
		this.maxScore = this.getMaxScore();
		this.snake = new Snake();
		do {
			point = this.createFood();
		} while (snake.checkCreateFood());
		this.setSize(500, 500);
		Thread thread = new Thread(this);
		thread.start();
	}

//	public void restart() {
//		thread = new Thread(this);
//		thread.start();
//	}

	// Lấy ra điểm cao nhất trong file
	public int getMaxScore() {
		FileReader fr = null;
		BufferedReader br = null;
		int sco = 0;
		try {
			fr = new FileReader("score.txt");
			br = new BufferedReader(fr);
			try {
				String scostr = br.readLine();
				sco = scostr != null ? Integer.parseInt(scostr) : 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sco;
	}

	// ghi điểm cao nhất vào trong file
	public void setMaxScore() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter("score.txt");
			bw = new BufferedWriter(fw);
			try {
				bw.write(maxScore + "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Khởi tạo âm thanh của game
	public void initAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioStreamEatFood = AudioSystem.getAudioInputStream(this.eatFoodAudio);
		clipEatFood = AudioSystem.getClip();
		clipEatFood.open(audioStreamEatFood);
		audioStreamGameOver = AudioSystem.getAudioInputStream(this.gameOverAudio);
		clipGameOVer = AudioSystem.getClip();
		clipGameOVer.open(audioStreamGameOver);
		audioStreamWinGame = AudioSystem.getAudioInputStream(this.winGameAudio);
		clipWinGame = AudioSystem.getClip();
		clipWinGame.open(audioStreamWinGame);
	}

	// Vẽ nền của game
	private void drawBackGround(Graphics g) {
		g.setColor(new Color(0, 204, 0));
		g.fillRect(0, 0, 500, 500);
		g.setColor(new Color(51, 180, 51));
		int x = 0;
		for (int i = 1; i <= 20; i++) {
			if (i % 2 == 0) {
				for (int j = 25; j <= 475; j += 50) {
					g.fillRect(j, x, 25, 25);
				}
			} else {
				for (int j = 0; j <= 450; j += 50) {
					g.fillRect(j, x, 25, 25);
				}
			}
			x += 25;
		}
		g.drawImage(food, point.x * 25, point.y * 25, 25, 25, null);
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.ITALIC, 18));
		g.drawString("Score: " + this.score, 10, 30);
		g.drawString("Max Score: " + this.maxScore, 10, 50);
	}

	// vẽ game
	public void paint(Graphics g) {
		drawBackGround(g);
		snake.drawSnake(g);
	}

	// Thực hiện khi thua trò chơi
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 60));
		g.drawString("Game Over!", 100, 250);
		g.setFont(new Font("Ink Free", Font.BOLD, 25));
		g.drawString("Press space to restart!", 130, 300);
	}

	public void gameStop(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 25));
		g.drawString("Press space to continue!", 130, 200);
	}

	public void gameWin(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 60));
		g.drawString("You Win!", 150, 250);
		g.setFont(new Font("Ink Free", Font.BOLD, 25));
		g.drawString("Press space to restart!", 130, 300);
	}

	// Tạo tọa độ cho mồi
	public Point createFood() {
		Random rd = new Random();
		return new Point(rd.nextInt(19), rd.nextInt(19));
	}

	public void run() {
		while (true) {
			if (snake.checkGameOver() == true) {
				this.score = 0;
				this.playGameOverAudio();
				snake.setGameOver(true);
				this.gameOver(this.getGraphics());
				this.setMaxScore();
				break;
			}

			if (snake.isStop() == true) {
				this.gameStop(this.getGraphics());
			}
			if (snake.checkEatFood()) {
				this.playEatFoodAudio();
				this.score++;
				if (this.score > this.maxScore) {
					this.maxScore = this.score;
				}

				if (snake.isWin()) {
					this.gameWin(this.getGraphics());
					this.playWinGameAudio();
					break;
				}

				do {
					point = createFood();
				} while (!snake.checkCreateFood());
			}
			if (snake.isGameOver() == false && snake.isStop() == false) {
				repaint();
				snake.update();
			}
			try {
				Thread.sleep(600 / this.level);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Snake getSnake() {
		return this.snake;
	}

	public void playEatFoodAudio() {
		clipEatFood.setMicrosecondPosition(0);
		clipEatFood.start();
	}

	public void playGameOverAudio() {
		clipGameOVer.setMicrosecondPosition(0);
		clipGameOVer.start();
	}

	public void playWinGameAudio() {
		clipWinGame.setMicrosecondPosition(0);
		clipWinGame.start();
	}

}
