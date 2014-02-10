import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LifeGame extends JFrame implements ComponentListener{
	MainPanel panel;

	public LifeGame() {
		setTitle("\"LifeGame\"");

		// メインパネルを作成してフレームに追加
		panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);

		addComponentListener(this);

		// パネルサイズに応じてフレームサイズを自動設定
		pack();
	}

	public static void main(String[] args) {
		LifeGame frame = new LifeGame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 終了処理の指定
		frame.setVisible(true);  // 表示

	}

	public void componentResized(ComponentEvent e) {
		System.out.println("windowsizechanged: " + getWidth() + ", " + getHeight());
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

}

