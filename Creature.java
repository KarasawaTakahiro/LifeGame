import java.awt.Point;

public class Creature extends BasisObject {
	private int nutrients;  // 養分
	private int nutrientsLimit = 20;  // 保有可能の養分の限度
	private int actionFrequency;
	private int vx;
	private int vy;

	public Creature(){
		super();
		nutrients = 0;
		nutrientsLimit = 20;
	}

	public void update(){
		super.update();
		move();
	}

	public void store(int nutrient) {
		/*
		 * 蓄える
		 */
		this.nutrients += nutrient;
	}

	public void consume(int amount) {
		/*
		 * 消費する
		 */
		this.nutrients -= amount;
	}

	public int getNutrients() {
		return nutrients;
	}

	public void setNutrients(int amount) {
		this.nutrients = amount;
	}

	public int getNutrientsLimit(){
		return nutrientsLimit;
	}

	public void setNutrientsLimit(int limit){
		nutrientsLimit = limit;
	}

	public boolean moovable(){
		if (getElapsedTime() % actionFrequency > 0){
			return false;
		}else{
			return true;
		}
	}

	public void move() {
		/*
		 * move enemy
		 * update enemy position
		 */
		if(!moovable()){
			return;
		}

		setPosition(getMovePoint());
		hitWall();

	}

	public void hitWall(){
		if (getX() < 0 || MainPanel.WIDTH < getX() +  getSize()) {
			vx = -vx;
		}
		if (getY() < 0 || MainPanel.HEIGHT < getY() + getSize()) {
			vy = -vy;
		}
	}

	public void reverse(){
		vx = -vx;
		vy = -vy;
	}

	public Point getMovePoint(){
		return new Point(getX()+vx, getY()+vy);
	}

	public int getActionFrequency(){
		return actionFrequency;
	}

	public void setActionFrequency(int f){
		actionFrequency = f;
	}

	public void setVelocity(int vx, int vy) {
		this.setVx(vx);
		this.setVy(vy);
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getVx(){
		return this.vx;
	}

	public int getVy(){
		return this.vy;
	}
}

