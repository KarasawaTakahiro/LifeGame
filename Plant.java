
public class Plant extends Creature {
	private int costTimeToStoreNutrients;  // 養分を蓄えるのにかかるフレーム数

	public Plant(){
		super();
		setNutrients(0);  // 現在の養分量
		setNutrientsLimit(100);
		setCostTimeToStoreNutrients(25);
		setActionFrequency(2);
	}

	public void update(){
		super.update();
		grow();
	}

	public void grow() {
		if (getElapsedTime() % costTimeToStoreNutrients == 0
				&& getNutrients() <= getNutrientsLimit()) {
			store(1);
		}
	}

	public int getCostTimeToStoreNutrients(){
		return costTimeToStoreNutrients;
	}

	public void setCostTimeToStoreNutrients(int costTime){
		costTimeToStoreNutrients = costTime;
	}

}
