import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

import java.text.DecimalFormat;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

public class MainPanel extends JPanel implements Runnable {
	private final int initialPlantNum = 10;
	private final int plantsMax = 300;

	private final int initialHerbivoreNum = 20;
	private final int animalMax = 3;

	private Random rand;
	private Thread gameLoop;
	private Graphics dbg;
	private Image dbImage = null;
	public static int WIDTH =  1260;
	public static int HEIGHT = 700;
	private static final int FPS = 50;
	private static final long PERIOD = (long)(1.0 / FPS * 1000000000);
	private static long MAX_STATS_INTERVAL = 1000000000L;
	private long calcInterval = 0L;
	private long prevCalcTime;
	private long frameCount = 0;
	private double actualFPS = 0.0;
	private DecimalFormat df = new DecimalFormat("0.0");
	private volatile boolean running = false;

	private Plant plant;
	private Plant plants[] = new Plant[plantsMax];
	private int plantsNum = 0;

	private Herbivore animal;
	private Herbivore herbivores[] = new Herbivore[animalMax];
	private int herbivoresNum = 0;

	public MainPanel() {
		// パネルの推奨サイズを設定、pack()に必要
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		//setBackground(new Color(69,69,69));
		gameLoop = new Thread(this);
		rand = new Random();

		// init
		birthPlants(initialPlantNum);
		birthHerbivore(initialHerbivoreNum);

		gameLoop.start();
	}

	public void birthPlants(int n){
		if (plantsMax < n) {
			n = plantsMax;
		}

		for(int i=0; i<n; i++){
			plants[i] = new JuvenileForm();
			plants[i].setSize((int)((Math.random()+1) * 10));
			plants[i].setPosition(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
			plants[i].setVelocity(0.0f);
			plantsNum ++;
		}

	}

	public void birthHerbivore(int n){
		if (animalMax < n){
			n = animalMax;
		}

		for(int i=0; i<n; i++){
			herbivores[i] = new Herbivore();
			herbivores[i].setSize((int)((Math.random()+3) * 10));
			int tx, ty;
			do{
				tx = rand.nextInt(WIDTH);
				ty = rand.nextInt(HEIGHT);
			}while(tx-herbivores[i].getSize()<0||WIDTH<tx+herbivores[i].getSize()||ty-herbivores[i].getSize()<0||HEIGHT<ty+herbivores[i].getSize());
			herbivores[i].setPosition(tx, ty);
			herbivores[i].setVelocity((float)((Math.random()) * 10));
			herbivoresNum ++;
		}

	}

	public void gameUpdate() {
		// 植物の成長
		for(int i=0; i<plantsNum; i++){
			if(plants[i] == null){
				continue;
			}
			plant = plants[i];
			if(plant instanceof JuvenileForm
					&& ((JuvenileForm)plant).isGrowable()
				){
				plants[i] = ((JuvenileForm)plant).growup();
			}else if(plantsNum < plantsMax
					&& plant instanceof AdultForm
					&& ((AdultForm)plant).isBreedable()
				){
				plants[plantsNum] = ((AdultForm)plant).breed(WIDTH, HEIGHT);
				plantsNum ++;
			}else if(plant instanceof AdultForm
							|| plant instanceof JuvenileForm){
				if(plant.getElapsedTime() > 1500){
					plants[i] = null;
					plantsNum --;
					System.out.println("tired...orz");
				}
			}
		}
		reflesh();

		// move animal
		for(int i=0; i<herbivoresNum; i++){
			herbivores[i].move(plants);
		}


		// 値の更新
		for(int i = 0; i<plantsNum; i++){
			if(plants[i] != null){
				plant = plants[i];
				plant.update();
			}
		}

		for(int i=0; i<herbivoresNum; i++){
			if(herbivores[i] != null){
				herbivores[i].update();
			}
		}

		if(plantsNum == 0){
			birthPlants(10);
		}
	}

	public void gameRender() {
		if (dbImage == null) {
			// バッファイメージ
			dbImage = createImage(WIDTH, HEIGHT);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else {
				// バッファイメージの描画オブジェクト
				dbg = dbImage.getGraphics();
			}
		}

		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, WIDTH, HEIGHT);

		// informations
		dbg.setColor(Color.BLACK);
		dbg.drawString("FPS    : " + df.format(actualFPS), 4, 16);
		dbg.drawString("Plants : " + plantsNum, 4, 35);
		dbg.drawString("Herbivores: " + herbivoresNum, 4, 54);

		// バッファへ描画
		for(int i=0; i<plantsNum; i++){
			if(plants[i] != null){
				plants[i].draw(dbg);
			}
		}

		for(int i=0; i<herbivoresNum; i++){
			if(herbivores[i] != null){
				herbivores[i].draw(dbg);
			}
		}

	}

	public void reflesh(){
		Vector<Integer> free = new Vector<Integer>();

		/*
		System.out.println("--before----------------");
		for(int i=0; i<plantsNum; i++){
			if(plants[i] != null){
				System.out.print(1);
			}else{
				System.out.print(0);
			}
				System.out.print(" ");
		}
		System.out.println("");
		*/

		for(int i=0; i<plantsNum; i++){
			if(plants[i] == null){
				free.add(i);
			}else{
				if(!free.isEmpty()){
					plants[free.get(0)] = plants[i];
					plants[i] = null;
					free.remove(0);
				}
			}
		}

		/*
		System.out.println("--after-----------------");
		for(int i=0; i<plantsNum; i++){
			if(plants[i] != null){
				System.out.print(1);
			}else{
				System.out.print(0);
			}
				System.out.print(" ");
		}
		System.out.println("");
		*/

	}

	public void paintScreen() {
		try {
			Graphics g = getGraphics();
			if ((g != null) && (dbImage != null)) {
				g.drawImage(dbImage, 0, 0, null); // バッファイメージを前へ
			}
			Toolkit.getDefaultToolkit().sync();
			if (g != null) {
				g.dispose();  // グラフィックobjを破棄
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		/*
		 * ゲームループ
		 */

		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;

		beforeTime = System.nanoTime();
		prevCalcTime = beforeTime;

		running = true;
		while (running) {
			gameUpdate();
			gameRender();
			paintScreen();

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			// 前回のフレームの休止時間誤差も引いておく
			sleepTime = (PERIOD - timeDiff) - overSleepTime;

			if (sleepTime > 0) {
				// 休止時間がとれる場合
				try {
					Thread.sleep(sleepTime / 1000000L); // nano->ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// sleep()の誤差
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else {
				// 状態更新・レンダリングで時間を使い切ってしまい
				// 休止時間がとれない場合
				overSleepTime = 0L;
				// 休止なしが16回以上続いたら
				if (++noDelays >= 16) {
					Thread.yield(); // 他のスレッドを強制実行
					noDelays = 0;
				}
			}

			beforeTime = System.nanoTime();

			// FPSを計算
			calcFPS();
		}

		System.exit(0);
	}
	private void calcFPS() {
		frameCount++;
		calcInterval += PERIOD;

		// 1秒おきにFPSを再計算する
		if (calcInterval >= MAX_STATS_INTERVAL) {
			long timeNow = System.nanoTime();
			// 実際の経過時間を測定
			long realElapsedTime = timeNow - prevCalcTime; // 単位: ns

			// FPSを計算
	  // realElapsedTimeの単位はnsなのでsに変換する
			actualFPS = ((double) frameCount / realElapsedTime) * 1000000000L;
			//System.out.println(df.format(actualFPS));

			frameCount = 0L;
			calcInterval = 0L;
			prevCalcTime = timeNow;
		}
	}
}


