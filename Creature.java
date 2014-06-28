import java.awt.Point;
import java.util.Random;

public class Creature extends BasisObject {
	private int nutrients;  // 養分
	private int nutrientsLimit = 20;  // 保有可能な養分の限度
	private int actionFrequency;
	private float velocity;  // 進行速度
	private float direction;  // 進行方向 [rad]
	private Random rand;

	public Creature(){
		super();
		nutrients = 0;
		nutrientsLimit = 20;
		rand = new Random();
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

	public int getActionFrequency(){
		return actionFrequency;
	}

	public void setActionFrequency(int f){
		actionFrequency = f;
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

	}

	public boolean moovable(){
		/*
		 * judge to be a hit walls, plants and animals.
		 */
		if (getElapsedTime() % actionFrequency > 0){
			return false;
		}else{
			if ((getX() < 0 || MainPanel.WIDTH < getX() +  getSize()) 
				|| (getY() < 0 || MainPanel.HEIGHT < getY() + getSize())){
				hitWall();
				return false;
			}else{
				return true;
			}
		}
	}

	public Point getMovePoint(){
		/*
		 * 移動先の座標を計算する
		 */
		return new Point((int)(getX() + (float)Math.cos(direction) * velocity), (int)(getY() + (float)Math.sin(direction) * velocity));
	}

	public float getVelocity(){
		return velocity;
	}

	public void setVelocity(float v){
		velocity = v;
	}

	public void reverse(){
		direction += getRandomDirection();
	}

	private float getRandomDirection(){
		return (float)Math.PI * (rand.nextFloat() * 2 - 1.0f);
	}

	public void setRandomDirection(){
		direction = getRandomDirection();
	}

	public void hitWall(){
		System.out.println("hitWall");
		reverse();
	}

	public void hitPlant(){
		System.out.println("hitPlant");
		reverse();
	}

	public void hitAnimal(){
		reverse();
	}

	// debug
	public float getDirection(){
		return direction;
	}

}
