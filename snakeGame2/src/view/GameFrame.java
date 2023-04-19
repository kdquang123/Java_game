package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameFrame extends JFrame {
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JLabel lb1;
	private int LOW_LV = 3;
	private int MID_LV = 5;
	private int HIGH_LV = 8;

	public GameFrame() {

		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/bgGame.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		lb1 = new JLabel("MỜI BẠN CHỌN MỨC ĐỘ");
		lb1.setBounds(180, 100, 150, 40);
		this.InitBTN1();
		this.InitBTN2();
		this.InitBTN3();
		this.setLayout(null);
		this.add(btn1);
		this.add(btn2);
		this.add(btn3);
		this.add(lb1);
		this.setResizable(false);

		this.setTitle("Snake Game");
		this.setSize(520, 540);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		this.setLocationRelativeTo(null);
		this.paint(getGraphics());
	}

	public void initGame(int level) {
		this.remove(btn1);
		this.remove(btn2);
		this.remove(btn3);
		this.remove(lb1);
		new SnakeGame(level);
		this.dispose();
	}

	public void InitBTN1() {
		this.btn1 = new JButton("Dễ");
		this.btn1.setBounds(200, 150, 100, 30);
		this.btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				initGame(LOW_LV);
			}
		});
	}

	public void InitBTN2() {
		btn2 = new JButton("Trung bình");
		btn2.setBounds(200, 200, 100, 30);
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				initGame(MID_LV);
			}
		});
	}

	public void InitBTN3() {
		btn3 = new JButton("Khó");
		btn3.setBounds(200, 250, 100, 30);
		btn3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				initGame(HIGH_LV);
			}
		});
	}
}
